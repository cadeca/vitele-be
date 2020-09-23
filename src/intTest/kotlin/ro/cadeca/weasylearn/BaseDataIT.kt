package ro.cadeca.weasylearn

import com.c4_soft.springaddons.security.oauth2.test.mockmvc.keycloak.ServletKeycloakAuthUnitTestingSupport
import org.junit.runner.RunWith
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import ro.cadeca.weasylearn.config.KeycloakConfig

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = [WeasylearnBeApplication::class])
@ActiveProfiles(profiles = ["intTest"])
@ComponentScan(basePackageClasses = [KeycloakConfig::class])
@Testcontainers
@AutoConfigureMockMvc
abstract class BaseDataIT : ServletKeycloakAuthUnitTestingSupport() {
    companion object {
        @Container
        val mongo = MyMongoContainer()

        @Container
        val postgres = MyPostgresContainer().apply {
            withDatabaseName("postgres")
            withUsername("integrationUser")
            withPassword("testPass")
        }
    }
}
