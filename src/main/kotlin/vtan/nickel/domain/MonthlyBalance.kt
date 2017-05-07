package vtan.nickel.domain

import java.math.BigDecimal
import java.time.YearMonth
import java.time.temporal.ChronoUnit

data class MonthlyBalance(
        val month: YearMonth,
        val expense: BigDecimal,
        val income: BigDecimal,
        val balance: BigDecimal,
        val accumBalance: BigDecimal)

fun calculateMonthlyBalances(
        expenses: Map<YearMonth, BigDecimal>,
        incomes: List<Income>):
        List<MonthlyBalance> {
    return spanMonths(expenses.keys)
            .map {
                val month = it
                val expense = expenses[month] ?: BigDecimal(0)
                val income = incomes
                        .filter { it.includes(month) }
                        .fold(BigDecimal(0), { x, y -> x + y.amount })
                Triple(month, expense, income)
            }
            .fold(Pair<BigDecimal, List<MonthlyBalance>>(BigDecimal(0), listOf()), { accum, triple ->
                var (accumBalance, monthlyBalances) = accum
                val (month, expense, income) = triple
                val balance = income - expense
                accumBalance += balance
                monthlyBalances += MonthlyBalance(month, expense, income, balance, accumBalance)
                Pair(accumBalance, monthlyBalances)
            })
            .second
}

private fun spanMonths(months: Set<YearMonth>): List<YearMonth> {
    val firstMonth = months.min()
    val lastMonth = months.max()
    if (firstMonth == null || lastMonth == null) {
        return listOf()
    }
    val monthsBetween = ChronoUnit.MONTHS.between(firstMonth, lastMonth)
    return (0..monthsBetween).map { firstMonth.plusMonths(it) }
}
