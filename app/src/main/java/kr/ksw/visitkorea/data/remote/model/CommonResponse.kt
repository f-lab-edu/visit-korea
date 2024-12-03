package kr.ksw.visitkorea.data.remote.model

data class CommonResponse<T>(
    val header: CommonHeader,
    val body: CommonBody<T>,
)
