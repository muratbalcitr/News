package com.m2lifeapps.challenge.core.platform

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.m2lifeapps.data.remote.response.ErrorResponse
import com.path.pathChallenge.core.extensions.Event
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    private val _baseEvent = MutableLiveData<Event<BaseViewEvent>>()
    val baseEvent: LiveData<Event<BaseViewEvent>> = _baseEvent

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading


    internal var disposable = CompositeDisposable()

    private fun disposeSubscriptions() {
        if (!disposable.isDisposed) disposable.dispose()
    }

    fun setLoading(loading: Boolean) = _loading.postValue(loading)

    open fun handleException(e: ErrorResponse?) {
        _baseEvent.postValue(Event(BaseViewEvent.ErrorMessage(e)))
    }

    override fun onCleared() {
        disposeSubscriptions()
        super.onCleared()
    }
}
