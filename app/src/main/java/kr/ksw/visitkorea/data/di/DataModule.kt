package kr.ksw.visitkorea.data.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.ksw.visitkorea.data.local.databases.AreaCodeDatabase
import kr.ksw.visitkorea.data.remote.api.AreaCodeApi
import kr.ksw.visitkorea.data.remote.api.LocationBasedListApi
import kr.ksw.visitkorea.data.remote.api.RetrofitInterceptor
import kr.ksw.visitkorea.data.repository.AreaCodeRepository
import kr.ksw.visitkorea.data.repository.AreaCodeRepositoryImpl
import kr.ksw.visitkorea.data.repository.LocationBasedListRepository
import kr.ksw.visitkorea.data.repository.LocationBasedListRepositoryImpl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    private const val BASE_URL = "https://apis.data.go.kr/B551011/KorService1/"

    @Provides
    @Singleton
    fun provideOkhttpClient(interceptor: RetrofitInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideAreaCodeDatabase(application: Application): AreaCodeDatabase {
        return Room.databaseBuilder(
            application,
            AreaCodeDatabase::class.java,
            "area_code.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideAreaCodeApi(retrofit: Retrofit): AreaCodeApi = retrofit.create(AreaCodeApi::class.java)

    @Provides
    @Singleton
    fun provideLocationBasedListApi(retrofit: Retrofit): LocationBasedListApi = retrofit.create(LocationBasedListApi::class.java)

    @Provides
    @Singleton
    fun provideAreaCodeRepository(areaCodeApi: AreaCodeApi): AreaCodeRepository {
        return AreaCodeRepositoryImpl(areaCodeApi)
    }

    @Provides
    @Singleton
    fun provideLocationBasedListRepository(locationBasedListApi: LocationBasedListApi): LocationBasedListRepository {
        return LocationBasedListRepositoryImpl(locationBasedListApi)
    }
}