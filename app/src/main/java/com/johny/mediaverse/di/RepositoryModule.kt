package com.johny.mediaverse.di

import com.johny.mediaverse.data.repository.BookmarkRepositoryImp
import com.johny.mediaverse.data.repository.ListenNoteApiImp
import com.johny.mediaverse.data.repository.MovieDbApiImp
import com.johny.mediaverse.data.repository.PodcastDetailsRepositoryImp
import com.johny.mediaverse.data.repository.PodcastRepositoryImp
import com.johny.mediaverse.domain.repository.BookmarkRepository
import com.johny.mediaverse.domain.repository.ListenNoteApi
import com.johny.mediaverse.domain.repository.MovieDbApi
import com.johny.mediaverse.domain.repository.MovieRepository
import com.johny.mediaverse.data.repository.MovieRepositoryImp
import com.johny.mediaverse.data.repository.TvShowApiImp
import com.johny.mediaverse.data.repository.TvShowRepositoryImp
import com.johny.mediaverse.domain.repository.PodcastDetailRepository
import com.johny.mediaverse.domain.repository.PodcastRepository
import com.johny.mediaverse.domain.repository.TvShowApi
import com.johny.mediaverse.domain.repository.TvShowRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::ListenNoteApiImp).bind<ListenNoteApi>()
    singleOf(::PodcastRepositoryImp).bind<PodcastRepository>()
    singleOf(::PodcastDetailsRepositoryImp).bind<PodcastDetailRepository>()
    singleOf(::BookmarkRepositoryImp).bind<BookmarkRepository>()
    singleOf(::MovieDbApiImp).bind<MovieDbApi>()
    singleOf(::MovieRepositoryImp).bind<MovieRepository>()
    singleOf(::TvShowApiImp).bind<TvShowApi>()
    singleOf(::TvShowRepositoryImp).bind<TvShowRepository>()
}