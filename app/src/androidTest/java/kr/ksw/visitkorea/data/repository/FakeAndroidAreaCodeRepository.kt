package kr.ksw.visitkorea.data.repository

import kr.ksw.visitkorea.data.mapper.toItems
import kr.ksw.visitkorea.data.remote.dto.AreaCodeDTO
import kr.ksw.visitkorea.data.remote.model.ApiResponse
import kr.ksw.visitkorea.data.remote.model.CommonBody
import kr.ksw.visitkorea.data.remote.model.CommonHeader
import kr.ksw.visitkorea.data.remote.model.CommonItem
import kr.ksw.visitkorea.data.remote.model.CommonResponse

class FakeAndroidAreaCodeRepository : AreaCodeRepository {
    private var areaCodeResponse = ApiResponse(
        CommonResponse(
            CommonHeader("0000", "OK"),
            CommonBody(
                10,
                1,
                17,
                CommonItem(listOf(
                    AreaCodeDTO("1","서울"),
                    AreaCodeDTO("2","인천"),
                    AreaCodeDTO("3","대전"),
                    AreaCodeDTO("4","대구"),
                    AreaCodeDTO("5","광주"),
                    AreaCodeDTO("6","부산"),
                    AreaCodeDTO("7","울산"),
                    AreaCodeDTO("8","세종특별자치시"),
                    AreaCodeDTO("31","경기도"),
                    AreaCodeDTO("32","강원특별자치도")
                ))
            )
        )
    )

    private var sigunguCodeResponse1 = ApiResponse(
        CommonResponse(
            CommonHeader("0000", "OK"),
            CommonBody(
                5,
                1,
                25,
                CommonItem(listOf(
                    AreaCodeDTO("1","강남구"),
                    AreaCodeDTO("2","강동구"),
                    AreaCodeDTO("3","강북구"),
                    AreaCodeDTO("4","강서구"),
                    AreaCodeDTO("5","관악구")
                ))
            )
        )
    )
    private var sigunguCodeResponse2 = ApiResponse(
        CommonResponse(
            CommonHeader("0000", "OK"),
            CommonBody(
                5,
                1,
                31,
                CommonItem(listOf(
                    AreaCodeDTO("1","가평군"),
                    AreaCodeDTO("2","고양시"),
                    AreaCodeDTO("3","과천시"),
                    AreaCodeDTO("4","광명시"),
                    AreaCodeDTO("5","광주시")
                ))
            )
        )
    )

    private var sigunguRequest = mapOf(
        "1" to sigunguCodeResponse1,
        "31" to sigunguCodeResponse2
    )

    override suspend fun getAreaCode(): Result<List<AreaCodeDTO>> = runCatching {
        areaCodeResponse.toItems()
    }

    override suspend fun getSigunguCode(areaCode: String): Result<List<AreaCodeDTO>> = runCatching {
        sigunguRequest[areaCode]!!.toItems()
    }
}