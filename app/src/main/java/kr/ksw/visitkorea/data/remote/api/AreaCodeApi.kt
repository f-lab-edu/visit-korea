package kr.ksw.visitkorea.data.remote.api

import kr.ksw.visitkorea.data.remote.dto.AreaCodeDTO
import kr.ksw.visitkorea.data.remote.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AreaCodeApi {
    @GET("areaCode1")
    suspend fun getAreaCode(
        @Query("numOfRows") numOfRows: Int = 20
    ): ApiResponse<AreaCodeDTO>

    @GET("areaCode1")
    suspend fun getSigunguCode(
        @Query("numOfRows") numOfRows: Int = 40,
        @Query("areaCode") areaCode: String,
    ): ApiResponse<AreaCodeDTO>
}