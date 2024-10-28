package kr.ksw.visitkorea.domain.usecase.festival

import kr.ksw.visitkorea.data.local.entity.AreaCodeEntity

interface GetAreaCodeUseCase {
    suspend operator fun invoke(): List<AreaCodeEntity>
}