package com.m2lifeapps.challenge.ui.main.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.m2lifeapps.data.Status
import com.m2lifeapps.data.remote.response.NewsResponse
import com.m2lifeapps.challenge.BuildConfig
import com.m2lifeapps.challenge.core.local.dao.FavoriteDAO
import com.m2lifeapps.challenge.core.local.entity.FavoriteEntity
import com.m2lifeapps.challenge.core.platform.BaseViewModel
import com.path.pathChallenge.core.extensions.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainListViewModel @Inject constructor(
    val useCase: MainListUseCase,
    val favoriteDAO: FavoriteDAO
) : BaseViewModel() {

    init {
        fetchNews(1, 25)

    }

    private val _event = MutableLiveData<Event<MainListViewEvent>>()
    val event: LiveData<Event<MainListViewEvent>> = _event

    private val _favorited = MutableLiveData<List<FavoriteEntity>>()
    val favorited: LiveData<List<FavoriteEntity>> = _favorited

    private val _newsResponse = MutableLiveData<NewsResponse?>()
    val newsResponse: LiveData<NewsResponse?> = _newsResponse


    fun fetchNews(page: Int, pageSize: Int) {
        val response = useCase.fetchNewsEverything(BuildConfig.PUBLIC_KEY, page, pageSize, "news")
        setLoading(true)
        response.subscribe {
            when (it.status) {
                Status.SUCCESS -> {
                    setLoading(false)
                    _newsResponse.postValue(it.data)
                    getFavoritedList()
                }
                Status.ERROR -> {
                    setLoading(false)
                    handleException(it.error)
                }
            }
        }

    }

    fun changeFavorite(item: NewsResponse.Article, position: Int) {
        viewModelScope.launch {
            val list = favoriteDAO.getAllFavorite()
            val items = list.filter { it.url == item.url }
            if (items.isNotEmpty())
                items[0].url?.let { favoriteDAO.deleteById(it) }
            else favoriteDAO.insert(
                FavoriteEntity(
                    author = item.author,
                    content = item.content,
                    description = item.description,
                    publishedAt = item.publishedAt,
                    sourceId = item.source?.id,
                    sourceName = item.source?.name,
                    title = item.title,
                    url = item.url,
                    urlToImage = item.urlToImage
                )
            )
            _event.postValue(Event(MainListViewEvent.AddFavorite(item, items.isEmpty(), position)))
        }
    }

    private fun getFavoritedList() {
        viewModelScope.launch {
            val response = favoriteDAO.getAllFavorite()
            _favorited.postValue(response)

            _newsResponse.value?.articles?.forEach { it ->
                val isFavorited = _favorited.value?.any { art ->
                    it.url == art.url
                }
                if (isFavorited == true)
                    _newsResponse.value?.articles?.filter { filt -> it.url == filt.url }
                        ?.get(0)?.isFavorite = true
            }
         }
    }

    fun navigateToDetail(item: NewsResponse.Article) {
        _event.postValue(Event(MainListViewEvent.NavigateToDetails(item)))
    }


}