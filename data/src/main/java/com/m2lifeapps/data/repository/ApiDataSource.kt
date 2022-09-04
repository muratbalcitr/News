package com.m2lifeapps.data.repository

import com.m2lifeapps.data.remote.ProjectService
import javax.inject.Inject

class ApiDataSource @Inject constructor(
    private val projectService: ProjectService
) {

    fun newsEverything(
        apiKey: String,
        currentLoadingPageKey: Int,
        pageSize:Int,
        query:String
    ) = projectService.newsEverything(apiKey,currentLoadingPageKey,pageSize,query)


}
