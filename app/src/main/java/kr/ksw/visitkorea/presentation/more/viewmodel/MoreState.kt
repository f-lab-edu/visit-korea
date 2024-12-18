package kr.ksw.visitkorea.presentation.more.viewmodel

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kr.ksw.visitkorea.domain.model.MoreCardModel

@Immutable
data class MoreState(
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val moreCardModelFlow: Flow<PagingData<MoreCardModel>> = emptyFlow(),
)
