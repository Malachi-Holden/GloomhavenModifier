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

val REMOTE_ASSET_URL = "https://raw.githubusercontent.com/Malachi-Holden/GloomhavenModifier/master/remoteAssets/"
/**
 * client needs to be configured with:
 * -expectSuccess = true
 * -install(ContentNegotiation)
 * -json()
 */
class RemoteCharacterRepo @Inject constructor(val client: HttpClient): CharacterRepository {
    suspend fun getCharacterAssetFiles(): List<String> {
        val response= client.get(
            REMOTE_ASSET_URL + "RemoteCharacters.json"
        )
        val body: String = response.body()
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