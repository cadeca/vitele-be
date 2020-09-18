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
@ContextConfiguration(classes = [WeasylearnBeApplication::class], initializers = [PostgresIT.Initializer::class])
@ActiveProfiles(profiles = ["intTest"])
@AutoConfigureMockMvc
abstract class PostgresIT {
    companion object {
        val postgres = PostgreSQLContainer<Nothing>().apply {
            withDatabaseName("postgres")
            withUsername("integrationUser")
            withPassword("testPass")
        }
    }

    internal class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
            postgres.start()

            TestPropertyValues.of(
                    "spring.datasource.url=${postgres.jdbcUrl}",
                    "spring.datasource.username=${postgres.username}",
                    "spring.datasource.password=${postgres.password}"
            ).applyTo(configurableApplicationContext.environment)
        }
    }
}
