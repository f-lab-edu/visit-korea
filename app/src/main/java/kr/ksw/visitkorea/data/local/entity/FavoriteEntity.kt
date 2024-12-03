package kr.ksw.visitkorea.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("favorite_table")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val firstImage: String,
    val address: String,
    val dist: String?,
    val contentId: String,
    val contentTypeId: String,
    val eventStartDate: String?,
    val eventEndDate: String?,
)
