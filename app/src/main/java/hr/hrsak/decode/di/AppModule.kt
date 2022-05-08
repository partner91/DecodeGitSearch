package hr.hrsak.decode.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hr.hrsak.decode.api.GitApi
import retrofit2.Retrofit
import javax.inject.Singleton
import hr.hrsak.decode.api.BASE_URL
import hr.hrsak.decode.repositories.ReposRepository
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideGitApi() : GitApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GitApi::class.java)

    @Singleton
    @Provides
    fun providesRepository(api: GitApi): ReposRepository = ReposRepository(api)

}