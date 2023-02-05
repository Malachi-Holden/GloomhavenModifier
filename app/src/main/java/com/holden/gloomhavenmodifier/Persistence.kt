package com.holden.gloomhavenmodifier

import android.content.Context
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.FileNotFoundException


@OptIn(ExperimentalSerializationApi::class)
inline fun <reified A>saveLocalGloomObject(context: Context, obj: A, file: String){
    val json = Json.encodeToString(obj)
    context.openFileOutput(file, Context.MODE_PRIVATE).use {
        it.write(json.toByteArray())
    }
}

@OptIn(ExperimentalSerializationApi::class)
inline fun <reified A>getLocalGloomObject(context: Context, file: String): A?{
    try {
        val json = context.openFileInput(file).bufferedReader().use {
            it.readText()
        }

        return Json.decodeFromString(json)
    } catch (e: FileNotFoundException){
        return null
    }
}