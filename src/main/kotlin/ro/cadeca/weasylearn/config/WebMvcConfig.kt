package ro.cadeca.weasylearn.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import ro.cadeca.weasylearn.interceptors.SubjectsAuthorizationInterceptor

@Configuration
class WebMvcConfig(
        private val subjectsAuthorizationInterceptor: SubjectsAuthorizationInterceptor
) : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins("http://localhost:2023",
                        "http://weasylearn-fe.localhost",
                        "https://dev.weasylearn.ro",
                        "https://demo.devweasylearn.ro",
                        "https://app.devweasylearn.ro")
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(subjectsAuthorizationInterceptor)
    }
}
