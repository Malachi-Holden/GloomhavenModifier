package com.holden.gloomhavenmodifier

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.holden.gloomhavenmodifier.navigate.GloomNavHost
import com.holden.gloomhavenmodifier.navigate.GloomScaffold
import com.holden.gloomhavenmodifier.ui.theme.GloomhavenModifierTheme

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