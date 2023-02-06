package com.holden.gloomhavenmodifier.editCharacter

import android.util.Log
import com.holden.gloomhavenmodifier.editCharacter.model.CharacterModel
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.engine.cio.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

val REMOTE_ASSET_URL = "https://raw.githubusercontent.com/Malachi-Holden/GloomhavenModifier/master/remoteAssets/"
/**
 * client needs to be configured with:
 * -expectSuccess = true
 * -install(ContentNegotiation)
 * -json()
 */
class RemoteCharacterRepo(val client: HttpClient = client()): CharacterRepository {
    suspend fun getCharacterAssetFiles(): List<String> {
        val body: String= client.get(
            REMOTE_ASSET_URL + "RemoteCharacters.json"
        ).body()
        return Json.decodeFromString(body)
    }

    override suspend fun getCharacters(): List<CharacterModel> = buildList {
        try {
            for (file in getCharacterAssetFiles()) {
                val body: String = client.get(REMOTE_ASSET_URL + file).body()
                add(Json.decodeFromString(body))
            }
        } catch (e: ResponseException){
            Log.d("RemoteCharacterRepo", e.message?: e.response.status.description)
        }
    }

    companion object{
        fun client() = HttpClient(CIO){
            expectSuccess = true
        }
    }
}