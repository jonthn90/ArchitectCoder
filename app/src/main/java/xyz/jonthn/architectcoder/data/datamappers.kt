package xyz.jonthn.architectcoder.data

import xyz.jonthn.architectcoder.data.database.Movie as RoomMovie
import xyz.jonthn.architectcoder.data.server.Movie as ServerMovie
import xyz.jonthn.domain.Movie as DomainMovie

fun DomainMovie.toRoomMovie(): RoomMovie =
    RoomMovie(
        id,
        title,
        overview,
        releaseDate,
        posterPath,
        backdropPath,
        originalLanguage,
        originalTitle,
        popularity,
        voteAverage,
        favorite
    )

fun RoomMovie.toDomainMovie(): DomainMovie = DomainMovie(
    id,
    title,
    overview,
    releaseDate,
    posterPath,
    backdropPath,
    originalLanguage,
    originalTitle,
    popularity,
    voteAverage,
    favorite
)

fun ServerMovie.toDomainMovie(): DomainMovie =
    DomainMovie(
        0,
        title,
        overview,
        releaseDate,
        posterPath,
        backdropPath ?: posterPath,
        originalLanguage,
        originalTitle,
        popularity,
        voteAverage,
        false
    )