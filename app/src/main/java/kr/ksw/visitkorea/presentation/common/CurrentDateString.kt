package kr.ksw.visitkorea.presentation.common

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun getCurrentDateString(): String {
    val pattern = DateTimeFormatter.ofPattern("yyyyMMdd")
    val patterned: String = LocalDateTime.now().format(pattern)
    return patterned
}