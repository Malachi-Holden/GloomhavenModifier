package com.holden.gloomhavenmodifier

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.holden.gloomhavenmodifier.navigate.GloomScaffold
import com.holden.gloomhavenmodifier.ui.theme.GloomhavenModifierTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GloomhavenModifierTheme {
                // A surface container using the 'background' color from the theme
                GloomScaffold()
            }
        }
    }
}