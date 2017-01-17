package vtan.nickel.persistence

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Component
import vtan.nickel.domain.Category
import vtan.nickel.domain.Expense
import vtan.nickel.domain.ExpenseRepository
import vtan.nickel.domain.SumByMonthAndCategory
import java.math.BigDecimal
import java.sql.Date
import java.sql.PreparedStatement
import java.sql.Statement
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@Component
class JdbcExpenseRepository @Inject constructor(val jdbcTemplate: JdbcTemplate) : ExpenseRepository {

    override fun createNew(date: LocalDate, amount: BigDecimal, category: Category): Expense {
        val keyHolder = GeneratedKeyHolder()
        jdbcTemplate.update(fun(conn): PreparedStatement {
            val stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)
            stmt.setDate(1, Date.valueOf(date))
            stmt.setBigDecimal(2, amount)
            stmt.setString(3, category.name)
            return stmt
        }, keyHolder)

        return Expense(keyHolder.key.toLong(), date, amount, category)
    }

    override fun sumByYearMonthAndCategory(): List<SumByMonthAndCategory> {
        return jdbcTemplate.query(SELECT_MONTH, RowMapper { resultSet, rowNum ->
            val category = Category(resultSet.getString("category"))
            val year = resultSet.getInt("year")
            val month = resultSet.getInt("month")
            val sum = resultSet.getBigDecimal("sum")
            SumByMonthAndCategory(YearMonth.of(year, month), category, sum)
        })
    }
}

private const val INSERT = "INSERT INTO expense (date, amount, category) VALUES (?, ?, ?)"

private const val SELECT_MONTH = """
SELECT category, YEAR(date) year, MONTH(date) month, SUM(amount) sum
FROM expense
GROUP BY category, YEAR(date), MONTH(date)
"""
