package ro.cadeca.weasylearn.exceptions.subject

class SubjectNotFoundException(subjectId: Long) : RuntimeException("Subject with id: $subjectId does not exist!")
