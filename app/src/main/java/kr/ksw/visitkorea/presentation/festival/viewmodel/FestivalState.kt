package kr.ksw.visitkorea.presentation.festival.viewmodel

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kr.ksw.visitkorea.domain.usecase.model.Festival

@Immutable
data class FestivalState(
    val festivalModelFlow: Flow<PagingData<Festival>> = emptyFlow(),
)