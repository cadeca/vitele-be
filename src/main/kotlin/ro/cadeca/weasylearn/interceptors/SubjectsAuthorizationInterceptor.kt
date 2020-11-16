package ro.cadeca.weasylearn.interceptors

import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.HandlerMapping
import ro.cadeca.weasylearn.services.AuthenticationService
import ro.cadeca.weasylearn.services.SubjectService
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class SubjectsAuthorizationInterceptor(
        private val subjectService: SubjectService,
        private val authenticationService: AuthenticationService
) : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {

        if (!request.servletPath.matches(Regex("/api/subject/\\d+")))
            return true

        val pathVariables = request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE) as Map<*, *>

        val subjectId = (pathVariables["id"] as String).toLong()

        return if (request.method == "GET") {
            val subject = subjectService.findById(subjectId)
            val loggedInUser = authenticationService.getKeycloakUser()
            subject.students.orEmpty().map { it.username }.contains(loggedInUser.username) || subjectService.userCanEdit(subjectId)
        } else {
            subjectService.userCanEdit(subjectId)
        }

    }
}
