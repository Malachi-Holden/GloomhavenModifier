package com.holden.gloomhavenmodifier.deck.model

import kotlinx.serialization.Serializable

@Serializable
data class CardModel (
    val description: String,
    val imageRes: String? = null,
    val reshuffle: Boolean
)

