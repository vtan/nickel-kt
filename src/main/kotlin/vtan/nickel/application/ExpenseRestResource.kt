package vtan.nickel.application

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import vtan.nickel.domain.Category
import vtan.nickel.domain.ExpenseRepository
import java.math.BigDecimal
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriInfo

private val LOG = LoggerFactory.getLogger(ExpenseRestResource::class.java)

@Component
@Path("expenses")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class ExpenseRestResource @Inject constructor(val expenseRepository: ExpenseRepository) {

    data class PostExpense(val date: LocalDate, val amount: BigDecimal, val category: Category, val description: String)

    @POST
    fun postExpense(postExpense: PostExpense, @Context uriInfo: UriInfo): Response {
        val newExpense = with(postExpense) {
            expenseRepository.createNew(date, amount, category, description)
        }
        LOG.debug("Created {}", newExpense)
        val location = uriInfo.absolutePathBuilder.path(newExpense.id.toString()).build()
        return Response.created(location).build()
    }

    @GET
    fun getExpenses(@QueryParam("yearmonth") yearMonth: YearMonthParam): Response = Response
        .ok(expenseRepository.findAllByYearMonth(yearMonth.parsed))
        .build()

    @GET
    @Path("yearmonths")
    fun getYearMonthsOfExpenses(): Response = Response
        .ok(expenseRepository.findYearMonths())
        .build()

    @DELETE
    @Path("{id}")
    fun deleteExpense(@PathParam("id") id: Long): Response =
        if (expenseRepository.delete(id)) {
            LOG.debug("Deleted {}", id)
            Response.noContent().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }

}
