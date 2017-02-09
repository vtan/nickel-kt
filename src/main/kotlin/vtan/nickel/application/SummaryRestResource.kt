package vtan.nickel.application

import org.springframework.stereotype.Component
import vtan.nickel.domain.ExpenseRepository
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Component
@Path("summary")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class SummaryRestResource @Inject constructor(val expenseRepository: ExpenseRepository) {

    @GET
    fun getMonthlySummary(): Response = Response
        .ok(expenseRepository.sumByYearMonthAndCategory())
        .build()

}
