package kr.ksw.visitkorea.data.remote.model

data class ApiResponse<T>(
    val response: CommonResponse<T>
)
