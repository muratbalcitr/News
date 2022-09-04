package com.m2lifeapps.data.utils

import com.google.gson.Gson
import com.m2lifeapps.data.Resource
import com.m2lifeapps.data.remote.response.ErrorResponse
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException

fun <T : Any> Single<T>.toObservable(
    emitter: ObservableEmitter<Resource<T>>
): Disposable {

    return this
        .subscribeOn(Schedulers.io())
        .subscribe(
            {

                emitter.onNext(
                    Resource.success(it)
                )
                emitter.onComplete()
            },
            {
                if (it is HttpException)
                    try {

                        emitter.onNext(
                            Resource.error(
                                Gson().fromJson(
                                    (it.response()?.errorBody()?.string()),
                                    ErrorResponse::class.java
                                )
                            )
                        )
                    }catch (e:Exception){}

                emitter.onComplete()
            }
        )
}
