package com.m2lifeapps.challenge.ui.favorited.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.m2lifeapps.data.remote.response.NewsResponse
import com.m2lifeapps.challenge.core.local.dao.FavoriteDAO
import com.m2lifeapps.challenge.core.local.entity.FavoriteEntity
import com.m2lifeapps.challenge.core.platform.BaseViewModel
import com.path.pathChallenge.core.extensions.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritedViewModel @Inject constructor(
    val favoriteDAO: FavoriteDAO
) : BaseViewModel() {

    init {
        getFavoritedList()
    }

    private val _event = MutableLiveData<Event<FavoritedViewEvent>>()
    val event: LiveData<Event<FavoritedViewEvent>> = _event

    private val _favorited = MutableLiveData<List<FavoriteEntity>>()
    val favorited: LiveData<List<FavoriteEntity>> = _favorited

    val isNotFavorited = MutableLiveData<Boolean>(false)

    fun getFavoritedList() {
        viewModelScope.launch {
            val response = favoriteDAO.getAllFavorite()
            _favorited.postValue(response)
            if (response.isEmpty())
                isNotFavorited.postValue(true)
            else
                isNotFavorited.postValue(false)
        }
    }

    fun navigateToDetail(item: FavoriteEntity) {
       _event.postValue(Event(FavoritedViewEvent.NavigateToDetail( NewsResponse.Article(
           item.author.toString(),
           item.content.toString(),
           item.description.toString(),
           item.publishedAt.toString(),
           title = item.title.toString(),
           url = item.url.toString(),
           urlToImage = item.urlToImage
       ))))

    }

    fun deleteItem(item: FavoriteEntity) {
        viewModelScope.launch {
            favoriteDAO.deleteById(item.url.toString())
            _event.postValue(Event(FavoritedViewEvent.RemoveFavorited))
        }
    }
}