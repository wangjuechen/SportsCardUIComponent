package com.jc.android.sportscard.di

import com.jc.android.sportscard.forhostapp.SportsCardLibraryClientImpl
import com.jc.android.sportscard.repository.ContentRepository
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(modules = [SportsCardModule::class])
internal interface AppComponent {
    fun inject(sportsCardLibraryClientImpl: SportsCardLibraryClientImpl)
}

@Module
class SportsCardModule {

    @Provides
    @Singleton
    fun provideContentRepository(): ContentRepository = ContentRepository()
}