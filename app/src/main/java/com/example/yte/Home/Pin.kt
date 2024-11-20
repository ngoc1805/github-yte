package com.example.yte.Home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.Circle
import androidx.compose.material3.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.example.yte.R

@Composable
fun PinCodeScreen(onPinEntered: (String) -> Unit){
    var pin by remember { mutableStateOf("") } // Trạng thái mã PIN
    val maxPinLength = 6 // Số ký tự tối đa

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Tiêu đề
        Text(
            text = "Nhập passcode",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            repeat(maxPinLength) { index ->
                val isFilled = index < pin.length
                Circle(isFilled)
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            // Các nút số
            (1..9).forEach { number ->
                item {
                    NumberButton(
                        number = number.toString(),
                        onClick = {
                            if (pin.length < maxPinLength) {
                                pin += number.toString()
                            }
                            if (pin.length == maxPinLength) {
                                onPinEntered(pin)
                            }
                        }
                    )
                }
            }
            // Nút "0"
            item { Spacer(modifier = Modifier.size(64.dp)) } // Chừa khoảng trống
            item {
                NumberButton(
                    number = "0",
                    onClick = {
                        if (pin.length < maxPinLength) {
                            pin += "0"
                        }
                        if (pin.length == maxPinLength) {
                            onPinEntered(pin)
                        }
                    }
                )
            }
            // Nút xóa
            item {
                IconButton(
                    onClick = {
                        if (pin.isNotEmpty()) {
                            pin = pin.dropLast(1)
                        }
                    },
                    modifier = Modifier.size(64.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.delete), // Thay bằng icon xóa
                        contentDescription = "Xóa",
                        tint = Color.Black
                    )
                }
            }
//            TextButton(onClick = { /* Xử lý quên mã PIN */ }) {
//                Text(text = "Quên passcode")
//            }
        }

    }
}

@Composable
fun Circle(isFilled: Boolean) {
    Box(
        modifier = Modifier
            .size(16.dp)
            .clip(CircleShape)
            .background(if (isFilled) Color.Black else Color.Gray)
    )
}

@Composable
fun NumberButton(number: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(64.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)
    ) {
        Text(
            text = number,
            style = MaterialTheme.typography.h6,
            color = Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PinCodeScreenPreview(){
    PinCodeScreen(
        onPinEntered = { pin ->
            // Print PIN for debugging in preview
            println("Pin Entered: $pin")
        }
    )
}