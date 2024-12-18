package kr.ksw.visitkorea.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import kr.ksw.visitkorea.domain.usecase.detail.GetDetailCommonUseCase
import kr.ksw.visitkorea.domain.usecase.detail.GetDetailCommonUseCaseImpl
import kr.ksw.visitkorea.domain.usecase.detail.GetDetailImageUseCase
import kr.ksw.visitkorea.domain.usecase.detail.GetDetailImageUseCaseUseCaseImpl
import kr.ksw.visitkorea.domain.usecase.detail.GetDetailIntroUseCase
import kr.ksw.visitkorea.domain.usecase.detail.GetDetailIntroUseCaseImpl
import kr.ksw.visitkorea.domain.usecase.detail.GetHotelDetailUseCase
import kr.ksw.visitkorea.domain.usecase.detail.GetHotelDetailUseCaseImpl
import kr.ksw.visitkorea.domain.usecase.detail.GetHotelRoomDetailUseCase
import kr.ksw.visitkorea.domain.usecase.detail.GetHotelRoomDetailUseCaseImpl

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class DetailModule {
    @Binds
    abstract fun bindGetDetailCommonUseCase(
        getDetailCommonUseCase: GetDetailCommonUseCaseImpl
    ): GetDetailCommonUseCase

    @Binds
    abstract fun bindGetDetailIntroUseCase(
        getDetailIntroUseCase: GetDetailIntroUseCaseImpl
    ): GetDetailIntroUseCase

    @Binds
    abstract fun bindGetDetailImageUseCase(
        getDetailImageUseCase: GetDetailImageUseCaseUseCaseImpl
    ): GetDetailImageUseCase

    @Binds
    abstract fun bindGetHotelRoomDetailUseCase(
        getDetailInfoUseCase: GetHotelRoomDetailUseCaseImpl
    ): GetHotelRoomDetailUseCase

    @Binds
    abstract fun bindGetHotelDetailUseCase(
        getHotelDetailUseCase: GetHotelDetailUseCaseImpl
    ): GetHotelDetailUseCase
}