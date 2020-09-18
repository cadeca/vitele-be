package ro.cadeca.weasylearn

import org.junit.runner.RunWith
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.containers.PostgreSQLContainer


@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = [WeasylearnBeApplication::class], initializers = [MongoIT.Initializer::class])
@ActiveProfiles(profiles = ["intTest"])
@AutoConfigureMockMvc
abstract class MongoIT {
    companion object {
        val mongo = MongoDBContainer()
    }

    internal class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
            mongo.start()

            TestPropertyValues.of(
                    "spring.data.mongodb.port=${mongo.firstMappedPort}",
                    "spring.data.mongodb.host=${mongo.host}"
            ).applyTo(configurableApplicationContext.environment)
        }
    }
}
