package vtan.nickel

import org.glassfish.jersey.server.ResourceConfig
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.stereotype.Component
import vtan.nickel.application.ExpenseRestResource
import vtan.nickel.application.SummaryRestResource
import javax.ws.rs.ApplicationPath

@SpringBootApplication
open class Application

fun main(args: Array<String>) = SpringApplication.run(Application::class.java, *args)

@Component
@ApplicationPath("/api")
class JerseyConfig : ResourceConfig() {

    init {
        registerClasses(
            ExpenseRestResource::class.java,
            SummaryRestResource::class.java)
    }

}
