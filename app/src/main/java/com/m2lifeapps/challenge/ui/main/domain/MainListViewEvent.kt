package com.m2lifeapps.challenge.ui.main.domain

import com.m2lifeapps.data.remote.response.NewsResponse

sealed class MainListViewEvent {
    data class AddFavorite(val item: NewsResponse.Article, val empty: Boolean, val position: Int) : MainListViewEvent()

    data class NavigateToDetails(val item: NewsResponse.Article) : MainListViewEvent()
}