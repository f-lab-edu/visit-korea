package kr.ksw.visitkorea.data.remote.api

import kr.ksw.visitkorea.data.remote.dto.DetailCommonDTO
import kr.ksw.visitkorea.data.remote.dto.DetailImageDTO
import kr.ksw.visitkorea.data.remote.dto.DetailInfoDTO
import kr.ksw.visitkorea.data.remote.dto.DetailIntroDTO
import kr.ksw.visitkorea.data.remote.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DetailApi {
    @GET("detailCommon1")
    suspend fun getDetailCommon(
        @Query("defaultYN") defaultYN: String = "Y",
        @Query("overviewYN") overviewYN: String = "Y",
        @Query("mapinfoYN") mapinfoYN: String = "Y",
        @Query("contentId") contentId: String
    ): ApiResponse<DetailCommonDTO>

    @GET("detailIntro1")
    suspend fun getDetailIntro(
        @Query("contentId") contentId: String,
        @Query("contentTypeId") contentTypeId: String
    ): ApiResponse<DetailIntroDTO>

    @GET("detailInfo1")
    suspend fun getDetailInfo(
        @Query("contentId") contentId: String,
        @Query("contentTypeId") contentTypeId: String = "32"
    ): ApiResponse<DetailInfoDTO>

    @GET("detailImage1")
    suspend fun getDetailImage(
        @Query("subImageYN") subImageYN: String = "Y",
        @Query("imageYN") imageYN: String = "Y",
        @Query("contentId") contentId: String,
    ): ApiResponse<DetailImageDTO>
}