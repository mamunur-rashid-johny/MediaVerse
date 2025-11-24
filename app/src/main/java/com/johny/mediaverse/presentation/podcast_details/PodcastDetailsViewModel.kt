package com.johny.mediaverse.presentation.podcast_details

import android.content.Context
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.johny.mediaverse.core.domain.utils.onError
import com.johny.mediaverse.core.domain.utils.onSuccess
import com.johny.mediaverse.core.navigation.Destination
import com.johny.mediaverse.core.presentation.utils.toString
import com.johny.mediaverse.core.utils.serializableType
import com.johny.mediaverse.domain.model.podcast.Podcast
import com.johny.mediaverse.domain.repository.PodcastDetailRepository
import com.johny.mediaverse.presentation.podcast_details.PodcastDetailsEffect.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.reflect.typeOf

class PodcastDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: PodcastDetailRepository,
    private val context: Context
) : ViewModel() {

    private val typeMap = mapOf(typeOf<Podcast>() to serializableType<Podcast>())
    val route = savedStateHandle.toRoute<Destination.PodcastDetailRoute>(typeMap)

    var state = MutableStateFlow(PodcastDetailsState())
        private set

    private val _effect = MutableSharedFlow<PodcastDetailsEffect>()
    val effect = _effect.asSharedFlow()

    private fun getPodcastDetails() = viewModelScope.launch(Dispatchers.IO) {
        state.update {
            it.copy(isLoading = true)
        }
        repository.getPodcastDetails(route.podcast.id)
            .onSuccess { details ->
                state.update {
                    it.copy(
                        podcastDetails = details,
                        isLoading = false
                    )
                }
            }
            .onError { error ->
                Log.e("PodcastDetailError", error.toString(context))
                state.update {
                    it.copy(
                        isLoading = false
                    )
                }
            }
    }

    fun onIntent(intent: PodcastDetailsIntent)=viewModelScope.launch {
        when (intent) {
            is PodcastDetailsIntent.OnAudioPlayIntent ->{
                _effect.emit(NavigateToAudioPlayer(intent.episodeModel))
            }

            PodcastDetailsIntent.OnBackPressed -> {
                _effect.emit(OnBackPressed)
            }
        }
    }

    init {
        getPodcastDetails()
    }
}