package vtan.nickel.domain

import java.math.BigDecimal
import java.time.LocalDate
import java.time.YearMonth

interface ExpenseRepository {

    fun createNew(date: LocalDate, amount: BigDecimal, category: Category, description: String): Expense

    fun findAllByYearMonth(yearMonth: YearMonth): List<Expense>

    fun findYearMonths(): List<YearMonth>

    fun sumByYearMonthAndCategory(): List<SumByMonthAndCategory>

}
