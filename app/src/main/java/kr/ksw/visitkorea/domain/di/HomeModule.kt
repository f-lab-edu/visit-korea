package kr.ksw.visitkorea.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import kr.ksw.visitkorea.domain.usecase.home.GetTouristSpotForHomeUseCase
import kr.ksw.visitkorea.domain.usecase.home.GetTouristSpotForHomeUseCaseImpl

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class HomeModule {
    @Binds
    abstract fun bindGetTouristSpotForHomeUseCase(
        getTouristSpotForHomeUseCase: GetTouristSpotForHomeUseCaseImpl
    ): GetTouristSpotForHomeUseCase
}