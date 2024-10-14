package kr.ksw.visitkorea.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import kr.ksw.visitkorea.domain.usecase.hotel.GetHotelListUseCase
import kr.ksw.visitkorea.domain.usecase.hotel.GetHotelListUseCaseImpl

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class HotelModule {
    @Binds
    abstract fun bindGetHotelListUseCase(
        getHotelListUseCase: GetHotelListUseCaseImpl
    ): GetHotelListUseCase
}