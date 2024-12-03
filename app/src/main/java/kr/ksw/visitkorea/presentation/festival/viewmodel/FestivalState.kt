package kr.ksw.visitkorea.presentation.festival.viewmodel

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kr.ksw.visitkorea.data.local.entity.AreaCodeEntity
import kr.ksw.visitkorea.domain.model.Festival

@Immutable
data class FestivalState(
    val showFilterDialog: Boolean = false,
    val areaCodes: List<AreaCodeEntity> = emptyList(),
    val festivalModelFlow: Flow<PagingData<Festival>> = emptyFlow(),
    val selectedAreaCode: Int = -1,
)