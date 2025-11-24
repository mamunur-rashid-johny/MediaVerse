package com.johny.mediaverse.di

import com.johny.mediaverse.data.repository.ListenNoteApiImp
import com.johny.mediaverse.data.repository.PodcastDetailsRepositoryImp
import com.johny.mediaverse.data.repository.PodcastRepositoryImp
import com.johny.mediaverse.domain.repository.ListenNoteApi
import com.johny.mediaverse.domain.repository.PodcastDetailRepository
import com.johny.mediaverse.domain.repository.PodcastRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::ListenNoteApiImp).bind<ListenNoteApi>()
    singleOf(::PodcastRepositoryImp).bind<PodcastRepository>()
    singleOf(::PodcastDetailsRepositoryImp).bind<PodcastDetailRepository>()
}