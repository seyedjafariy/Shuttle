@file:Suppress("EXPERIMENTAL_API_USAGE")
@file:OptIn(InternalSerializationApi::class, InternalSerializationApi::class)

package com.seyedjafariy.shared.network

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.util.reflect.*
import io.ktor.utils.io.charsets.*
import io.ktor.utils.io.errors.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer
import kotlin.coroutines.CoroutineContext

suspend inline fun <reified T> HttpClient.executeRequest(
    retry: Int = 3,
    coroutineContext: CoroutineContext = Dispatchers.Default,
    noinline block: HttpRequestBuilder.() -> Unit
): Response<T> = executeRequest(typeInfo<T>(), retry, coroutineContext, block)

suspend fun <T> HttpClient.executeRequest(
    responseType: TypeInfo,
    retry: Int = 3,
    coroutineContext: CoroutineContext = Dispatchers.Default,
    block: HttpRequestBuilder.() -> Unit
): Response<T> = executeRequestInternal(responseType, retry, coroutineContext, block)

private suspend fun <T> HttpClient.executeRequestInternal(
    responseType: TypeInfo,
    retry: Int = 3,
    coroutineContext: CoroutineContext = Dispatchers.Default,
    block: HttpRequestBuilder.() -> Unit
): Response<T> = withContext(coroutineContext) {
    try {
        executeRequestInternal(responseType, block)
    } catch (e: IOException) {
        if (retry > 0) {
            executeRequestInternal(responseType, retry - 1, coroutineContext, block)
        } else {
            e.printStackTrace()
            @Suppress("UNCHECKED_CAST")
            Response.Error(STATUS_INTERRUPTED, e.toString(), e) as Response<T>
        }
    } catch (e: ClientRequestException) {
        println("request failed with an exception= $e")
        e.printStackTrace()

        println("request failed with message:" + e.message)
        val response = e.response
        val errorBody = response.responseToString()
        println("request failed with body= $errorBody")
        @Suppress("UNCHECKED_CAST")
        Response.Error(response.status.value, errorBody, e) as Response<T>
    } catch (e: Exception) {
        println("request failed with an exception= $e")
        e.printStackTrace()

        val statusCode = getServerErrorStatusCode(e)
        @Suppress("UNCHECKED_CAST")
        Response.Error(statusCode, e.message ?: e.toString(), e) as Response<T>
    }
}

private suspend fun <T> HttpClient.executeRequestInternal(
    responseType: TypeInfo,
    block: HttpRequestBuilder.() -> Unit
): Response<T> {
    val response = request<HttpResponse>(block)

    val statusCode = response.status.value

    return if (statusCode in 200..299) {
        parseSuccess(statusCode, response, responseType)
    } else {
        val errorBody = response.responseToString()
        println("request failed with body= $errorBody")
        @Suppress("UNCHECKED_CAST")
        Response.Error(statusCode, errorBody) as Response<T>
    }
}

@Suppress("UNCHECKED_CAST")
private suspend fun <T> parseSuccess(code: Int, response: HttpResponse, type: TypeInfo): Response<T> {
    if (type.type == Unit::class) {
        return Response.Success.EmptyBody(code) as Response<T>
    }

    val serializer = type.kotlinType?.let { serializer(it) } ?: type.type.serializer()

    val text = response.responseToString()

    val parsed = jsonSerializer.decodeFromString(serializer, text)

    @Suppress("UNCHECKED_CAST")
    return Response.Success.WithBody(code, parsed as T)
}

private suspend fun HttpResponse.responseToString(): String =
    readText(Charsets.UTF_8)