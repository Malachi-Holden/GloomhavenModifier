package com.holden.gloomhavenmodifier.editCharacter

import com.holden.gloomhavenmodifier.chooseCharacter.viewModel.CharacterState
import com.holden.gloomhavenmodifier.editCharacter.model.CharacterModel
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

val REMOTE_ASSET_URL = "https://api.github.com/repos/Malachi-Holden/GloomhavenModifier/contents/remoteAssets/"
/**
 * client needs to be configured with:
 * -expectSuccess = true
 * -install(ContentNegotiation)
 * -json()
 */
class RemoteCharacterRepo @Inject constructor(val client: HttpClient): CharacterRepository {
    suspend fun getCharacterAssetFiles(): List<String> {
        val body: Json  = client.get(
            REMOTE_ASSET_URL + "RemoteCharacters.json"
        ).body()
        return Json.decodeFromString(body)
    }

    suspend fun attemptGetCharacters(): List<CharacterModel> = buildList {
        for (file in getCharacterAssetFiles()) {
            val body: String = client.get(REMOTE_ASSET_URL + file).body()
            add(Json.decodeFromString(body))
        }
    }

    override suspend fun getCharacters(): CharacterState = try {
        CharacterState.Loaded(attemptGetCharacters())
    } catch (e: ResponseException) {
        CharacterState.Error(e)
    }
}