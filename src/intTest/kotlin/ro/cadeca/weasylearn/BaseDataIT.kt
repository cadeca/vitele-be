package ro.cadeca.weasylearn

import com.c4_soft.springaddons.security.oauth2.test.mockmvc.keycloak.ServletKeycloakAuthUnitTestingSupport
import org.junit.runner.RunWith
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.containers.PostgreSQLContainer
import ro.cadeca.weasylearn.config.KeycloakConfig

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = [WeasylearnBeApplication::class], initializers = [BaseDataIT.Initializer::class])
@ActiveProfiles(profiles = ["intTest"])
@ComponentScan(basePackageClasses = [KeycloakConfig::class])
@AutoConfigureMockMvc
abstract class BaseDataIT : ServletKeycloakAuthUnitTestingSupport() {
    companion object {
        val mongo = MongoDBContainer()
        val postgres = PostgreSQLContainer<Nothing>().apply {
            withDatabaseName("postgres")
            withUsername("integrationUser")
            withPassword("testPass")
        }
    }

    internal class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
            mongo.start()
            postgres.start()

            TestPropertyValues.of(
                    "spring.datasource.url=${postgres.jdbcUrl}",
                    "spring.datasource.username=${postgres.username}",
                    "spring.datasource.password=${postgres.password}",
                    "spring.data.mongodb.port=${mongo.firstMappedPort}",
                    "spring.data.mongodb.host=${mongo.host}"
            ).applyTo(configurableApplicationContext.environment)
        }
    }
}
