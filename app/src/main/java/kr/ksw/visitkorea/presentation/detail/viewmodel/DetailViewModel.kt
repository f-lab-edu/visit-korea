package kr.ksw.visitkorea.presentation.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kr.ksw.visitkorea.data.local.entity.FavoriteEntity
import kr.ksw.visitkorea.domain.common.TYPE_RESTAURANT
import kr.ksw.visitkorea.domain.usecase.detail.GetDetailCommonUseCase
import kr.ksw.visitkorea.domain.usecase.detail.GetDetailImageUseCase
import kr.ksw.visitkorea.domain.usecase.detail.GetDetailIntroUseCase
import kr.ksw.visitkorea.domain.usecase.favorite.DeleteFavoriteEntityByContentIdUseCase
import kr.ksw.visitkorea.domain.usecase.favorite.ExistFavoriteEntityUseCase
import kr.ksw.visitkorea.domain.usecase.favorite.UpsertFavoriteEntityUseCase
import kr.ksw.visitkorea.domain.usecase.util.toImageUrl
import kr.ksw.visitkorea.presentation.common.DetailParcel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getDetailCommonUseCase: GetDetailCommonUseCase,
    private val getDetailIntroUseCase: GetDetailIntroUseCase,
    private val getDetailImageUseCase: GetDetailImageUseCase,
    private val upsertFavoriteEntityUseCase: UpsertFavoriteEntityUseCase,
    private val deleteFavoriteEntityByContentIdUseCase: DeleteFavoriteEntityByContentIdUseCase,
    private val existFavoriteEntityUseCase: ExistFavoriteEntityUseCase,
): ViewModel() {
    private val _detailState = MutableStateFlow(DetailState())
    val detailState: StateFlow<DetailState>
        get() = _detailState.asStateFlow()

    fun onAction(action: DetailActions) {
        when(action) {
            is DetailActions.ClickFavoriteIconUpsert -> {
                upsertFavorite(action.favorite)
            }
            is DetailActions.ClickFavoriteIconDelete -> {
                deleteFavorite(action.contentId)
            }
            is DetailActions.ClickDetailImages -> {
                openImageViewPager(action.selectedImage)
            }
            is DetailActions.ClickBackButtonWhenViewPagerOpened -> {
                _detailState.update {
                    it.copy(
                        viewPagerOpen = false
                    )
                }
            }
        }
    }

    fun initDetail(
        detailParcel: DetailParcel
    ) {
        viewModelScope.launch {
            _detailState.update {
                it.copy(
                    title = detailParcel.title,
                    firstImage = detailParcel.firstImage,
                    address = detailParcel.address,
                    dist = detailParcel.dist,
                    contentId = detailParcel.contentId,
                    contentTypeId = detailParcel.contentTypeId,
                    eventStartDate = detailParcel.eventStartDate,
                    eventEndDate = detailParcel.eventEndDate,
                    isFavorite = existFavoriteEntityUseCase(detailParcel.contentId)
                )
            }
        }
        getDetailCommon(detailParcel.contentId)
        getDetailIntro(
            detailParcel.contentId,
            detailParcel.contentTypeId
        )
        if(detailParcel.contentTypeId == TYPE_RESTAURANT) {
            getDetailMenuImage(detailParcel.contentId)
        } else {
            getDetailImage(detailParcel.contentId)
        }
    }

    private fun getDetailCommon(contentId: String) {
        viewModelScope.launch {
            getDetailCommonUseCase(contentId).getOrNull()?.run {
                _detailState.update {
                    it.copy(
                        detailCommon = this
                    )
                }
            }
        }
    }

    private fun getDetailIntro(
        contentId: String,
        contentTypeId: String
    ) {
        viewModelScope.launch {
            getDetailIntroUseCase(
                contentId,
                contentTypeId
            ).getOrNull()?.run {
                _detailState.update {
                    it.copy(
                        detailIntro = this
                    )
                }
            }
        }
    }

    private fun getDetailImage(
        contentId: String
    ) {
        viewModelScope.launch {
            getDetailImageUseCase(
                contentId, "Y"
            ).getOrNull()?.run {
                _detailState.update {
                    it.copy(
                        images = this.map { image ->
                            image.copy(
                                originImgUrl = image.originImgUrl.toImageUrl(),
                                smallImageUrl = image.smallImageUrl.toImageUrl()
                            )
                        }
                    )
                }
            }
        }
    }

    private fun getDetailMenuImage(
        contentId: String
    ) {
        viewModelScope.launch {
            val result = getDetailImageUseCase(
                contentId, "N"
            ).getOrNull()
            if(result.isNullOrEmpty()) {
                getDetailImage(contentId)
            } else {
                _detailState.update {
                    it.copy(
                        images = result.map { image ->
                            image.copy(
                                originImgUrl = image.originImgUrl.toImageUrl(),
                                smallImageUrl = image.smallImageUrl.toImageUrl()
                            )
                        }
                    )
                }
            }
        }
    }

    private fun upsertFavorite(favorite: FavoriteEntity) {
        viewModelScope.launch {
            upsertFavoriteEntityUseCase(favorite)
            _detailState.update {
                it.copy(
                    isFavorite = true
                )
            }
        }
    }

    private fun deleteFavorite(contentId: String) {
        viewModelScope.launch {
            deleteFavoriteEntityByContentIdUseCase(contentId)
            _detailState.update {
                it.copy(
                    isFavorite = false
                )
            }
        }
    }

    private fun openImageViewPager(selectedImage: Int) {
        _detailState.update {
            it.copy(
                selectedImage = selectedImage,
                viewPagerOpen = true,
            )
        }
    }
}