package com.m2lifeapps.challenge.ui.detail.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.m2lifeapps.data.remote.response.NewsResponse
import com.m2lifeapps.challenge.core.platform.BaseViewModel
import com.path.pathChallenge.core.extensions.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    val useCase: DetailUseCase
) : BaseViewModel() {


    private val _event = MutableLiveData<Event<DetailViewEvent>>()
    val event: LiveData<Event<DetailViewEvent>> = _event

    private val _newsResponse = MutableLiveData<NewsResponse.Article>()
    val newsResponse: LiveData<NewsResponse.Article> = _newsResponse
    fun newsResponse(response: NewsResponse.Article) {
        _newsResponse.postValue(response)
    }

}