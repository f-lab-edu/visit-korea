package kr.ksw.visitkorea.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("area_code")
data class AreaCodeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val code: String,
    val name: String
)