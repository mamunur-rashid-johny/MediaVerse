package com.johny.mediaverse.presentation.trending

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.johny.mediaverse.domain.repository.TrendingRepository

/**
 * Created by Johny on 4/6/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */
class TrendingViewModel(
    private val repo: TrendingRepository
) : ViewModel() {
    val trending = repo.getTrending().cachedIn(viewModelScope)
}