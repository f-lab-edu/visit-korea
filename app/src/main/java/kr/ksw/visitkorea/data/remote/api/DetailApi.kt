package kr.ksw.visitkorea.data.remote.api

import kr.ksw.visitkorea.data.remote.dto.DetailCommonDTO
import kr.ksw.visitkorea.data.remote.dto.DetailImageDTO
import kr.ksw.visitkorea.data.remote.dto.DetailIntroDTO
import kr.ksw.visitkorea.data.remote.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DetailApi {
    @GET("detailCommon1")
    suspend fun getDetailCommon(
        @Query("numOfRows") numOfRows: Int = 10,
        @Query("pageNo") pageNo: Int = 1,
        @Query("defaultYN") defaultYN: String = "Y",
        @Query("overviewYN") overviewYN: String = "Y",
        @Query("contentId") contentId: Int
    ): ApiResponse<DetailCommonDTO>

    @GET("detailIntro1")
    suspend fun getDetailIntro(
        @Query("numOfRows") numOfRows: Int = 10,
        @Query("pageNo") pageNo: Int = 1,
        @Query("contentId") contentId: Int,
        @Query("contentTypeId") contentTypeId: Int
    ): ApiResponse<DetailIntroDTO>
/*
    @GET("detailInfo1")
    suspend fun getDetailInfo(
        @Query("numOfRows") numOfRows: Int = 10,
        @Query("pageNo") pageNo: Int = 1,
        @Query("contentId") contentId: Int,
        @Query("contentTypeId") contentTypeId: Int
    )
*/
    @GET("detailImage1")
    suspend fun getDetailImage(
        @Query("numOfRows") numOfRows: Int = 10,
        @Query("pageNo") pageNo: Int = 1,
        @Query("subImageYN") subImageYN: String = "Y",
        @Query("imageYN") imageYN: String = "Y",
        @Query("contentId") contentId: Int,
    ): ApiResponse<DetailImageDTO>
}