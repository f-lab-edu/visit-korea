package kr.ksw.visitkorea.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import kr.ksw.visitkorea.domain.usecase.festival.GetAreaCodeUseCase
import kr.ksw.visitkorea.domain.usecase.festival.GetAreaCodeUseCaseImpl
import kr.ksw.visitkorea.domain.usecase.festival.GetFestivalListUseCase
import kr.ksw.visitkorea.domain.usecase.festival.GetFestivalListUseCaseImpl

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class FestivalModule {
    @Binds
    abstract fun bindGetFestivalListUseCase(
        getFestivalListUseCase: GetFestivalListUseCaseImpl
    ): GetFestivalListUseCase

    @Binds
    abstract fun bindGetAreaCodeUseCase(
        getAreaCodeUseCase: GetAreaCodeUseCaseImpl
    ): GetAreaCodeUseCase
}