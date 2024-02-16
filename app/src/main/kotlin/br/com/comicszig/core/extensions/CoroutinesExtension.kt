package br.com.comicszig.core.extensions

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.HttpException

typealias MutableLiveResource<T> = MutableLiveData<Resource<T>>

sealed class Resource<T> {
    data class Success<T>(val data: T?) : Resource<T>()
    data class Error<T>(val statusCode: Int?) : Resource<T>()
    class Loading<T> : Resource<T>()

    companion object {
        fun <T> success(data: T?): Resource<T> = Success(data)
        fun <T> error(statusCode: Int?): Resource<T> = Error(statusCode)
        fun <T> loading(): Resource<T> = Loading()
    }
}

fun <T> CoroutineScope.launchResource(
    mutableLiveResource: MutableLiveResource<T> = MutableLiveResource(),
    data: suspend () -> T?,
    onSuccess: suspend (T?) -> Unit = {},
    onError: suspend (Int?, String) -> Unit = { _, _ -> }
): Job {
    mutableLiveResource.loading()

    return launch {
        mutableLiveResource.setupValidationState(data, onSuccess, onError)
    }
}

private suspend fun <T> MutableLiveResource<T>.setupValidationState(
    data: suspend () -> T?,
    onSuccess: suspend (T?) -> Unit = {},
    onError: suspend (Int, String) -> Unit = { _, _ -> }
) {
    try {
        data.invoke().also {
            this.success(it)
            onSuccess.invoke(it)
        }
    } catch (e: HttpException) {
        this.error(e.code())
        onError.invoke(e.code(), deserializeMessage(e.response()?.errorBody()))
    } catch (e: Exception) {
        this.error(0)
        onError.invoke(0, "")
    }
}

private fun deserializeMessage(responseBody: ResponseBody?): String {
    return try {
        val gson = Gson()
        val type = object : TypeToken<ErrorResponse>() {}.type
        val errorResponse: ErrorResponse? = gson.fromJson(responseBody?.charStream(), type)
        return errorResponse?.errors?.first()?.message ?: ""
    } catch (e: Exception) {
        ""
    }
}

private data class ErrorResponse(
    val errors: List<Errors>? = null,
    val errStack: String? = null
)

private data class Errors(
    val message: String? = null
)

fun <T> MutableLiveResource<T>.success(data: T?) {
    value = Resource.success(data)
}

fun <T> MutableLiveResource<T>.error(statusCode: Int?) {
    value = Resource.error(statusCode)
}

fun <T> MutableLiveResource<T>.loading() {
    postValue(Resource.loading())
}
