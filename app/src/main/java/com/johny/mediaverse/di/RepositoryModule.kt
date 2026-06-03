package com.johny.mediaverse.di

import com.johny.mediaverse.core.startup.RemoteConfigDataSource
import com.johny.mediaverse.core.startup.RemoteConfigDataSourceImp
import com.johny.mediaverse.data.repository.BookmarkRepositoryImp
import com.johny.mediaverse.data.repository.ListenNoteApiImp
import com.johny.mediaverse.data.repository.MovieDbApiImp
import com.johny.mediaverse.data.repository.MovieDetailsRepositoryImp
import com.johny.mediaverse.data.repository.MovieRepositoryImp
import com.johny.mediaverse.data.repository.PodcastDetailsRepositoryImp
import com.johny.mediaverse.data.repository.PodcastRepositoryImp
import com.johny.mediaverse.data.repository.SeasonRepositoryImpl
import com.johny.mediaverse.data.repository.TrendingApiImpl
import com.johny.mediaverse.data.repository.TrendingRepositoryImpl
import com.johny.mediaverse.data.repository.TvShowApiImp
import com.johny.mediaverse.data.repository.TvShowDetailsRepositoryImpl
import com.johny.mediaverse.data.repository.TvShowRepositoryImp
import com.johny.mediaverse.domain.repository.BookmarkRepository
import com.johny.mediaverse.domain.repository.ListenNoteApi
import com.johny.mediaverse.domain.repository.MovieDbApi
import com.johny.mediaverse.domain.repository.MovieDetailsRepository
import com.johny.mediaverse.domain.repository.MovieRepository
import com.johny.mediaverse.domain.repository.PodcastDetailRepository
import com.johny.mediaverse.domain.repository.PodcastRepository
import com.johny.mediaverse.domain.repository.SeasonRepository
import com.johny.mediaverse.domain.repository.SimilarTvShowApi
import com.johny.mediaverse.domain.repository.SimilarTvShowApiImpl
import com.johny.mediaverse.domain.repository.TrendingApi
import com.johny.mediaverse.domain.repository.TrendingRepository
import com.johny.mediaverse.domain.repository.TvShowApi
import com.johny.mediaverse.domain.repository.TvShowDetailsRepository
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
    singleOf(::RemoteConfigDataSourceImp).bind<RemoteConfigDataSource>()
    singleOf(::MovieDetailsRepositoryImp).bind<MovieDetailsRepository>()
    singleOf(::TvShowDetailsRepositoryImpl).bind<TvShowDetailsRepository>()
    singleOf(::SeasonRepositoryImpl).bind<SeasonRepository>()
    singleOf(::SimilarTvShowApiImpl).bind<SimilarTvShowApi>()
    singleOf(::TrendingApiImpl).bind<TrendingApi>()
    singleOf(::TrendingRepositoryImpl).bind<TrendingRepository>()
}