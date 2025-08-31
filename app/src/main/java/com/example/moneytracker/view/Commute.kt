package com.example.moneytracker.view

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moneytracker.ui.theme.buttonDark
import com.example.moneytracker.ui.theme.buttonLight

@Composable
fun Commute() {
    Surface {
        val (from, setFrom) = remember { mutableStateOf("") }
        val (to, setTo) = remember { mutableStateOf("") }
        val color = if (isSystemInDarkTheme()) buttonDark else buttonLight

        Column (
            modifier = Modifier.fillMaxSize().padding(top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Input(
                label = "From",
                value = from,
                onValueChange = setFrom,
                color = color
            )

            AddHeight(20.dp)

            Input(
                label = "To",
                value = to,
                onValueChange = setTo,
                color = color
            )

            Button(
                onClick = {}
            ) { }
        }
    }
}