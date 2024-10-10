package kr.ksw.visitkorea.domain.usecase.util

fun String.toImageUrl(): String = if(contains("https")) this else replace("http", "https")

fun String.toDistForUi(): String {
    val meters = this.split(".")
    return if(meters[0].length > 3) {
        "${String.format("%.1f", meters[0].toFloat() / 1000.0F)}km"
    } else {
        "${meters[0]}m"
    }
}