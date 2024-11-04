package com.example.yte.HealthIndex

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yte.R

@Composable
fun Temperature(){
    var Temperature by remember{ mutableStateOf("0") }
    var temperature by remember{ mutableStateOf("0") }
    var temperaturenote by remember{ mutableStateOf("") }
    var showAlertDialog by remember { mutableStateOf(false) }

    val backgroundColor = if(temperaturenote.isNotEmpty()){
        when (temperature){
            "Nhiệt độ bình thường" -> colorResource(id = R.color.darkblue)
            "Hạ thân nhiệt" -> colorResource(id = R.color.purple_500)
            "Sốt nhẹ" -> colorResource(id = R.color.cam)
            "Sốt cao" -> colorResource(id = R.color.maudo)
            else -> Color.White
        }

    }else {
        Color.White
    }

    fun tinhTemp(){
        val TemperatureDouble = Temperature.toDoubleOrNull() ?: 0.0
        if(TemperatureDouble in 1.0..35.5)
        {
            temperature =  "Hạ thân nhiệt"
            temperaturenote = "Cần theo dõi thường xuyên"
        }
        else if(TemperatureDouble in 35.5..37.5)
        {
            temperature =  "Nhiệt độ bình thường"
            temperaturenote = "Tuy nhiên, vẫn cần phải theo dõi thường xuyên"
        }else if(TemperatureDouble in 37.6..38.9)
        {
            temperature =  "Sốt nhẹ"
            temperaturenote = "Uống thuốc hạ sốt theo đơn của bác sĩ"
        }
        else if(TemperatureDouble >= 39)
        {
            temperature =  "Sốt cao"
            temperaturenote = "Cần đến CSYT gần nhất"
        }
        else
        {
            temperature = ""
            temperaturenote = ""
        }

    }

    Column(Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text ="  Chỉ số gần đây",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        // các số đo
        Row( verticalAlignment = Alignment.CenterVertically,modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.width(4.dp))

            Box(
                modifier = Modifier
                    .size(width = 24.dp, height = 40.dp)
                    .background(color = Color(0xFFE0F7FA), shape = RoundedCornerShape(24.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.temperature),
                    contentDescription = null,
                    tint = colorResource(id = R.color.teal_200),
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = "$Temperature °C",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.teal_200)
                )
                Text(
                    text = "Nhiệt độ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            }
            Text(text = "", modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .size(width = 24.dp, height = 40.dp)
                    .background(
                        color = colorResource(id = R.color.teal_200),
                        shape = RoundedCornerShape(24.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = { /*TODO*/
                        showAlertDialog = true
                    }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }


            }
            Spacer(modifier = Modifier.width(8.dp))
        }
        //
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 2.dp,
            backgroundColor = backgroundColor
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = temperature,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = temperaturenote,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

        }
        //
        if (showAlertDialog) {
            AlertDialog(
                onDismissRequest = { showAlertDialog = false },
                title = {
                    Text(
                        text = "Chỉ số Nhiệt độ",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.teal_700)
                    )
                },
                text = {
                    Column {
                        Text(
                            text = "Nhập chỉ số hiện tại của bạn",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Chiều cao (cm)
                        OutlinedTextField(
                            value = Temperature,
                            onValueChange = { Temperature = it },
                            label = { Text(text = "Nhiệt độ (°C)", color = Color.Black) },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal ),
                            )

                        Spacer(modifier = Modifier.height(8.dp))
                    }
                },
                confirmButton = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(
                            onClick = {
                                tinhTemp()
                                showAlertDialog = false },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = colorResource(id = R.color.teal_700),
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .width(140.dp) // Đặt chiều rộng tự động vừa với nội dung
                                .shadow(6.dp),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Thêm chỉ số", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                },
                modifier = Modifier
                    .padding(16.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White)
            )
        }
        //

    }
}

@Preview(showBackground = true)
@Composable
fun TemperaturePreview(){
    Temperature()
}