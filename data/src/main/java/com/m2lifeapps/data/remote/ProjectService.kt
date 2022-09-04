package com.m2lifeapps.data.remote

import com.m2lifeapps.data.remote.response.NewsResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ProjectService {
    object News {
        const val everything = "everything"
     }
    @GET(News.everything)
    fun newsEverything(
        @Query("apiKey") apikey: String,
        @Query("page") currentLoadingPageKey: Int,
        @Query("pageSize") pageSize: Int,
        @Query("q") query: String
    ): Single<NewsResponse>

}
