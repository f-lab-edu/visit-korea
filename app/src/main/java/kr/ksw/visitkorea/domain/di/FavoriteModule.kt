package kr.ksw.visitkorea.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import kr.ksw.visitkorea.domain.usecase.favorite.DeleteFavoriteEntityByContentIdUseCase
import kr.ksw.visitkorea.domain.usecase.favorite.DeleteFavoriteEntityByContentIdUseCaseImpl
import kr.ksw.visitkorea.domain.usecase.favorite.ExistFavoriteEntityUseCase
import kr.ksw.visitkorea.domain.usecase.favorite.ExistFavoriteEntityUseCaseImpl
import kr.ksw.visitkorea.domain.usecase.favorite.GetAllFavoriteEntityUseCase
import kr.ksw.visitkorea.domain.usecase.favorite.GetAllFavoriteEntityUseCaseImpl
import kr.ksw.visitkorea.domain.usecase.favorite.UpsertFavoriteEntityUseCase
import kr.ksw.visitkorea.domain.usecase.favorite.UpsertFavoriteEntityUseCaseImpl

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class FavoriteModule {
    @Binds
    abstract fun bindGetAllFavoriteEntityUseCase(
        getAllFavoriteEntityUseCase: GetAllFavoriteEntityUseCaseImpl
    ): GetAllFavoriteEntityUseCase

    @Binds
    abstract fun bindUpsertFavoriteEntityUseCase(
        upsertFavoriteEntityUseCase: UpsertFavoriteEntityUseCaseImpl
    ): UpsertFavoriteEntityUseCase

    @Binds
    abstract fun bindExistFavoriteEntityUseCase(
        existFavoriteEntityUseCase: ExistFavoriteEntityUseCaseImpl
    ): ExistFavoriteEntityUseCase

    @Binds
    abstract fun bindDeleteFavoriteEntityByContentId(
        deleteFavoriteEntityByContentIdUseCase: DeleteFavoriteEntityByContentIdUseCaseImpl
    ): DeleteFavoriteEntityByContentIdUseCase
}