package ro.cadeca.weasylearn.controllers

import org.springframework.web.bind.annotation.*
import ro.cadeca.weasylearn.config.Roles.Companion.ADMIN
import ro.cadeca.weasylearn.converters.subject.SubjectFromDtoConverter
import ro.cadeca.weasylearn.converters.subject.SubjectToDtoConverter
import ro.cadeca.weasylearn.dto.subjects.SubjectDTO
import ro.cadeca.weasylearn.services.SubjectService
import javax.annotation.security.RolesAllowed

@RestController
@RequestMapping("api/subject")
class SubjectController(
        private val subjectService: SubjectService,
        private val subjectFromDtoConverter: SubjectFromDtoConverter,
        private val subjectToDtoConverter: SubjectToDtoConverter
) {

    @GetMapping
    @RolesAllowed(ADMIN)
    fun search(@RequestParam(required = false) query: String?): List<SubjectDTO> =
            subjectService.search(query).map(subjectToDtoConverter::convert)

    @PostMapping
    @RolesAllowed(ADMIN)
    fun createOrUpdate(@RequestBody subject: SubjectDTO) =
            subjectService.create(subjectFromDtoConverter.convert(subject))

    @PutMapping("/{id}/teacher")
    @RolesAllowed(ADMIN)
    fun setTeacher(@PathVariable id: Long, @RequestParam username: String) {
        subjectService.setTeacher(id, username)
    }

    @PutMapping("/{id}/tutor")
    @RolesAllowed(ADMIN)
    fun addTutor(@PathVariable id: Long, @RequestParam username: String) {
        subjectService.addTutor(id, username)
    }

    @PutMapping("/{id}/student")
    @RolesAllowed(ADMIN)
    fun addStudent(@PathVariable id: Long, @RequestParam username: String) {
        subjectService.addStudent(id, username)
    }
}
