package kr.ksw.visitkorea.data.mapper

import kr.ksw.visitkorea.data.remote.model.ApiResponse

fun <T> ApiResponse<T>.toItems(): List<T> = response.body.items.item