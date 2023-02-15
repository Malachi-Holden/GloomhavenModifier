package com.holden.gloomhavenmodifier

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.staticCompositionLocalOf
import com.holden.gloomhavenmodifier.navigate.GloomScaffold
import com.holden.gloomhavenmodifier.ui.theme.GloomhavenModifierTheme
import dagger.hilt.android.AndroidEntryPoint

val LocalComponentActivity = staticCompositionLocalOf<ComponentActivity> { throw IllegalArgumentException() }

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GloomhavenModifierTheme {
                // A surface container using the 'background' color from the theme
                CompositionLocalProvider(LocalComponentActivity provides this) {
                    GloomScaffold()
                }
            }
        }
    }
}