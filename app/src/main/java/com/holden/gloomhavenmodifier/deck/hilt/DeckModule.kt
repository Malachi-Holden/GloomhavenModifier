package com.holden.gloomhavenmodifier.deck.hilt

import android.content.Context
import com.holden.gloomhavenmodifier.deck.getLocalDeck
import com.holden.gloomhavenmodifier.deck.model.DeckModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
class DeckModule {
    @Provides
    @ViewModelScoped
    fun bindsDeck(@ApplicationContext context: Context): DeckModel = getLocalDeck(context)
}