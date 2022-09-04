package com.m2lifeapps.challenge.ui.main.domain

import com.m2lifeapps.data.Resource
import com.m2lifeapps.data.remote.response.NewsResponse
import com.m2lifeapps.data.repository.ApiRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MainListUseCase @Inject constructor(
    val apiRepository: ApiRepository
) {
    fun fetchNewsEverything(
        apiKey: String,
        currentLoadingPageKey: Int,
        pageSize: Int,
        query:String
    ): Observable<Resource<NewsResponse>> {
        return apiRepository.newsEverything(
            apiKey,
            currentLoadingPageKey,
            pageSize,
            query
        ).subscribeOn(Schedulers.io())
    }
}
