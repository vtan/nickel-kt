package vtan.nickel.domain

import java.math.BigDecimal
import java.time.LocalDate
import java.time.YearMonth

interface ExpenseRepository {

    fun createNew(date: LocalDate, amount: BigDecimal, category: Category, description: String): Expense

    fun sumByYearMonthAndCategory(): List<SumByMonthAndCategory>

}
