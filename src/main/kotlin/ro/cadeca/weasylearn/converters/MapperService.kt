package ro.cadeca.weasylearn.converters

import ma.glasnost.orika.MapperFactory
import ma.glasnost.orika.impl.DefaultMapperFactory
import ma.glasnost.orika.metadata.ClassMapBuilder
import org.springframework.stereotype.Service
import kotlin.reflect.KClass

@Service
class MapperService {
    val mapperFactory: MapperFactory = DefaultMapperFactory.Builder().build()

    final inline fun <reified A, reified B : Any> map(source: A): B =
            map(source, B::class)

    final inline fun <reified A, reified B : Any> map(source: A, kClass: KClass<B>): B =
            mapperFactory.mapperFacade.map(source, kClass.java)
}
