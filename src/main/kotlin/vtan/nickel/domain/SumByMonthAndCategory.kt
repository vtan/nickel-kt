package vtan.nickel.domain

import java.math.BigDecimal
import java.time.YearMonth

data class SumByMonthAndCategory(val month: YearMonth, val category: Category, val sum: BigDecimal)
