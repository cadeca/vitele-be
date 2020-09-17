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
@ContextConfiguration(classes = [WeasylearnBeApplication::class], initializers = [DatabaseContainerIT.Initializer::class])
@ActiveProfiles(profiles = ["intTest"])
@AutoConfigureMockMvc
abstract class DatabaseContainerIT {
    companion object {
        val postgres = PostgreSQLContainer<Nothing>().apply {
            withDatabaseName("postgres")
            withUsername("integrationUser")
            withPassword("testPass")
        }

        const val mongoDbUser = "weasylearner"
        const val mongoDbPassword = "weasylearner"
        val mongo = MongoDBContainer().apply {
//            withClasspathResourceMapping("init-mongo.js", "/docker-entrypoint-initdb.d/init-mongo.js", BindMode.READ_WRITE)
        }
    }

    internal class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
            postgres.start()
            mongo.start()

            TestPropertyValues.of(
                    "spring.datasource.url=${postgres.jdbcUrl}",
                    "spring.datasource.username=${postgres.username}",
                    "spring.datasource.password=${postgres.password}"
//                    "spring.data.mongodb.uri=${mongo.replicaSetUrl}",
//                    "spring.data.mongodb.username=$mongoDbUser",
//                    "spring.data.mongodb.password=$mongoDbPassword"
            ).applyTo(configurableApplicationContext.environment)
        }
    }
}
