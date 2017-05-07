package vtan.nickel.persistence

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Component
import vtan.nickel.domain.Income
import vtan.nickel.domain.IncomeRepository
import java.math.BigDecimal
import java.sql.Date
import java.sql.Statement
import java.time.YearMonth
import javax.inject.Inject

@Component
class JdbcIncomeRepository @Inject constructor(val jdbcTemplate: JdbcTemplate) : IncomeRepository {
    override fun createNew(beginMonth: YearMonth, endMonth: YearMonth?, amount: BigDecimal, description: String): Income {
        val keyHolder = GeneratedKeyHolder()
        jdbcTemplate.update({ conn ->
            val stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)
            stmt.setDate(1, Date.valueOf(beginMonth.atDay(1)))
            stmt.setDate(2, if (endMonth == null) null else Date.valueOf(endMonth.atDay(1)))
            stmt.setBigDecimal(3, amount)
            stmt.setString(4, description)
            stmt
        }, keyHolder)

        return Income(keyHolder.key.toLong(), beginMonth, endMonth, amount, description)
    }

    override fun delete(id: Long): Boolean {
        val affectedRows = jdbcTemplate.update { conn ->
            val stmt = conn.prepareStatement(DELETE)
            stmt.setLong(1, id)
            stmt
        }
        return affectedRows > 0
    }

    override fun findAll(): List<Income> = jdbcTemplate.query(
        SELECT,
        { resultSet, rowNum ->
            val id = resultSet.getLong("id")
            val beginMonth = YearMonth.from(resultSet.getDate("begin").toLocalDate())
            val end = resultSet.getDate("end")
            val endMonth = if (end == null) null else YearMonth.from(end.toLocalDate())
            val amount = resultSet.getBigDecimal("amount")
            val description = resultSet.getString("description")
            Income(id, beginMonth, endMonth, amount, description)
        }
    )

}

private const val INSERT = """
INSERT INTO income (begin, end, amount, description)
VALUES (?, ?, ?, ?)
"""

private const val DELETE = """
DELETE FROM income
WHERE id = ?
"""

private const val SELECT = """
SELECT id, begin, end, amount, description
FROM income
"""
