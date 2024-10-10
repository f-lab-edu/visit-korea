package kr.ksw.visitkorea.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import kr.ksw.visitkorea.domain.usecase.home.GetCultureCenterForHomeUseCase
import kr.ksw.visitkorea.domain.usecase.home.GetCultureCenterForHomeUseCaseImpl
import kr.ksw.visitkorea.domain.usecase.home.GetLeisureSportsForHomeUseCase
import kr.ksw.visitkorea.domain.usecase.home.GetLeisureSportsForHomeUseCaseImpl
import kr.ksw.visitkorea.domain.usecase.home.GetRestaurantForHomeUseCase
import kr.ksw.visitkorea.domain.usecase.home.GetRestaurantForHomeUseCaseImpl
import kr.ksw.visitkorea.domain.usecase.home.GetTouristSpotForHomeUseCase
import kr.ksw.visitkorea.domain.usecase.home.GetTouristSpotForHomeUseCaseImpl

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class HomeModule {
    @Binds
    abstract fun bindGetTouristSpotForHomeUseCase(
        getTouristSpotForHomeUseCase: GetTouristSpotForHomeUseCaseImpl
    ): GetTouristSpotForHomeUseCase

    @Binds
    abstract fun bindGetCultureCenterForHomeUseCase(
        getCultureCenterForHomeUseCase: GetCultureCenterForHomeUseCaseImpl
    ): GetCultureCenterForHomeUseCase

    @Binds
    abstract fun bindGetRestaurantForHomeUseCase(
        getRestaurantForHomeUseCase: GetRestaurantForHomeUseCaseImpl
    ): GetRestaurantForHomeUseCase

    @Binds
    abstract fun bindGetLeisureSportsForHomeCase(
        getLeisureSportsForHomeUseCase: GetLeisureSportsForHomeUseCaseImpl
    ): GetLeisureSportsForHomeUseCase
}