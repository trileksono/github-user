package com.tleksono.githubuser.util

import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created by trileksono on 19/08/20
 */
class ErrorUtil {
    companion object {
        fun parseError(throwable: Throwable): String {
            return when (throwable) {
                is HttpException -> {
                    return try {
                        throwable.message()
                    } catch (e: Throwable) {
                        e.printStackTrace()
                        "Something wrong, please try again"
                    }
                }
                is SocketTimeoutException -> "Connection timeout, please try again later"
                is IOException -> "Please check your connection and try again later"
                is UnknownHostException -> "Please check your connection and try again later"
                else -> "Something wrong, please try again"
            }
        }
    }
}
