package kr.ksw.visitkorea.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kr.ksw.visitkorea.data.di.DataModule
import kr.ksw.visitkorea.data.local.databases.AreaCodeDatabase
import kr.ksw.visitkorea.data.repository.AreaCodeRepository
import kr.ksw.visitkorea.data.repository.FakeAndroidAreaCodeRepository
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModule::class]
)
object TestAppModule {

    @Provides
    @Singleton
    fun provideAreaCodeDatabase(application: Application): AreaCodeDatabase {
        return Room.inMemoryDatabaseBuilder(
            application,
            AreaCodeDatabase::class.java
        ).build()
    }

    @Provides
    @Singleton
    fun provideAreaCodeRepository(): AreaCodeRepository {
        return FakeAndroidAreaCodeRepository()
    }

}