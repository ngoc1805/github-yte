package com.example.yte.Home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.yte.AppBarView
import com.example.yte.R
@Composable
fun Booking(navController: NavController){
    // Trạng thái cuộn của LazyColumn
    val listState = rememberLazyListState()
    // Kiểm tra xem dòng "Chọn khoa khám bệnh" có hiển thị không
    val isHeaderVisible by remember {
        derivedStateOf { listState.firstVisibleItemIndex == 0 }
    }
    val OutpatientClinic = listOf(
        "Khoa nội ",
        "Khoa ngoại",
        "Khoa sản",
        "Khoa nhi",
        "Khoa mắt",
        "Khoa tai mũi họng",
        "Khoa răng hàm mặt",
        "Khoa da liễu",
        "Khoa thần kinh",
        "Khoa ung bứu"
    )
    val OutpatientClinicDetail = listOf(
        "chuyên khám và điều trị các bệnh về nội tạng, tim mạch, tiêu hóa, hô hấp, thần kinh,...",
        "chuyên khám và điều trị bằng phẫu thuật các vấn đề liên quan đến cơ xương khớp, chấn thương, bỏng,..",
        "chuyên về chăm sóc sức khỏe phụ nữ, mang thai, sinh nở và các vấn đề sản phụ khoa...",
        "chuyên khám và điều trị cho trẻ em.",
        "chuyên khám và điều trị các bệnh về mắt.",
        "chuyên khám và điều trị các vấn đề về tai, mũi, họng.",
        "chuyên về các bệnh lý răng miệng, hàm và mặt.",
        "chuyên về các bệnh lý về da, tóc, móng.",
        "chuyên về các bệnh lý thần kinh, ví dụ như đau đầu, rối loạn giấc ngủ, động kinh.",
        "chuyên khám và điều trị các loại bệnh ung thư."
    )
    Column(modifier = Modifier.fillMaxSize()) {
        AppBarView(
            title = "Đặt lịch khám",
            color = R.color.white,
            backgroundColor = R.color.darkblue ,
            alignment = Alignment.Center,
            onDeleteNavClicked = { navController.popBackStack()}
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (isHeaderVisible) {
            Text(
                text = " -> Chọn khoa khám bệnh",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.teal_700),
                modifier = Modifier.padding(horizontal = 16.dp)
            )

        }
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(modifier = Modifier.fillMaxSize(), state = listState) {
            items(OutpatientClinic.size){ index ->
                ClinicCard(
                    title = OutpatientClinic[index],
                    detail = OutpatientClinicDetail[index],
                    onClick = {
                        navController.navigate("clinicDetail/${OutpatientClinic[index]}")
                    }
                )

            }

        }

    }
}

@Composable
fun ClinicCard(title: String, detail: String,  onClick: () -> Unit = {}){
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.darkblue)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = detail,
                fontSize = 14.sp,
                color = MaterialTheme.colors.onBackground
            )

        }

    }
}

@Composable
fun ClinicDetailScreen(clinicName: String, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize(),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AppBarView(
            title = "Chọn bác sĩ",
            color = R.color.white ,
            backgroundColor = R.color.darkblue,
            alignment = Alignment.Center,
            onDeleteNavClicked = {navController.popBackStack()}
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Bác sĩ $clinicName",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

    }
}


