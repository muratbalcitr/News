package com.m2lifeapps.data.repository

import com.m2lifeapps.data.Resource
import com.m2lifeapps.data.remote.response.NewsResponse
import com.m2lifeapps.data.utils.toObservable
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val apiDataSource: ApiDataSource
) {

    fun newsEverything(
        apiKey: String,
        currentLoadingPageKey: Int,
        pageSize:Int,
        query:String
    ): Observable<Resource<NewsResponse>> {
        return Observable.create { emitter ->
            apiDataSource.newsEverything(apiKey,currentLoadingPageKey,pageSize,query)
                .toObservable(emitter)
        }
    }
}
