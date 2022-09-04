package com.m2lifeapps.data

import com.m2lifeapps.data.remote.response.ErrorResponse

data class Resource<out T>(
    val status: Status,
    val data: T?,
    val error: ErrorResponse?
) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(error: ErrorResponse): Resource<T> {
            return Resource(Status.ERROR, null, error)
        }


    }
}

