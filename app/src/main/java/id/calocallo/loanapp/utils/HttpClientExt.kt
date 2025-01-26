package id.calocallo.loanapp.utils

import android.util.Log
import id.calocallo.mybookpedia.core.domain.DataError
import id.calocallo.mybookpedia.core.domain.Result
import retrofit2.Response
import java.io.IOException

suspend fun <T> safeApiCall(
    apiCall: suspend () -> Response<T>
): Result<T, DataError.Remote> {
    return try {
        val response = apiCall()
        responseToResult(response)
    } catch (e: Exception) {
        when (e) {
            is IOException -> Result.Error(DataError.Remote.NO_INTERNET)
            else -> {
                Log.e("xxx", "e:$e")
                Result.Error(DataError.Remote.UNKNOWN)
            }
        }
    }
}

fun <T> responseToResult(
    response: Response<T>
): Result<T, DataError.Remote> {
    return when {
        response.isSuccessful -> {
            val body = response.body()
            if (body != null) {
                Result.Success(body)
            } else {
                Result.Error(DataError.Remote.SERIALIZATION)
            }
        }

        response.code() == 408 -> Result.Error(DataError.Remote.REQUEST_TIMEOUT)
        response.code() == 429 -> Result.Error(DataError.Remote.TOO_MANY_REQUEST)
        response.code() in 500..599 -> Result.Error(DataError.Remote.SERVER)
        else -> Result.Error(DataError.Remote.UNKNOWN)
    }
}