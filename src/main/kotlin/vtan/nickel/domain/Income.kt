package vtan.nickel.domain

import java.math.BigDecimal
import java.time.YearMonth

data class Income(
    val id: Long,
    var beginMonth: YearMonth,
    var endMonth: YearMonth?,
    var amount: BigDecimal,
    var description: String)

interface IncomeRepository {
    fun createNew(beginMonth: YearMonth, endMonth: YearMonth?, amount: BigDecimal, description: String): Income
    fun delete(id: Long): Boolean
    fun findAll(): List<Income>
}
