package kr.ksw.visitkorea.domain.usecase.festival

import kr.ksw.visitkorea.data.local.entity.AreaCodeEntity
import kr.ksw.visitkorea.data.repository.AreaCodeRepository
import javax.inject.Inject

class GetAreaCodeUseCaseImpl @Inject constructor(
    private val areaCodeRepository: AreaCodeRepository
): GetAreaCodeUseCase {
    override suspend fun invoke(): List<AreaCodeEntity> {
        return areaCodeRepository.getAllAreaCodes()
    }
}