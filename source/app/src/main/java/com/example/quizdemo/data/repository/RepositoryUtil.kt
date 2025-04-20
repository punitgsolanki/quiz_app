package com.example.quizdemo.data.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.quizdemo.MyBaseApp
import com.example.quizdemo.R
import com.example.quizdemo.data.model.ErrorMessage
import com.example.quizdemo.data.model.Resource
import com.example.quizdemo.utils.CommonUtils.checkInternetConnected
import com.example.quizdemo.utils.extensions.nullSafe
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response
import java.net.HttpURLConnection.HTTP_NOT_FOUND
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun <T> retrofitCall(
    call: Call<T>,
    isAuth: Boolean = true,
): MutableLiveData<Any> {

    val data = MutableLiveData<Any>()
    val context: Context = MyBaseApp.applicationContext()

    if (!context.checkInternetConnected()) {
        data.value =
            Resource.NoInternetError<String>(context.getString(R.string.default_internet_message))
        return data
    }

    /*val handler = CoroutineExceptionHandler { _, t ->
        data.value = Resource.Error<String>(t.message.nullSafe())
    }*/

    data.value = Resource.Loading<Boolean>(true)

    call.enqueue(object : retrofit2.Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            data.value = Resource.Loading<Boolean>(false)
            if (response.isSuccessful && response.body() != null) {
                data.value = Resource.Success(response.body())
            } else if (response.code() == HTTP_NOT_FOUND) {
                data.value = Resource.Error<String>(context.getString(R.string.msg_invalid_url))
            } else if (response.code() == HTTP_UNAUTHORIZED && isAuth) {
                /**
                 * Here we get 401 error so here we will call refresh token api and api will give us new access and refresh token,
                 * this will only be call if api required access token to call api
                 */
                // getAccessTokenFromRefreshToken()
            } else if (response.errorBody() != null) {
                val errorMessage =
                    Gson().fromJson(response.errorBody()?.string(), ErrorMessage::class.java)
                data.value = Resource.Error<String>(errorMessage.message.nullSafe())
            }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            t.printStackTrace()
            data.value = Resource.Loading<Boolean>(false)
            if (t is UnknownHostException || t is SocketTimeoutException || t is SocketException)
                data.value =
                    Resource.Error<String>(context.getString(R.string.default_internet_message))
            else data.value = Resource.Error<String>(
                t.localizedMessage ?: context.getString(R.string.msg_something_went_wrong)
            )
        }

    })

    return data
}
