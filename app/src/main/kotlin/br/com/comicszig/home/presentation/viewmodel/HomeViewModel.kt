package br.com.comicszig.home.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.comicszig.core.components.Error
import br.com.comicszig.core.components.ViewState
import br.com.comicszig.core.extensions.launchResource
import br.com.comicszig.home.domain.HomeRepository
import br.com.comicszig.home.model.ComicItemModel

class HomeViewModel(
    private val repository: HomeRepository
) : ViewModel() {

    private val _comics = mutableStateOf(ViewState.initial<ComicItemModel>())
    val comics = _comics

    private val _refreshing = mutableStateOf(false)
    val refreshing = _refreshing

    fun getComics() {
        viewModelScope.launchResource(
            data = {
                _comics.value = ViewState.loading()
                _refreshing.value = true
                repository.getComics()
            },
            onSuccess = { response ->
                _refreshing.value = false
                response?.let {
                    _comics.value = ViewState.success(response)
                } ?: kotlin.run {
                    _comics.value = ViewState.error(Error(code = 0, message = ""))
                }
            },
            onError = { code, message ->
                _refreshing.value = false
                _comics.value = ViewState.error(Error(code = code, message = message))
            }
        )
    }

    fun changeRefreshingState(state: Boolean) {
        _refreshing.value = state
    }
}
