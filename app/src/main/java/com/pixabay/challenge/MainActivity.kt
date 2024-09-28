package com.pixabay.challenge

import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.pixabay.challenge.navigation.Navigation
import com.pixabay.challenge.ui.theme.PixabayImageSearchTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        WindowCompat.setDecorFitsSystemWindows(window, true)

        setContent {
            val navController = rememberNavController()
            PixabayImageSearchTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Navigation(navController)
                }
            }
        }
    }
}
