package com.seyedjafariy.shared.network

@Suppress("MemberVisibilityCanBePrivate")
sealed class Response<T>(
    val statusCode: Int
) {
    sealed class Success<K>(
        status: Int,
    ) : Response<K>(status) {

        data class WithBody<K>(
            val status: Int,
            val body: K
        ) : Success<K>(status)

        data class EmptyBody(
            val status: Int,
        ) : Success<Unit>(status)
    }

    data class Error(
        val status: Int,
        val body: String,
        val throwable: Throwable? = null
    ) : Response<Nothing>(status)

    val isSuccessful: Boolean
        get() = statusCode in 200..299

    val isNotSuccessful: Boolean
        get() = !isSuccessful

    val success: Success<T>
        get() = this as Success<T>

    val error: Error
        get() = this as Error
}


val <T> Response<T>.successBody: T
    get() = (this as Response.Success.WithBody<T>).body

fun <T> Response<T>.bodyOrNull(whenNull: (Response.Error) -> T): T {
    return if (isSuccessful) {
        successBody
    } else {
        whenNull((this as Response.Error))
    }
}

fun <T, R> Response<T>.mapBody(mapper: (T) -> R): Response<R> {
    return if (this is Response.Success.WithBody<T>) {
        Response.Success.WithBody<R>(this.status, mapper(this.body))
    } else {
        @Suppress("UNCHECKED_CAST")
        this as Response<R>
    }
}
