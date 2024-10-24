package kr.ksw.visitkorea.presentation.search.viewmodel

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kr.ksw.visitkorea.domain.usecase.model.CommonCardModel

@Immutable
data class SearchState(
    val searchKeyword: String = "",
    val isLoadingImages: Boolean = false,
    val searchCardModelFlow: Flow<PagingData<CommonCardModel>> = emptyFlow(),
)