package ro.cadeca.weasylearn.controllers

import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import ro.cadeca.weasylearn.annotations.validation.ValidSubject
import ro.cadeca.weasylearn.config.Roles.Companion.ADMIN
import ro.cadeca.weasylearn.config.Roles.Companion.STUDENT
import ro.cadeca.weasylearn.config.Roles.Companion.TEACHER
import ro.cadeca.weasylearn.converters.subject.SubjectFromDtoConverter
import ro.cadeca.weasylearn.converters.subject.SubjectToDtoConverter
import ro.cadeca.weasylearn.dto.subjects.SubjectDTO
import ro.cadeca.weasylearn.dto.subjects.SubjectSaveDTO
import ro.cadeca.weasylearn.services.SubjectService
import javax.annotation.security.RolesAllowed
import javax.validation.Valid

@RestController
@RequestMapping("api/subject")
@Validated
class SubjectController(
        private val subjectService: SubjectService,
        private val subjectFromDtoConverter: SubjectFromDtoConverter,
        private val subjectToDtoConverter: SubjectToDtoConverter
) {

    @GetMapping("/search")
    @RolesAllowed(ADMIN, TEACHER, STUDENT)
    fun search(@RequestParam(required = false) query: String?): List<SubjectDTO> =
            subjectService.search(query).map(subjectToDtoConverter::convert)

    @GetMapping
    @RolesAllowed(ADMIN, TEACHER, STUDENT)
    fun getUsersSubjects(): List<SubjectDTO> =
            subjectService.getUsersSubjects().map(subjectToDtoConverter::convert)


    @GetMapping("/{id}")
    @RolesAllowed
    fun findById(@PathVariable id: Long): SubjectDTO {
        return subjectService.findById(id).let(subjectToDtoConverter::convert)
    }

    @GetMapping("isEditable/{id}")
    @RolesAllowed
    fun userCanEdit(@PathVariable id: Long): Boolean {
        return subjectService.userCanEdit(id)
    }

    @PostMapping
    @RolesAllowed(ADMIN)
    fun save(@RequestBody @Valid @ValidSubject subject: SubjectSaveDTO) =
            subjectService.save(subject)

    @PutMapping("/{id}/teacher")
    @RolesAllowed(ADMIN)
    fun setTeacher(@PathVariable id: Long, @RequestParam username: String) {
        subjectService.setTeacher(id, username)
    }

    @PutMapping("/{id}/tutor")
    @RolesAllowed(ADMIN, TEACHER)
    fun addTutor(@PathVariable id: Long, @RequestParam username: String) {
        subjectService.addTutor(id, username)
    }

    @PutMapping("/{id}/student")
    @RolesAllowed(ADMIN, TEACHER)
    fun addStudent(@PathVariable id: Long, @RequestParam username: String) {
        subjectService.addStudent(id, username)
    }

    @DeleteMapping("/{id}/tutor")
    @RolesAllowed(ADMIN, TEACHER)
    fun removeTutor(@PathVariable id: Long, @RequestParam username: String) {
        subjectService.removeTutor(id, username)
    }

    @DeleteMapping("/{id}/student")
    @RolesAllowed(ADMIN, TEACHER)
    fun removeStudent(@PathVariable id: Long, @RequestParam username: String) {
        subjectService.removeStudent(id, username)
    }
}
