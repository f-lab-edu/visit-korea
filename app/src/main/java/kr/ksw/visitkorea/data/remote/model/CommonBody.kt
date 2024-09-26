package kr.ksw.visitkorea.data.remote.model

data class CommonBody<T>(
    val numOfRows: Int,
    val pageNo: Int,
    val totalCount: Int,
    val items: CommonItem<T>
)