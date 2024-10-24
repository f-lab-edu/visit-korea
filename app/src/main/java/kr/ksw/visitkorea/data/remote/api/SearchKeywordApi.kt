package kr.ksw.visitkorea.data.remote.api

import kr.ksw.visitkorea.data.remote.dto.LocationBasedDTO
import kr.ksw.visitkorea.data.remote.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchKeywordApi {
    @GET("searchKeyword1")
    suspend fun searchListByKeyword(
        @Query("arrange") arrange: String = "Q",
        @Query("numOfRows") numOfRows: Int,
        @Query("pageNo") pageNo: Int,
        @Query("keyword") keyword: String,
        @Query("contentTypeId") contentTypeId: String? = null
    ): ApiResponse<LocationBasedDTO>
}