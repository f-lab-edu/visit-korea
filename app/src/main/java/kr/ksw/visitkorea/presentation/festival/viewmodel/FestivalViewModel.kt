package kr.ksw.visitkorea.presentation.festival.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kr.ksw.visitkorea.data.local.entity.FavoriteEntity
import kr.ksw.visitkorea.domain.usecase.favorite.DeleteFavoriteEntityByContentIdUseCase
import kr.ksw.visitkorea.domain.usecase.favorite.ExistFavoriteEntityUseCase
import kr.ksw.visitkorea.domain.usecase.favorite.GetAllFavoriteEntityUseCase
import kr.ksw.visitkorea.domain.usecase.favorite.UpsertFavoriteEntityUseCase
import kr.ksw.visitkorea.domain.usecase.festival.GetAreaCodeUseCase
import kr.ksw.visitkorea.domain.usecase.festival.GetFestivalListUseCase
import kr.ksw.visitkorea.domain.usecase.mapper.toFestival
import kr.ksw.visitkorea.presentation.common.DetailParcel
import kr.ksw.visitkorea.presentation.common.getCurrentDateString
import javax.inject.Inject

@HiltViewModel
class FestivalViewModel @Inject constructor(
    private val getFestivalListUseCase: GetFestivalListUseCase,
    private val upsertFavoriteEntityUseCase: UpsertFavoriteEntityUseCase,
    private val deleteFavoriteEntityByContentIdUseCase: DeleteFavoriteEntityByContentIdUseCase,
    private val getAllFavoriteEntityUseCase: GetAllFavoriteEntityUseCase,
    private val getAreaCodeUseCase: GetAreaCodeUseCase
) : ViewModel() {
    private val _festivalState = MutableStateFlow(FestivalState())
    val festivalState: StateFlow<FestivalState>
        get() = _festivalState.asStateFlow()

    private val _festivalUiEffect = MutableSharedFlow<FestivalUiEffect>(replay = 0)
    val festivalUiEffect: SharedFlow<FestivalUiEffect>
        get() = _festivalUiEffect.asSharedFlow()

    private val favoriteIdFlow = MutableStateFlow(setOf<String>())

    init {
        getFestivalList()
        getAllAreaCodes()
        viewModelScope.launch {
            getAllFavoriteEntityUseCase().collect { entities ->
                favoriteIdFlow.update {
                    entities.map {
                        it.contentId
                    }.toSet()
                }
            }
        }
    }

    fun onAction(action: FestivalActions) {
        when(action) {
            is FestivalActions.ClickFestivalCardItem -> {
                startDetailActivity(action.data)
            }
            is FestivalActions.ClickFavoriteIcon -> {
                if(action.isFavorite) {
                    deleteFavorite(action.entity)
                } else {
                    upsertFavorite(action.entity)
                }
            }
            is FestivalActions.ClickFilterIcon -> {
                dialogToggle(true)
            }
            is FestivalActions.DismissDialog -> {
                dialogToggle(false)
            }
            is FestivalActions.ClickFilterItem -> {
                dialogToggle(false)
                getFestivalList(
                    forceFetch = true,
                    areaCodeIndex = action.index
                )
            }
        }
    }

    private fun getFestivalList(
        forceFetch: Boolean = false,
        areaCodeIndex: Int = -1
    ) {
        val currentDate = getCurrentDateString()
        val areaCode = if(areaCodeIndex == -1 ||
            _festivalState.value.areaCodes.isEmpty()) {
            null
        } else {
            _festivalState.value.areaCodes[areaCodeIndex].code
        }

        viewModelScope.launch {
            val festivalListFlow = getFestivalListUseCase(
                forceFetch,
                currentDate,
                currentDate,
                areaCode,
                null
            ).getOrNull()
            if(festivalListFlow != null) {
                val festivalModelFlow = festivalListFlow.map { pagingData ->
                    pagingData.map { data ->
                        data.toFestival()
                    }
                }.cachedIn(viewModelScope)

                _festivalState.update {
                    it.copy(
                        // flow의 combine 함수를 이용한 PagingData의 Favorite 상태관리
                        festivalModelFlow = combine(
                            festivalModelFlow,
                            favoriteIdFlow
                        ) { paging, favorites ->
                            paging.map { festival ->
                                if(favorites.contains(festival.contentId)) {
                                    festival.copy(isFavorite = true)
                                } else {
                                    festival.copy(isFavorite = false)
                                }
                            }
                        }
                    )
                }
            }
        }
    }

    private fun getAllAreaCodes() {
        viewModelScope.launch {
            _festivalState.update {
                it.copy(
                    areaCodes = getAreaCodeUseCase()
                )
            }
        }
    }

    private fun startDetailActivity(data: DetailParcel) {
        viewModelScope.launch {
            _festivalUiEffect.emit(FestivalUiEffect.StartDetailActivity(data))
        }
    }

    private fun upsertFavorite(favoriteEntity: FavoriteEntity) {
        viewModelScope.launch {
            upsertFavoriteEntityUseCase(favoriteEntity)
        }
    }

    private fun deleteFavorite(favoriteEntity: FavoriteEntity) {
        viewModelScope.launch {
            deleteFavoriteEntityByContentIdUseCase(favoriteEntity.contentId)
        }
    }

    private fun dialogToggle(show: Boolean) {
        _festivalState.update {
            it.copy(
                showFilterDialog = show
            )
        }
    }
}