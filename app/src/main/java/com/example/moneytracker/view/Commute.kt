package com.example.moneytracker.view

import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moneytracker.models.model.AddCommuteRequest
import com.example.moneytracker.ui.theme.Lexend
import com.example.moneytracker.ui.theme.button
import com.example.moneytracker.ui.theme.buttonDark
import com.example.moneytracker.ui.theme.buttonLight
import com.example.moneytracker.viewmodels.viewmodel.CommuteViewModel
import com.example.moneytracker.viewmodels.viewmodel.UserViewModel
import java.time.Instant
import java.time.ZoneId
import java.util.Date

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Commute(
    commuteViewModel: CommuteViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel()
) {
    Surface {
        val (from, setFrom) = remember { mutableStateOf("") }
        val (to, setTo) = remember { mutableStateOf("") }
        var (price) = remember { mutableIntStateOf(0) }
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
        var selectedDate by remember { mutableStateOf("") }
        var showDatePicker by remember { mutableStateOf(false) }

        LaunchedEffect(addCommuteResult) {
            addCommuteResult.let {
                Toast.makeText(context, "Commute added", Toast.LENGTH_LONG).show()
            }
        }

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!add && !get && !del) {
                Column (
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
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
                            user.value.let {
                                commuteViewModel.getCommute(
                                    userId = it.id,
                                    token = "Bearer ${it.token}"
                                )
                            }
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

                AddHeight(20.dp)

                Row (
                    modifier = Modifier.fillMaxWidth(fraction = 0.9f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Input(
                        label = "Price",
                        value = price.toString(),
                        onValueChange = { price = it.toIntOrNull() ?: 0 },
                        color = color
                    )

                    Card(
                        modifier = Modifier.size(40.dp),
                        shape = CircleShape,
                        colors = CardDefaults.cardColors(
                            containerColor = button,
                            contentColor = Color.White
                        )
                    ) {
                        IconButton(
                            onClick = {
                                showDatePicker = true
                            }
                        ) {
                            Icon(
                                Icons.Default.CalendarMonth,
                                contentDescription = null
                            )
                        }
                    }
                }

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
                                    addCommuteRequest = AddCommuteRequest(0, "","$from->$to"),
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

                if (showDatePicker) {
                    val datePickerState = rememberDatePickerState()

                    DatePickerDialog(
                        onDismissRequest = { showDatePicker = false },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    val millis = datePickerState.selectedDateMillis
                                    if (millis != null) {
                                        val localDate = Instant.ofEpochMilli(millis)
                                            .atZone(ZoneId.systemDefault())
                                            .toLocalDate()
                                        selectedDate = localDate.toString()
                                    }
                                    showDatePicker = false
                                }
                            ) { Text("OK") }
                        },
                        dismissButton = {
                            TextButton(onClick = { showDatePicker = false }) {
                                Text("Cancel")
                            }
                        }
                    ) {
                        DatePicker(state = datePickerState)
                    }
                }
            }

            if (get) {
                if (getCommuteResult.value == null) {
                    Box (
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularWavyProgressIndicator()
                    }
                }
                else {
                    ConstraintLayout (
                        modifier = Modifier.fillMaxSize()
                    ) {
                        val (topRow) = createRefs()

                        Box(
                            modifier = Modifier
                                .constrainAs(topRow) {
                                    top.linkTo(parent.top)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                }
                                .fillMaxWidth(0.9f)
                        ) {
                            Text(
                                "Month",
                                modifier = Modifier.align(Alignment.Center),
                            )
                            IconButton(
                                onClick = {},
                                modifier = Modifier.align(Alignment.CenterEnd)
                            ) {
                                Icon(
                                    Icons.Default.CalendarMonth,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}