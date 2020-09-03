package ro.cadeca.vitele.converters

interface IConverter<A, B> {
    fun convert(a: A): B
}