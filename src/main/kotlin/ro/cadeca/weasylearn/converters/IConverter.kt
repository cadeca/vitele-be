package ro.cadeca.weasylearn.converters

interface IConverter<SOURCE, TARGET> {
    fun convert(source: SOURCE): TARGET
}
