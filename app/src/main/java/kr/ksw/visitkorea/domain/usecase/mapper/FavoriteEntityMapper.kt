package kr.ksw.visitkorea.domain.usecase.mapper

import kr.ksw.visitkorea.data.local.entity.FavoriteEntity
import kr.ksw.visitkorea.domain.model.Favorite

fun FavoriteEntity.toFavorite(): Favorite = Favorite(
    id = id ?: 0,
    title = title,
    firstImage = firstImage,
    address = address,
    dist = dist,
    contentId = contentId,
    contentTypeId = contentTypeId,
    eventEndDate = eventStartDate,
    eventStartDate = eventEndDate
)

fun Favorite.toEntity(): FavoriteEntity = FavoriteEntity(
    id = id,
    title = title,
    firstImage = firstImage ,
    address = address,
    dist = dist,
    contentId = contentId,
    contentTypeId = contentTypeId ,
    eventStartDate = eventStartDate,
    eventEndDate = eventEndDate,
)