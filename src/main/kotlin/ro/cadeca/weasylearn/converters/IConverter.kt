package ro.cadeca.weasylearn.converters

interface IConverter<A, B> {
    fun convert(a: A): B
}
