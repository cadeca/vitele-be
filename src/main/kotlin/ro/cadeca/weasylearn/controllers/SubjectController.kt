package ro.cadeca.weasylearn.controllers

import org.springframework.web.bind.annotation.*
import ro.cadeca.weasylearn.model.Subject
import ro.cadeca.weasylearn.services.SubjectService

@RestController
@RequestMapping("api/subject")
class SubjectController(private val subjectService: SubjectService) {

    @GetMapping("all")
    fun findAll(): List<Subject> = subjectService.findAll()

    @PutMapping
    fun create(@RequestBody subject: Subject) = subjectService.createSubject(subject)
}
