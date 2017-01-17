package vtan.nickel.domain

import java.math.BigDecimal
import java.time.LocalDate

data class Expense(val id: Long, var date: LocalDate, var amount: BigDecimal, var category: Category)
