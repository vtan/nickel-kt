package vtan.nickel.application

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import vtan.nickel.domain.IncomeRepository
import java.math.BigDecimal
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriInfo

private val log = LoggerFactory.getLogger(IncomeRestResource::class.java)

@Component
@Path("incomes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class IncomeRestResource @Inject constructor(val incomeRepository: IncomeRepository) {

    @POST
    fun postIncome(request: PostIncome, @Context uriInfo: UriInfo): Response {
        val newIncome = with(request) {
            incomeRepository.createNew(beginMonth.parsed, endMonth?.parsed, amount, description)
        }
        log.debug("Created {}", newIncome)

        val location = uriInfo.absolutePathBuilder.path(newIncome.id.toString()).build()
        return Response.created(location).build()
    }

    @GET
    fun getAllIncomes(): Response =
        Response.ok(incomeRepository.findAll()).build()

    @DELETE
    @Path("{id}")
    fun deleteExpense(@PathParam("id") id: Long): Response =
        if (incomeRepository.delete(id)) {
            log.debug("Deleted {}", id)
            Response.noContent().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }

}

data class PostIncome(
    val beginMonth: YearMonthParam,
    val endMonth: YearMonthParam?,
    val amount: BigDecimal,
    val description: String)
