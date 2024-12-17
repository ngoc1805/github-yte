package com.example.yte.Home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yte.Connect.ThongBao
import com.example.yte.Connect.ThongBaoViewModel
import com.example.yte.R
import com.example.yte.idBenhNhan
import com.example.yte.soSanhThoiGian

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Notice(
    navController: NavController,
    thongBaoViewModel: ThongBaoViewModel = viewModel()
){
    LaunchedEffect(idBenhNhan) {
        thongBaoViewModel.getThongBao(idBenhNhan)
    }
    val thongBaos = thongBaoViewModel.thongBaoList

    if(thongBaos.size == 0){
        Column {
            Spacer(modifier = Modifier.height(16.dp))
            Box(modifier = Modifier.fillMaxSize()) {  // Box to center content
                androidx.compose.material3.Text(
                    text = "Không có dữ liệu",
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
        }

    }
    else{
        LazyColumn {
            items(thongBaos) {thongBao ->
                noticeCard(
                    thongBao = thongBao,
                    onClicked = {
                        thongBaoViewModel.updateDaxem(thongBao.idThongBao, true)
                        navController.navigate("${thongBao.duongDan}")
                    }
                )
            }
        }
    }


}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun noticeCard(
    thongBao: ThongBao,
    thongBaoViewModel: ThongBaoViewModel = viewModel(),
    onClicked: () -> Unit = {}
){
    var color = if(thongBao.daXem){
        colorResource(id = R.color.black)} else {
        colorResource(id = R.color.white)}
    var backGround = if(thongBao.daXem){
        colorResource(id = R.color.white)} else {
        colorResource(id = R.color.darkblue)}
    var colorIcon = if(thongBao.daXem){
        colorResource(id = R.color.darkblue)} else {
        colorResource(id = R.color.white)}

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClicked() },
        elevation = 4.dp,
        backgroundColor = backGround
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.notice),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 0.dp),
                colorFilter = ColorFilter.tint(colorIcon)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f) // Chiếm toàn bộ không gian còn lại
            ) {
                // Nội dung thông báo
                Text(
                    text = thongBao.noiDung,
                    fontSize = 14.sp,
                    color = color,
                    maxLines = 2, // Giới hạn tối đa 2 dòng
                    lineHeight = 18.sp // Đảm bảo khoảng cách dòng rõ ràng
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = soSanhThoiGian(thongBao.thoiGian),
                fontSize = 12.sp,
                color = color,
                modifier = Modifier.padding(start = 8.dp), // Thêm khoảng cách với nội dung
                maxLines = 1 // Giới hạn 1 dòng cho thời gian
            )

        }
    }
}