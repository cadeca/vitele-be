package ro.cadeca.vitele.controllers

import org.springframework.web.bind.annotation.*
import ro.cadeca.vitele.model.Subject
import ro.cadeca.vitele.services.SubjectService

@RestController
@RequestMapping("api/subject")
class SubjectController(private val subjectService: SubjectService) {

    @GetMapping("all")
    fun findAll(): List<Subject> {
        return subjectService.findAll()
    }

    @PutMapping
    fun create(@RequestBody subject: Subject) {
        subjectService.createSubject(subject)
    }
}