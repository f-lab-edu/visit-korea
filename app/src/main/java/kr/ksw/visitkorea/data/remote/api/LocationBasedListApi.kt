package kr.ksw.visitkorea.data.remote.api

import kr.ksw.visitkorea.data.remote.dto.LocationBasedDTO
import kr.ksw.visitkorea.data.remote.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationBasedListApi {
    @GET("locationBasedList1")
    suspend fun getLocationBasedListByContentType(
        @Query("radius") radius: String = "5000",
        @Query("numOfRows") numOfRows: Int,
        @Query("pageNo") pageNo: Int,
        @Query("mapX") mapX: String,
        @Query("mapY") mapY: String,
        @Query("contentTypeId") contentTypeId: String
    ): ApiResponse<LocationBasedDTO>
}