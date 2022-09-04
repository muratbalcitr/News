package com.m2lifeapps.challenge.ui.favorited.domain

import com.m2lifeapps.data.remote.response.NewsResponse
import com.m2lifeapps.challenge.core.platform.BaseViewModel

sealed class FavoritedViewEvent : BaseViewModel() {
    data class NavigateToDetail(val article: NewsResponse.Article) : FavoritedViewEvent() {

    }

    object RemoveFavorited : FavoritedViewEvent()
}