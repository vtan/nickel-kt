package vtan.nickel.application

import org.springframework.stereotype.Component
import vtan.nickel.domain.ExpenseRepository
import vtan.nickel.domain.IncomeRepository
import vtan.nickel.domain.calculateMonthlyBalances
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Component
@Path("balances")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class BalanceRestResource @Inject constructor(
        val expenseRepository: ExpenseRepository,
        val incomeRepository: IncomeRepository) {

    @GET
    fun getMonthlyBalances(): Response {
        val expenses = expenseRepository.sumByYearMonth()
        val incomes = incomeRepository.findAll()
        val balances = calculateMonthlyBalances(expenses, incomes)
        return Response.ok(balances).build()
    }

}

