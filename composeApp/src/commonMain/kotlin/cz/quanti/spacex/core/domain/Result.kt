package cz.quanti.spacex.core.domain

// A sealed interface representing the result of an operation.
// It can be either a successful result with data, or an error with a domain-specific error type.
sealed interface Result<out D, out E: Error> {

    // Represents a successful result containing some data of type D.
    data class Success<out D>(val data: D): Result<D, Nothing>
    // Represents an error result containing a domain error of type E.
    data class Error<out E: cz.quanti.spacex.core.domain.Error>(val error: E):
        Result<Nothing, E>
}

// Transforms the successful data value using the provided mapping function.
// If the result is an error, it is returned unchanged.
inline fun <T, E: Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when(this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
    }
}

// Converts any successful result into an EmptyResult (Unit as data).
// Useful when you only care about success/failure, not the payload.
fun <T, E: Error> Result<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map {  }
}

// Executes the given action only if the result is successful.
// Returns the original result to allow fluent chaining.
inline fun <T, E: Error> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {
    return when(this) {
        is Result.Error -> this
        is Result.Success -> {
            action(data)
            this
        }
    }
}
// Executes the given action only if the result is an error.
// Returns the original result to allow fluent chaining.
inline fun <T, E: Error> Result<T, E>.onError(action: (E) -> Unit): Result<T, E> {
    return when(this) {
        is Result.Error -> {
            action(error)
            this
        }
        is Result.Success -> this
    }
}

// A convenience type alias for results that don't return any data,
// but can still fail with an error of type E.
typealias EmptyResult<E> = Result<Unit, E>