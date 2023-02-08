package com.holden.gloomhavenmodifier.chooseCharacter.hilt

import android.content.Context
import android.content.res.AssetManager
import com.holden.gloomhavenmodifier.editCharacter.BuiltInCharacterRepo
import com.holden.gloomhavenmodifier.editCharacter.model.CharacterModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.cio.*

@Module
@InstallIn(ViewModelComponent::class)
class CharacterModule {
    @Provides
    @ViewModelScoped
    fun providesClient(): HttpClient = HttpClient(CIO){
        expectSuccess = true
    }

    @Provides
    @ViewModelScoped
    fun providesAssets(@ApplicationContext context: Context): AssetManager = context.assets

    @Provides
    @ViewModelScoped
    fun provideCharacter(@ApplicationContext context: Context): CharacterModel
        = BuiltInCharacterRepo.getLocalCharacter(context)
}