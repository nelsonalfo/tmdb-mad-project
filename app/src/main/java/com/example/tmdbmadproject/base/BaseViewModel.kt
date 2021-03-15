package com.example.tmdbmadproject.base

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.properties.Delegates

abstract class BaseViewModel<ViewState : BaseViewState, Action : BaseAction, Intention : BaseIntention>(initialState: ViewState) : ViewModel() {
    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = _viewState

    protected var state: ViewState by Delegates.observable(initialState) { _, _, new -> _viewState.value = new }

    abstract fun sendIntention(intention: Intention)

    protected fun sendAction(action: Action) {
        state = onReduceState(action)
    }

    protected abstract fun onReduceState(action: Action): ViewState

    @VisibleForTesting
    fun changeState(state: ViewState) {
        this.state = state
    }
}

interface BaseViewState

interface BaseAction

interface BaseIntention