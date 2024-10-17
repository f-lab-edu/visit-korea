package kr.ksw.visitkorea.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import kr.ksw.visitkorea.domain.usecase.more.GetMoreListUseCase
import kr.ksw.visitkorea.domain.usecase.more.GetMoreListUseCaseImpl

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class MoreModule {
    @Binds
    abstract fun bindGetMoreListUseCase(
        getMoreListUseCase: GetMoreListUseCaseImpl
    ): GetMoreListUseCase
}