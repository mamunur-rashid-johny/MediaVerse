package com.johny.mediaverse.presentation.podcast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.johny.mediaverse.domain.model.podcast.Podcast
import com.johny.mediaverse.domain.repository.PodcastRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class PodcastViewModel(
    private val repository: PodcastRepository
) : ViewModel() {

    val podcastList: Flow<PagingData<Podcast>> =
        repository.getBestPodcasts()
            .cachedIn(viewModelScope)

    private val _effect = MutableSharedFlow<PodcastEffect>()
    val effect = _effect.asSharedFlow()

    fun onIntent(intent: PodcastIntent)=viewModelScope.launch {
        when (intent) {
            is PodcastIntent.OnItemClick -> {
                _effect.emit(PodcastEffect.NavigateToDetail(intent.podcast))
            }
        }
    }
}