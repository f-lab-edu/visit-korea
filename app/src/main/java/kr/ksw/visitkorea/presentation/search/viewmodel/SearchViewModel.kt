package kr.ksw.visitkorea.presentation.search.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kr.ksw.visitkorea.data.local.entity.FavoriteEntity
import kr.ksw.visitkorea.domain.usecase.favorite.DeleteFavoriteEntityByContentIdUseCase
import kr.ksw.visitkorea.domain.usecase.favorite.GetAllFavoriteEntityUseCase
import kr.ksw.visitkorea.domain.usecase.favorite.UpsertFavoriteEntityUseCase
import kr.ksw.visitkorea.domain.usecase.mapper.toCommonCardModel
import kr.ksw.visitkorea.domain.usecase.search.GetListByKeywordUseCase
import kr.ksw.visitkorea.presentation.common.ContentType
import kr.ksw.visitkorea.presentation.core.BaseViewModel
import kr.ksw.visitkorea.presentation.core.viewModelLauncher
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getListByKeywordUseCase: GetListByKeywordUseCase,
    private val upsertFavoriteEntityUseCase: UpsertFavoriteEntityUseCase,
    private val deleteFavoriteEntityByContentIdUseCase: DeleteFavoriteEntityByContentIdUseCase,
    private val getAllFavoriteEntityUseCase: GetAllFavoriteEntityUseCase,
): BaseViewModel<SearchUiEffect>() {
    private val _searchState: MutableStateFlow<SearchState> = MutableStateFlow(SearchState())
    val searchState: StateFlow<SearchState>
        get() = _searchState.asStateFlow()

    private val favoriteIdFlow = MutableStateFlow(setOf<String>())

    init {
        viewModelLauncher {
            getAllFavoriteEntityUseCase().collect { entities ->
                favoriteIdFlow.update {
                    entities.map {
                        it.contentId
                    }.toSet()
                }
            }
        }
    }

    fun onAction(action: SearchActions) {
        when(action) {
            is SearchActions.UpdateSearchKeyword -> {
                _searchState.update {
                    it.copy(
                        searchKeyword = action.newKeyword
                    )
                }
            }
            is SearchActions.ClickFavoriteIcon -> {
                if(action.isFavorite) {
                    deleteFavorite(action.entity)
                } else {
                    upsertFavorite(action.entity)
                }
            }
            is SearchActions.SubmitSearchKeyword -> {
                getListByKeyword()
            }
            is SearchActions.ClickCardItem -> {
                postUIEffect(SearchUiEffect.StartDetailActivity(action.data))
            }
        }
    }

    private fun getListByKeyword() {
        viewModelLauncher {
            _searchState.update {
                it.copy(
                    isLoadingImages = true
                )
            }

            val searchListFlow = getListByKeywordUseCase(
                searchState.value.searchKeyword
            ).getOrNull()?.map { pagingData ->
                pagingData.filter {
                    contentTypeFilter(it.contentTypeId)
                }.map {
                    it.toCommonCardModel()
                }
            }?.cachedIn(viewModelScope) ?: emptyFlow()

            delay(500)
            _searchState.update {
                it.copy(
                    searchCardModelFlow = combine(
                        searchListFlow,
                        favoriteIdFlow
                    ) { pagingData, favorites ->
                        pagingData.map { model ->
                            if(favorites.contains(model.contentId)) {
                                model.copy(
                                    isFavorite = true
                                )
                            } else {
                                model.copy(
                                    isFavorite = false
                                )
                            }
                        }
                    },
                    isLoadingImages = false
                )
            }
        }
    }

    private fun upsertFavorite(favoriteEntity: FavoriteEntity) {
        viewModelLauncher {
            upsertFavoriteEntityUseCase(favoriteEntity)
        }
    }

    private fun deleteFavorite(favoriteEntity: FavoriteEntity) {
        viewModelLauncher {
            deleteFavoriteEntityByContentIdUseCase(favoriteEntity.contentId)
        }
    }

    private fun contentTypeFilter(contentType: String) = when(contentType) {
        ContentType.CULTURE.contentTypeId,
        ContentType.LEiSURE.contentTypeId,
        ContentType.RESTAURANT.contentTypeId,
        ContentType.TOURIST.contentTypeId -> true
        else -> false
    }
}