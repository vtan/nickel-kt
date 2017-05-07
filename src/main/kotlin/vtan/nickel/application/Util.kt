package vtan.nickel.application

import java.time.YearMonth

class YearMonthParam(param: String) {
    val parsed: YearMonth = YearMonth.parse(param)
}

