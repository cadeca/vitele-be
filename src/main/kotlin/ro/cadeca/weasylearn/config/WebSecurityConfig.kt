package ro.cadeca.weasylearn.config

import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.FilterType
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true)
@ComponentScan(basePackageClasses = [KeycloakSecurityComponents::class],
        excludeFilters = [ComponentScan.Filter(type = FilterType.REGEX, pattern = ["org.keycloak.adapters.springsecurity.management.HttpSessionManager"])])
class WebSecurityConfig : KeycloakWebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        super.configure(http)
        http.authorizeRequests()
                .anyRequest()
                .authenticated()


        http.cors().configurationSource(corsConfigurationSource())
        http.csrf().disable()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource? {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("https://devweasylearn.ro", "https://demo.devweasylearn.ro", "https://app.devweasylearn.ro")
        configuration.allowedMethods = listOf("*")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

//    In case we need to skip keycloak for testing purposes
//    override fun configure(web: WebSecurity) {
//        web.ignoring().anyRequest()
//    }


    @Autowired
    fun configureGlobal(auth: AuthenticationManagerBuilder?) {
        super.configure(auth)
        auth!!.authenticationProvider(
                keycloakAuthenticationProvider().apply {
                    setGrantedAuthoritiesMapper(simpleAuthorityMapper())
                }
        )
    }

    @Bean
    fun simpleAuthorityMapper() = SimpleAuthorityMapper()

    @Bean
    @Override
    override fun sessionAuthenticationStrategy(): SessionAuthenticationStrategy {
        return NullAuthenticatedSessionStrategy()
    }
}
