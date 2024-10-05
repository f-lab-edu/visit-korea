package kr.ksw.visitkorea.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("sigungu_code")
data class SigunguCodeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val areaCode: String,
    val code: String,
    val name: String,
)