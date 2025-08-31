package com.example.moneytracker.view

import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moneytracker.models.model.AddCommuteRequest
import com.example.moneytracker.ui.theme.Lexend
import com.example.moneytracker.ui.theme.button
import com.example.moneytracker.ui.theme.buttonDark
import com.example.moneytracker.ui.theme.buttonLight
import com.example.moneytracker.viewmodels.viewmodel.CommuteViewModel
import com.example.moneytracker.viewmodels.viewmodel.UserViewModel

@Composable
fun Commute(
    commuteViewModel: CommuteViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel()
) {
    Surface {
        val (from, setFrom) = remember { mutableStateOf("") }
        val (to, setTo) = remember { mutableStateOf("") }
        val color = if (isSystemInDarkTheme()) buttonDark else buttonLight
        var add by remember { mutableStateOf(false) }
        var get by remember { mutableStateOf(false) }
        var del by remember { mutableStateOf(false) }
        val keyboardController = LocalSoftwareKeyboardController.current
        val context = LocalContext.current
        val user = userViewModel.userData.collectAsState()
        val addCommuteResult = commuteViewModel.addCommuteResult.collectAsState()
        val getCommuteResult = commuteViewModel.getCommuteResult.collectAsState()
        val delCommuteResult = commuteViewModel.deleteCommuteResult.collectAsState()

        LaunchedEffect(addCommuteResult) {
            addCommuteResult.let {
                Toast.makeText(context, "Commute added", Toast.LENGTH_SHORT).show()
            }
        }

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (!add && !get && !del) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.85f)
                        .height(50.dp),
                    shape = RoundedCornerShape(20.dp),
                    onClick = {
                        add = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = button,
                        contentColor = Color.White
                    )
                ) {
                    Text("Add Commute", fontFamily = Lexend)
                }

                AddHeight(20.dp)

                Button(
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.85f)
                        .height(50.dp),
                    shape = RoundedCornerShape(20.dp),
                    onClick = {
                        get = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSystemInDarkTheme()) buttonDark else buttonLight,
                        contentColor = if (isSystemInDarkTheme()) Color.White else Color.Black
                    )
                ) {
                    Text("Get Previous", fontFamily = Lexend)
                }

                AddHeight(20.dp)

                Button(
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.85f)
                        .height(50.dp),
                    shape = RoundedCornerShape(20.dp),
                    onClick = {
                        del = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSystemInDarkTheme()) buttonDark else buttonLight,
                        contentColor = if (isSystemInDarkTheme()) Color.White else Color.Black
                    )
                ) {
                    Text("Delete Commutes", fontFamily = Lexend)
                }
            }

            if (add) {
                Row (
                    modifier = Modifier.fillMaxWidth(fraction = 0.9f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text("Start", fontFamily = Lexend)
                }

                AddHeight(20.dp)

                Input(
                    label = "from",
                    value = from,
                    onValueChange = setFrom,
                    color = color
                )

                AddHeight(20.dp)

                Row (
                    modifier = Modifier.fillMaxWidth(fraction = 0.9f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text("Destination", fontFamily = Lexend)
                }

                AddHeight(20.dp)

                Input(
                    label = "To",
                    value = to,
                    onValueChange = setTo,
                    color = color,
                )

                AddHeight(40.dp)

                Button(
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.9f)
                        .height(50.dp),
                    shape = RoundedCornerShape(20.dp),
                    onClick = {
                        if (from.isNotEmpty() && to.isNotEmpty()) {
                            keyboardController?.hide()
                            user.value.let {
                                commuteViewModel.addCommute(
                                    userId = it.id,
                                    addCommuteRequest = AddCommuteRequest(0, "$from->$to"),
                                    token = "Bearer ${it.token}"
                                )
                            }
                        }
                        else if (from.isEmpty()) {
                            Toast.makeText(context, "Source Empty", Toast.LENGTH_SHORT).show()
                        }
                        else if (to.isEmpty()) {
                            Toast.makeText(context, "Destination Empty", Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = button,
                        contentColor = Color.White
                    )
                ) {
                    Text("Login", fontFamily = Lexend)
                }
            }
        }
    }
}