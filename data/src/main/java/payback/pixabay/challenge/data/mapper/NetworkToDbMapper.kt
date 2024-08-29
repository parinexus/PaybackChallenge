package payback.pixabay.challenge.data.mapper

class DatabaseMapperException(message: String, throwable: Throwable? = null) :
    Exception(message, throwable)

abstract class ApiToDbMapper<INPUT : Any, OUTPUT : Any> {

    fun toDatabase(input: INPUT): OUTPUT = try {
        map(input)
    } catch (throwable: Throwable) {
        throw DatabaseMapperException(
            "Could not map ${input::class.simpleName} to Database",
            throwable
        )
    }

    protected abstract fun map(input: INPUT): OUTPUT
}