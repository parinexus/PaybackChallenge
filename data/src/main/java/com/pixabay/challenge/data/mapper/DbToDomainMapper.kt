package com.pixabay.challenge.data.mapper

abstract class DbToDomainMapper<INPUT : Any, OUTPUT : Any> {

    fun toDomain(input: INPUT): OUTPUT = try {
        map(input)
    } catch (throwable: Throwable) {
        throw Exception(
            "Could not map ${input::class.simpleName} to Domain",
            throwable
        )
    }

    protected abstract fun map(input: INPUT): OUTPUT
}