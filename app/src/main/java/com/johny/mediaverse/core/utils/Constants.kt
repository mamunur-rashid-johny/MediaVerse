package com.johny.mediaverse.core.utils

object Constants {

    object PreferenceKeys{
        const val SHOW_ONBOARDING = "SHOW_ONBOARDING"
    }

    object ListenNoteUrls{
        const val LISTEN_NOTE_BASE_URL = "https://listen-api.listennotes.com/api/v2/"
        const val BEST_PODCASTS = "best_podcasts"
        const val PODCAST_DETAILS = "podcasts/"
    }

    object Miscellaneous{
        const val PREF_NAME = "MEDIA_VERSE_APP"
        const val STARTING_PAGE_INDEX = 1
        const val DATABASE_NAME = "media_verse_db"
    }

    object MovieDbUrl{
        const val MOVIE_DB_BASE_URL = "https://api.themoviedb.org/3/"
        const val DISCOVER_MOVIE = "discover/movie"
        const val DISCOVER_TV_SHOW = "discover/tv"
        const val IMAGE_ROOT_PATH ="https://image.tmdb.org/t/p/"
    }

    object ApiQueryParam{
        const val INCLUDE_ADULT = "include_adult"
        const val INCLUDE_ADULT_VALUE = true
        const val INCLUDE_VIDEO = "include_video"
        const val INCLUDE_VIDEO_VALUE = false
        const val LANGUAGE = "language"
        const val LANGUAGE_VALUE = "en-US"
        const val SORT_BY = "sort_by"
        const val SORT_BY_VALUE = "popularity.desc"
        const val INCLUDE_NULL_FIRST_AIR_DATES = "include_null_first_air_dates"
        const val INCLUDE_NULL_FIRST_AIR_DATES_VALUES = false
        const val PAGE = "page"
    }
}