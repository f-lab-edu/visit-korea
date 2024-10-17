package kr.ksw.visitkorea.data.remote.api

import kr.ksw.visitkorea.data.remote.dto.SearchFestivalDTO
import kr.ksw.visitkorea.data.remote.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchFestivalApi {
    @GET("searchFestival1")
    suspend fun searchFestival(
        @Query("arrange") arrange: String = "S",
        @Query("numOfRows") numOfRows: Int,
        @Query("pageNo") pageNo: Int,
        @Query("eventStartDate") eventStartDate: String,
        @Query("eventEndDate") eventEndDate: String,
        @Query("areaCode") areaCode: String? = null,
        @Query("sigunguCode") sigunguCode: String? = null
    ): ApiResponse<SearchFestivalDTO>
}