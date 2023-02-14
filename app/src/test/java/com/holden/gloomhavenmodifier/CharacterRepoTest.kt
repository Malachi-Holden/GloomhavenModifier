package com.holden.gloomhavenmodifier

import com.holden.gloomhavenmodifier.chooseCharacter.viewModel.CharacterState
import com.holden.gloomhavenmodifier.editCharacter.CharacterRepository
import com.holden.gloomhavenmodifier.editCharacter.model.CharacterModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Test

class CharacterRepoTest {
    lateinit var characterRepository: CharacterRepository

    @Before
    fun setup() {
        characterRepository = MockCharacterRepo()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetCharacters() = runTest {
        val characters = (characterRepository.getCharacters() as CharacterState.Loaded).characters
        assertEquals("there should be 1 loaded character", 1, characters.size)
        val character = characters[0]
        assertEquals("the title should be 'Drifter (test)'", "Drifter (test)", character.title)
        assertEquals(
            character.perks.map { it.description }.toSet(),
            setOf(
                "replace one -1 with one +1 card",
                "replace one -2 with one +0 card",
                "replace two +0 with 2 rolling pierce 3 cards"
            )
        )
    }

    @Test
    fun testThing(){
        assertEquals(2, 1 + 1)
    }
}


class MockCharacterRepo : CharacterRepository {
    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun getCharacters(): CharacterState
        = buildList<CharacterModel> {
            for (characterString in getCharacterJson()){
                add(Json.decodeFromString(characterString))
            }
        }.let { CharacterState.Loaded(it) }
    fun getCharacterJson(): List<String> = listOf(
        """
        {
          "title": "Drifter (test)",
          "specialtyCards": [
            {
              "description": "rolling pierce 3",
              "resourceType": "local",
              "imageRes": "rolling_pierce_3",
              "reshuffle": false
            },
            {
              "description": "rolling push 2",
              "resourceType": "local",
              "imageRes": "rolling_push_2",
              "reshuffle": false
            }
          ],
          "perks": [
            {
              "description": "replace one -1 with one +1 card",
              "add": ["+ 1"],
              "remove": ["- 1"]
            },
            {
              "description": "replace one -2 with one +0 card",
              "add": ["+ 0"],
              "remove": ["- 2"]
            },
            {
              "description": "replace two +0 with 2 rolling pierce 3 cards",
              "add": ["rolling push 2", "rolling push 2"],
              "remove": ["+ 0", "+ 0"]
            }
          ]
        }
    """.trimIndent()
    )

}