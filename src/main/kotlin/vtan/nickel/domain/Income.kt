package vtan.nickel.domain

import java.math.BigDecimal
import java.time.YearMonth

data class Income(
    val id: Long,
    var beginMonth: YearMonth,
    var endMonth: YearMonth?,
    var amount: BigDecimal,
    var description: String) {

    fun includes(month: YearMonth): Boolean {
        val afterBegin = beginMonth <= month
        val beforeEnd = if (endMonth == null) true else month <= endMonth
        return afterBegin && beforeEnd
    }

}

interface IncomeRepository {
    fun createNew(beginMonth: YearMonth, endMonth: YearMonth?, amount: BigDecimal, description: String): Income
    fun delete(id: Long): Boolean
    fun findAll(): List<Income>
}
