package com.example.moneytracker.view

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moneytracker.ui.theme.Lexend
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun AddWidth(space: Dp) {
    Spacer(modifier = Modifier.width(space))
}

@Composable
fun AddHeight(space: Dp) {
    Spacer(modifier = Modifier.height(space))
}

@Composable
fun Input(
    label : String,
    value : String,
    onValueChange: (String) -> Unit,
    trailingIcon: (@Composable () -> Unit)? = null,
    color: Color,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    TextField(
        modifier = modifier
            .fillMaxWidth(fraction = 0.9f),
        label = {
            Text(
                label,
                fontFamily = Lexend
            )
        },
        value = value,
        onValueChange = onValueChange,
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = color,
            unfocusedContainerColor = color,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            disabledLabelColor = Color.Gray,
            unfocusedLabelColor = Color.Gray,
            focusedLabelColor = Color.Gray
        ),
        shape = RoundedCornerShape(10.dp),
        textStyle = androidx.compose.ui.text.TextStyle(
            fontSize = 15.sp,
            fontFamily = Lexend
        )
    )
}

@Composable
fun CommuteCard(
    source: String,
    destination: String,
    price: Int,
    date: Date
) {
    val formattedDate = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(date)

    Card(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFF66CCFF)), // light blue border
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            //contentAlignment = Alignment.Center
        ) {
            // Arrow Row
            Row(
                modifier = Modifier.fillMaxWidth().height(50.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier.weight(0.3f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(source, fontFamily = Lexend, fontSize = 13.sp)
                }

                Row (modifier = Modifier.weight(0.4f)) {

                }
                Box(
                    modifier = Modifier.weight(0.3f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(destination, fontFamily = Lexend, fontSize = 13.sp)
                }
            }

            Text(
                text = price.toString(),
                modifier = Modifier.align(Alignment.TopCenter),
                fontFamily = Lexend,
                fontSize = 14.sp
            )

            // Date below the arrow (center aligned)
            Text(
                text = formattedDate,
                modifier = Modifier.align(Alignment.BottomCenter),
                fontFamily = Lexend,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

fun splitRoute(route: String): Pair<String, String> {
    val parts = route.split("->")
    val source = parts.getOrNull(0).orEmpty()
    val destination = parts.getOrNull(1).orEmpty()
    return source to destination
}