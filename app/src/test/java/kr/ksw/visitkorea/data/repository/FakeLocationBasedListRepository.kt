package kr.ksw.visitkorea.data.repository

import kr.ksw.visitkorea.data.mapper.toItems
import kr.ksw.visitkorea.data.remote.dto.LocationBasedDTO
import kr.ksw.visitkorea.data.remote.model.ApiResponse
import kr.ksw.visitkorea.data.remote.model.CommonBody
import kr.ksw.visitkorea.data.remote.model.CommonHeader
import kr.ksw.visitkorea.data.remote.model.CommonItem
import kr.ksw.visitkorea.data.remote.model.CommonResponse

class FakeLocationBasedListRepository : LocationBasedListRepository {
    private val locationList = listOf(
        LocationBasedDTO(
            "서울특별시 중구 청계천로 40 한국관광공사 서울센터",
            "1",
            "24",
            "2834048",
            "14",
            "11.97810236270856",
            "http://tong.visitkorea.or.kr/cms/resource/57/2833957_image2_1.jpg",
            "http://tong.visitkorea.or.kr/cms/resource/57/2833957_image3_1.jpg",
            "126.9816428989",
            "37.5685818430",
            "",
            "하이커 그라운드"
        ),
        LocationBasedDTO(
            "서울특별시 중구 청계천로 40 한국관광공사 서울센터",
            "1",
            "24",
            "2993194",
            "14",
            "15.2102151022",
            "http://tong.visitkorea.or.kr/cms/resource/11/2987811_image2_1.jpg",
            "http://tong.visitkorea.or.kr/cms/resource/11/2987811_image3_1.jpg",
            "126.9816373430",
            "37.5686123950",
            "070-7722-9284",
            "위라이드 서울전차"
        ),
        LocationBasedDTO(
            "서울특별시 중구 다동길 46",
            "1",
            "24",
            "2834048",
            "39",
            "65.35942494553268",
            "http://tong.visitkorea.or.kr/cms/resource/21/2654721_image2_1.jpg",
            "http://tong.visitkorea.or.kr/cms/resource/21/2654721_image2_1.jpg",
            "126.9817290217",
            "37.5678958128",
            "02-772-9023",
            "가쯔야"
        ),
        LocationBasedDTO(
            "서울특별시 중구 다동길 36",
            "1",
            "24",
            "2788822",
            "39",
            "74.50735119629756",
            "http://tong.visitkorea.or.kr/cms/resource/75/2789575_image2_1.jpg",
            "http://tong.visitkorea.or.kr/cms/resource/75/2789575_image2_1.jpg",
            "126.9812818412",
            "37.5678596976",
            "",
            "인천집"
        )
    )

    override suspend fun getLocationBasedListByContentType(
        numOfRows: Int,
        pageNo: Int,
        mapX: String,
        mapY: String,
        contentTypeId: String
    ): Result<List<LocationBasedDTO>> = runCatching {
        ApiResponse(
            CommonResponse(
                CommonHeader("0000", "OK"),
                CommonBody(
                    10,
                    1,
                    2944,
                    CommonItem(locationList.filter { it.contentTypeId == contentTypeId })
                )
            )
        ).toItems()
    }
}