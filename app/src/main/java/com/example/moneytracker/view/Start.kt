package com.example.moneytracker.view

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moneytracker.ui.theme.Lexend
import com.example.moneytracker.ui.theme.button
import com.example.moneytracker.ui.theme.buttonDark
import com.example.moneytracker.ui.theme.buttonLight
import com.example.moneytracker.viewmodels.navigation.Screens

@Composable
fun Start(
    navController: NavController
) {
    Surface {
        Column (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(fraction = 0.85f).height(50.dp),
                    shape = RoundedCornerShape(20.dp),
                    onClick = {
                        navController.navigate(Screens.Commute.route)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = button,
                        contentColor = Color.White
                    )
                ) {
                    Text("Commute", fontFamily = Lexend)
                }

                AddHeight(20.dp)

                Button(
                    modifier = Modifier.fillMaxWidth(fraction = 0.85f).height(50.dp),
                    shape = RoundedCornerShape(20.dp),
                    onClick = {
                        navController.navigate(Screens.Items.route)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSystemInDarkTheme()) buttonDark else buttonLight,
                        contentColor = if (isSystemInDarkTheme()) Color.White else Color.Black
                    )
                ) {
                    Text("Item", fontFamily = Lexend)
                }
            }
        }
    }
}