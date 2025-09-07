package com.example.moneytracker

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.moneytracker.ui.theme.Lexend
import com.example.moneytracker.ui.theme.MoneyTrackerTheme
import com.example.moneytracker.viewmodels.navigation.Screens
import com.example.moneytracker.viewmodels.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreen : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoneyTrackerTheme {
                Splash { startDestination ->
                    startActivity(Intent(this, MainActivity::class.java).apply {
                        putExtra("startDestination", startDestination)
                    })
                    finish()
                }
            }
        }
    }

    @Composable
    private fun Splash(onSplashFinished: (String) -> Unit) {

        var isSplashComplete by remember { mutableStateOf(false) }
        val userViewModel = hiltViewModel<UserViewModel>()
        val userData by userViewModel.userData.collectAsStateWithLifecycle()
        val session by userViewModel.session.collectAsStateWithLifecycle()
        val text = "Money Tracker"
        var displayedText by remember { mutableStateOf("") }

        LaunchedEffect (Unit) {

            for (i in 1 ..text.length) {
                displayedText = text.substring(0, i)
                delay(100)
            }

            delay(500)
            isSplashComplete = true
        }

        LaunchedEffect(isSplashComplete, session) {
            if (!isSplashComplete) return@LaunchedEffect

            val startDestination = if (session && userData.userData.token.isNotEmpty()) Screens.Start.route else Screens.Login.route

            onSplashFinished(startDestination)
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = displayedText,
                fontSize = 45.sp,
                color = Color.White,
                fontFamily = Lexend,
                fontWeight = FontWeight.Bold
            )
        }
    }
}