package ro.cadeca.weasylearn.converters.subject

import ro.cadeca.weasylearn.annotations.Converter
import ro.cadeca.weasylearn.converters.IConverter
import ro.cadeca.weasylearn.converters.MapperService
import ro.cadeca.weasylearn.converters.factory.UserDocumentToModelConverterFactory
import ro.cadeca.weasylearn.model.Student
import ro.cadeca.weasylearn.model.Subject
import ro.cadeca.weasylearn.model.Teacher
import ro.cadeca.weasylearn.persistence.subject.SubjectEntity
import ro.cadeca.weasylearn.persistence.user.UserRepository

@Converter
class SubjectFromEntityConverter(
        private val mapperService: MapperService,
        private val userRepository: UserRepository,
        private val userDocumentToModelConverterFactory: UserDocumentToModelConverterFactory
) : IConverter<SubjectEntity, Subject> {

    init {
        mapperService.registerCustomFactory { username, _ -> getUserModelFromUsername(username) }

        mapperService.registerCustomFactory { username, _ -> getUserModelFromUsername(username) as Student }

        mapperService.registerCustomFactory { username, _ -> getUserModelFromUsername(username) as Teacher }
    }

    private fun getUserModelFromUsername(username: Any) =
            (username as String).let(userRepository::findByUsername)
                    ?.let { userDocumentToModelConverterFactory.getDocumentToModelConverter(it).convert(it) }

    override fun convert(source: SubjectEntity): Subject =
            mapperService.map(source)
}
