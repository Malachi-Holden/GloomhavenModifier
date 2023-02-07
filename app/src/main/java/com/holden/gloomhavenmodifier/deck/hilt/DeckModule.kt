package com.holden.gloomhavenmodifier.deck.hilt

import android.content.Context
import com.holden.gloomhavenmodifier.deck.DeckRepository
import com.holden.gloomhavenmodifier.deck.model.DeckModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DeckModule {
    @Provides
    fun providesDeck(@ApplicationContext context: Context): DeckModel
        = DeckRepository.getLocalDeck(context)
}