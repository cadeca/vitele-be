package ro.cadeca.weasylearn.services

import ma.glasnost.orika.MapperFactory
import ma.glasnost.orika.impl.DefaultMapperFactory
import ma.glasnost.orika.metadata.ClassMapBuilder
import org.springframework.stereotype.Service

@Service
class MapperService {
    val mapperFactory: MapperFactory = DefaultMapperFactory.Builder().build()

    final inline fun <reified A, reified B> map(source: A): B =
            mapperFactory.getMapperFacade(A::class.java, B::class.java).map(source)

    final inline fun <reified A, reified B> configureClassMap(configure: (builder: ClassMapBuilder<A, B>) -> Unit) {
        val classMapBuilder = mapperFactory.classMap(A::class.java, B::class.java)
        configure(classMapBuilder)
        classMapBuilder.register()
    }
}
