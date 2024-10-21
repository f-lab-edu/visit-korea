package kr.ksw.visitkorea.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import kr.ksw.visitkorea.domain.usecase.search.GetListByKeywordUseCase
import kr.ksw.visitkorea.domain.usecase.search.GetListByKeywordUseCaseImpl

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class SearchModule {
    @Binds
    abstract fun bindGetListByKeywordUseCase(
        getListByKeywordUseCase: GetListByKeywordUseCaseImpl
    ): GetListByKeywordUseCase
}