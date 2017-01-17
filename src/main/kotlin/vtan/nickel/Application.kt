package vtan.nickel

import org.glassfish.jersey.logging.LoggingFeature
import org.glassfish.jersey.server.ResourceConfig
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.stereotype.Component
import javax.ws.rs.ApplicationPath

@SpringBootApplication
open class Application

fun main(args: Array<String>) = SpringApplication.run(Application::class.java, *args)

@Component
@ApplicationPath("/api")
class JerseyConfig : ResourceConfig() {

    init {
        packages("vtan.nickel")
        register(LoggingFeature::class.java)
    }

}
