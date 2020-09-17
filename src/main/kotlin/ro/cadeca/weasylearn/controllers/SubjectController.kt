package ro.cadeca.weasylearn.controllers

import org.springframework.web.bind.annotation.*
import ro.cadeca.weasylearn.config.ADMIN
import ro.cadeca.weasylearn.config.TEACHER
import ro.cadeca.weasylearn.model.Subject
import ro.cadeca.weasylearn.services.AuthenticationService
import ro.cadeca.weasylearn.services.SubjectService
import java.security.Principal
import javax.annotation.security.RolesAllowed

@RestController
@RequestMapping("api/subject")
class SubjectController(private val subjectService: SubjectService) {

    @GetMapping
    @RolesAllowed(ADMIN, TEACHER)
    fun findAll(): List<Subject> = subjectService.findAll()

    @PutMapping
    @RolesAllowed(ADMIN)
    fun create(@RequestBody subject: Subject) = subjectService.createSubject(subject)
}
