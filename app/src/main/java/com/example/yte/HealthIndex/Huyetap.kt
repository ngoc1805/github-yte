package com.example.yte.HealthIndex

import androidx.compose.foundation.Image
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
fun Huyetap(){
    var Tamthu by remember{ mutableStateOf("0") }
    var Tamtruong by remember{ mutableStateOf("0") }
    var Huyetap by remember{ mutableStateOf("") }
    var Huyetapnote by remember{ mutableStateOf("") }
    val tamthu = "Huyết áp tâm thu"
    val tamtruong = "Huyết áp tâm trương"
    var showAlertDialog by remember { mutableStateOf(false) }

    val backgroundColor = if (Huyetapnote.isNotEmpty()) {
        when (Huyetap) {
            "Huyết áp tối ưu" -> colorResource(id = R.color.darkblue)
            "Huyết áp bình thường" -> colorResource(id = R.color.darkblue)
            "Huyết áp cao bình thường" -> colorResource(id = R.color.darkblue)
            "Huyết áp tăng vừa (cấp độ 1)" -> colorResource(id = R.color.cam)
            "Huyết áp tăng vừa (cấp độ 2)" -> colorResource(id = R.color.mauhong)
            "Huyết áp tăng nghiêm trọng (cấp độ 3)" -> colorResource(id = R.color.maudo)
            else -> Color.White
        }
    } else {
        Color.White
    }

    fun tinhHuyetap(){
        val TamthuInt = Tamthu.toIntOrNull() ?: 0
        val TamtruongInt = Tamtruong.toIntOrNull() ?: 0
        if(TamthuInt in 1..120 && TamtruongInt in 1..80)
        {
            Huyetap = "Huyết áp tối ưu"
            Huyetapnote = "Tuy nhiên, bạn vẫn cần phải theo dõi thường xuyên"
        }
        else if(TamthuInt in 120..129 && TamtruongInt in 80..84)
        {
            Huyetap = "Huyết áp bình thường"
            Huyetapnote = "Tuy nhiên, bạn vẫn cần phải theo dõi thường xuyên"
        }
        else if(TamthuInt in 130..139 || TamtruongInt in 85..89)
        {
            Huyetap = "Huyết áp cao bình thường"
            Huyetapnote = "Thuờng xuyên kiểm tra bởi bác sĩ"
        }
        else if(TamthuInt in 140..159 || TamtruongInt in 90..99)
        {
            Huyetap = "Huyết áp tăng vừa (cấp độ 1)"
            Huyetapnote = "Thuờng xuyên kiểm tra bởi bác sĩ"
        }
        else if(TamthuInt in 160..179 || TamtruongInt in 100..109)
        {
            Huyetap = "Huyết áp tăng vừa (cấp độ 2)"
            Huyetapnote = "Đi khám tại CSYT gần nhất"
        }
        else if(TamthuInt >= 180 || TamtruongInt >=110)
        {
            Huyetap = "Huyết áp tăng nghiêm trọng (cấp độ 3)"
            Huyetapnote = "Đi khám tại CSYT gần nhất"
        }
        else
        {
            Huyetap = ""
            Huyetapnote = ""
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
                    painter = painterResource(id = R.drawable.systolic),
                    contentDescription = null,
                    tint = colorResource(id = R.color.teal_200),
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = "$Tamthu mmHg",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.teal_200)
                )
                Text(
                    text = "Tâm thu",
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
                    painter = painterResource(id = R.drawable.diastolic),
                    contentDescription = null,
                    tint = colorResource(id = R.color.teal_200),
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = "$Tamtruong mmHg",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.teal_200)
                )
                Text(
                    text = "Tâm trương",
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
        // lưu ý về chỉ số huyết áp
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
                    text = Huyetap,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = Huyetapnote,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

        }
        // ảnh
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(color = Color.White),
        ) {
            Image(
                painter = painterResource(id = R.drawable.huyetap),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        // chú thích về chỉ số
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
                        .size(8.dp)
                        .background(
                            color = colorResource(id = R.color.purple_200),
                            shape = CircleShape
                        ), // Màu nền tím đậm cho biểu tượng
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "",
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
                            append(tamthu)
                        }
                        append(" là mức huyết áp cao nhất trong mạch máu, xảy ra khi tim co bóp.")
                    },
                    fontSize = 14.sp,
                    color = Color(0xFF4A148C), // Màu chữ tím
                    lineHeight = 20.sp
                )
            }

        }
        //
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
                        .size(8.dp)
                        .background(
                            color = colorResource(id = R.color.purple_200),
                            shape = CircleShape
                        ), // Màu nền tím đậm cho biểu tượng
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "",
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
                            append(tamtruong)
                        }
                        append(" là mức huyết áp thấp nhất trong mạch máu và xảy ra giữa các lần tim co bóp, khi cơ tim được thả lỏng.")
                    },
                    fontSize = 14.sp,
                    color = Color(0xFF4A148C), // Màu chữ tím
                    lineHeight = 20.sp
                )
            }

        }
        //
        if (showAlertDialog) {
            AlertDialog(
                onDismissRequest = { showAlertDialog = false },
                title = {
                    Text(
                        text = "Chỉ số Huyết áp",
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
                            value = Tamthu,
                            onValueChange = { Tamthu = it },
                            label = { Text(text = "Tâm thu mmHg (Huyết áp tối đa)", color = Color.Black) },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal ),


                            )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Cân nặng (kg)
                        OutlinedTextField(
                            value = Tamtruong,
                            onValueChange = { Tamtruong = it },
                            label = { Text(text = "Tâm trương mmHg (Huyết áp tối thiểu)", color = Color.Black) },
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
                                tinhHuyetap()
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
fun HuyetapPreview(){
    Huyetap()
}