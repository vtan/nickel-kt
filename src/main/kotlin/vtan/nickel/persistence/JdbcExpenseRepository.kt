package vtan.nickel.persistence

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Component
import vtan.nickel.domain.Category
import vtan.nickel.domain.Expense
import vtan.nickel.domain.ExpenseRepository
import vtan.nickel.domain.SumByMonthAndCategory
import java.math.BigDecimal
import java.sql.Date
import java.sql.Statement
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@Component
class JdbcExpenseRepository @Inject constructor(val jdbcTemplate: JdbcTemplate) : ExpenseRepository {

    override fun createNew(date: LocalDate, amount: BigDecimal, category: Category, description: String): Expense {
        val keyHolder = GeneratedKeyHolder()
        jdbcTemplate.update({ conn ->
            val stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)
            stmt.setDate(1, Date.valueOf(date))
            stmt.setBigDecimal(2, amount)
            stmt.setString(3, category.name)
            stmt.setString(4, description)
            stmt
        }, keyHolder)

        return Expense(keyHolder.key.toLong(), date, amount, category, description)
    }

    override fun findAllByYearMonth(yearMonth: YearMonth): List<Expense> = jdbcTemplate.query(
        { conn ->
            val stmt = conn.prepareStatement(SELECT_BY_YEAR_MONTH)
            stmt.setInt(1, yearMonth.year)
            stmt.setInt(2, yearMonth.monthValue)
            stmt
        },
        { resultSet, rowNum ->
            val id = resultSet.getLong("id")
            val date = resultSet.getDate("date").toLocalDate()
            val amount = resultSet.getBigDecimal("amount")
            val category = Category(resultSet.getString("category"))
            val description = resultSet.getString("description")
            Expense(id, date, amount, category, description)
        })

    override fun findYearMonths(): List<YearMonth> = jdbcTemplate.query(
        SELECT_YEAR_MONTHS,
        { resultSet, rowNum ->
            val year = resultSet.getInt("year")
            val month = resultSet.getInt("month")
            YearMonth.of(year, month)
        })

    override fun sumByYearMonthAndCategory(): List<SumByMonthAndCategory> = jdbcTemplate.query(
        SELECT_MONTHLY_SUMS,
        { resultSet, rowNum ->
            val category = Category(resultSet.getString("category"))
            val year = resultSet.getInt("year")
            val month = resultSet.getInt("month")
            val sum = resultSet.getBigDecimal("sum")
            SumByMonthAndCategory(YearMonth.of(year, month), category, sum)
        })

    override fun delete(id: Long): Boolean {
        val affectedRows = jdbcTemplate.update { conn ->
            val stmt = conn.prepareStatement(DELETE)
            stmt.setLong(1, id)
            stmt
        }
        return affectedRows > 0
    }
}

private const val INSERT = """
INSERT INTO expense (date, amount, category, description)
VALUES (?, ?, ?, ?)
"""

private const val SELECT_BY_YEAR_MONTH = """
SELECT id, date, amount, category, description
FROM expense
WHERE YEAR(date) = ? AND MONTH(date) = ?
ORDER BY date, category, description, id
"""

private const val SELECT_YEAR_MONTHS = """
SELECT DISTINCT YEAR(date) year, MONTH(date) month
FROM expense
ORDER BY year, month
"""

private const val SELECT_MONTHLY_SUMS = """
SELECT category, YEAR(date) year, MONTH(date) month, SUM(amount) sum
FROM expense
GROUP BY category, YEAR(date), MONTH(date)
"""

private const val DELETE = """
DELETE FROM expense
WHERE id = ?
"""
