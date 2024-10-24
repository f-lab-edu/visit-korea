package kr.ksw.visitkorea.data.repository

import kr.ksw.visitkorea.data.remote.dto.DetailCommonDTO
import kr.ksw.visitkorea.data.remote.dto.DetailImageDTO
import kr.ksw.visitkorea.data.remote.dto.DetailIntroDTO

class FakeDetailRepository : DetailRepository {
    val detailCommons = mapOf(
        "264408" to DetailCommonDTO(
            "https://www.suwon.go.kr/web/visitsuwon/hs01/hs01-01/pages.do?seqNo=37",
            "팔달산 정상에 자리 잡은 서장대는 정조 18년에 건설한 군사시설이다. 서장대의 아래층은 사면 3칸, 위층은 1칸으로 위로 가면서 좁아지는 형태이다. 아래층은 장수가 머물면서 군사 훈련을 지휘하고, 위층은 군사가 주변을 감시하는 용도로 썼다. 정조는 서장대에서 군사 훈련인 성조를 거행했는데 1795년의 행사 모습이 그림으로 남아 있다."
        ),
        "2662855" to DetailCommonDTO(
            "http://www.gglakepark.or.kr",
            "광교호수공원은 아름다운 수변공간인 어반레비와 함께 6개의 테마를 가진 둠벙으로 어우러져 여러 가지 새로운 문화를 담은 국내 최대의 도심속 호수공원이다. "
        ),
        "125531" to DetailCommonDTO(
            "http://www.yongjoosa.or.kr",
            "용주사는 정조가 창건한 사찰이다. 본래 이 곳은 신라 문성왕 16년(854)에 길양사를 창건하여 고려 초기에 수륙재가 봉행되었으나 잦은 병란으로 소실된 후 폐사되었었다. "
        ),
    )

    override suspend fun getDetailCommon(contentId: String): Result<DetailCommonDTO> = runCatching {
        detailCommons[contentId] ?: throw NullPointerException()
    }

    override suspend fun getDetailIntro(
        contentId: String,
        contentTypeId: String
    ): Result<DetailIntroDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun getDetailImage(contentId: String): Result<List<DetailImageDTO>> {
        TODO("Not yet implemented")
    }
}