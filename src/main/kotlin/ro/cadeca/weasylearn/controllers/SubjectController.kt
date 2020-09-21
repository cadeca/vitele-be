package ro.cadeca.weasylearn.controllers

import org.springframework.web.bind.annotation.*
import ro.cadeca.weasylearn.config.Roles.Companion.ADMIN
import ro.cadeca.weasylearn.converters.subject.SubjectFromDtoConverter
import ro.cadeca.weasylearn.dto.subjects.SubjectDTO
import ro.cadeca.weasylearn.model.Subject
import ro.cadeca.weasylearn.services.SubjectService
import javax.annotation.security.RolesAllowed

@RestController
@RequestMapping("api/subject")
class SubjectController(
        private val subjectService: SubjectService,
        private val subjectFromDtoConverter: SubjectFromDtoConverter
) {

    @GetMapping
    @RolesAllowed(ADMIN)
    fun findAll(@RequestParam(required = false) query: String?): List<Subject> =
            subjectService.findAll(query)

    @PostMapping
    @RolesAllowed(ADMIN)
    fun create(@RequestBody subject: SubjectDTO) =
            subjectService.createSubject(subjectFromDtoConverter.convert(subject))
}
