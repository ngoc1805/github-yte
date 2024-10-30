package com.example.yte.HealthIndex

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yte.R

@Composable
fun BMI(){
    var height by remember{ mutableStateOf("0") }
    var weighed by remember{ mutableStateOf("0") }
    var BMI by remember{ mutableStateOf(0) }
    var BMISTR by remember{ mutableStateOf("") }
    var BMInote by remember{ mutableStateOf("") }
    val bmi = "BMI"
    var showAlertDialog by remember { mutableStateOf(false) }


    val backgroundColor = if (BMISTR.isNotEmpty()) {
        when (BMInote) {
            "Chỉ số bình thường" -> colorResource(id = R.color.darkblue)
            "Chỉ số thấp, thiếu cân" -> colorResource(id = R.color.cam)
            "Chỉ số cao, béo phì" -> colorResource(id = R.color.maudo)
            "Chỉ số khá cao, tiền béo phì" -> colorResource(id = R.color.mauhong)
            else -> Color.White
        }
    } else {
        Color.White
    }
    fun tinhBMI() {
        val heightDouble = height.toDoubleOrNull() ?: 0.0
        val weighedDouble = weighed.toDoubleOrNull() ?: 0.0
        if (heightDouble > 0 && weighedDouble > 0) {
            val result = weighedDouble / ((heightDouble / 100) * (heightDouble / 100))
            BMISTR = String.format("%.2f", result)
            BMInote = when {
                result < 18.5 -> "Chỉ số thấp, thiếu cân"
                result in 18.5..24.9 -> "Chỉ số bình thường"
                result in 25.0..29.9 -> "Chỉ số khá cao, tiền béo phì"
                else -> "Chỉ số cao, béo phì"
            }
        } else {
            BMISTR = ""
            BMInote = ""
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
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
                    painter = painterResource(id = R.drawable.rule),
                    contentDescription = null,
                    tint = colorResource(id = R.color.teal_200),
                    modifier = Modifier.size(24.dp)
                    )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = "$height cm",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.teal_200)
                )
                Text(
                    text = "Chiều cao",
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            }
            Spacer(modifier = Modifier.width(80.dp))
            Box(
                modifier = Modifier
                    .size(width = 24.dp, height = 40.dp)
                    .background(color = Color(0xFFE0F7FA), shape = RoundedCornerShape(24.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.weighed),
                    contentDescription = null,
                    tint = colorResource(id = R.color.teal_200),
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = "$weighed kg",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.teal_200)
                )
                Text(
                    text = "Cân nặng",
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
        Spacer(modifier = Modifier.height(16.dp))
        // lưu ý về chỉ số bmi
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
                    text = BMISTR,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                    )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = BMInote,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

        }
        // công thức tính BMI
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(color = Color.White),
        ) {
            Image(
                painter = painterResource(id = R.drawable.ctbmi),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
        // ảnh thang đo bmi
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(color = Color.White),
        ) {
            Image(
                painter = painterResource(id = R.drawable.bmi),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        // chú thích về BMI
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = 2.dp,
            backgroundColor = Color(0xFFEDE7F6)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp), // Khoảng cách bên trong Card
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(
                            color = colorResource(id = R.color.purple_200),
                            shape = CircleShape
                        ), // Màu nền tím đậm cho biểu tượng
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "BMI",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.width(16.dp)) // Khoảng cách giữa biểu tượng và văn bản

                // Văn bản mô tả
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(bmi)
                        }
                        append(" là chỉ số khối của cơ thể, dùng để đánh giá cơ thể của bạn đang thiếu cân, bình thường, thừa cân hay béo phì.")
                    },
                    fontSize = 14.sp,
                    color = Color(0xFF4A148C), // Màu chữ tím
                    lineHeight = 20.sp
                )
            }

        }
        if (showAlertDialog) {
            AlertDialog(
                onDismissRequest = { showAlertDialog = false },
                title = {
                    Text(
                        text = "Chỉ số BMI",
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
                            value = height,
                            onValueChange = { height = it },
                            label = { Text(text = "Chiều cao (cm)", color = Color.Black) },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal ),


                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Cân nặng (kg)
                        OutlinedTextField(
                            value = weighed,
                            onValueChange = { weighed = it },
                            label = { Text(text = "Cân nặng (kg)", color = Color.Black) },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal )
                        )
                    }
                },
                confirmButton = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(
                            onClick = {
                                tinhBMI()
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
    }

}




@Preview(showBackground = true)
@Composable
fun BMIPreview(){
    BMI()
}

