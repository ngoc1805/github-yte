package com.example.yte.Home

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yte.AppBarView
import com.example.yte.Appointment.AppointmentViewModel
import com.example.yte.Connect.ChucNangViewModel
import com.example.yte.Connect.FileKetQua
import com.example.yte.Connect.FileKetQuaViewModel
import com.example.yte.Connect.LichKhamViewModel
import com.example.yte.R
import com.example.yte.address

@Composable
fun medicalExaminationResults(
    appointmentViewModel: AppointmentViewModel = viewModel(),
    navController: NavController,
    lichKhamViewModel: LichKhamViewModel = viewModel(),
    fileKetQuaViewModel: FileKetQuaViewModel = viewModel()
){
    val ketQuaKham = appointmentViewModel.ketQuaKham.value
    var idLichKham by remember { mutableStateOf(0) }
    var nhanXet by remember { mutableStateOf("") }
    var ngayTraKetQua by remember { mutableStateOf("") }
    var daThanhToan by remember { mutableStateOf(true) }
    val lichKham by lichKhamViewModel.lichKham.observeAsState()
    val context = LocalContext.current
    
    ketQuaKham?.let { ketQuaKham ->
        idLichKham = ketQuaKham.idLichKham
        nhanXet = ketQuaKham.nhanXet
        ngayTraKetQua = ketQuaKham.ngayTraKetQua
        daThanhToan = ketQuaKham.daThanhToan
    }
    LaunchedEffect(idLichKham) {
        lichKhamViewModel.getLichHenByIdLichKham(idLichKham)
    }
    LaunchedEffect(lichKham) {
        if (lichKham != null) {
            appointmentViewModel.selectedLichKham.value = lichKham
        }
    }
    LaunchedEffect(idLichKham) {
        fileKetQuaViewModel.getFileKetQuaByIdLichKham(idLichKham)
    }
    val filesKetQua = fileKetQuaViewModel.fileKetQuaList
    Column(modifier = Modifier.fillMaxSize()) {
        AppBarView(
            title = "Kết quả khám bệnh",
            color = R.color.white,
            backgroundColor = R.color.darkblue,
            alignment = Alignment.Center,
            isVisible = true,
            onDeleteNavClicked = {navController.popBackStack()}
        )
//        if(daThanhToan == true){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Nhận xét:",
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    elevation = 4.dp
                ) {
                    Text(
                        text = nhanXet,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onSurface
                    )
                }
                LazyColumn {
                    items(filesKetQua){fileKetQua ->
                        fileKetQuaCard(
                            fileKetQua = fileKetQua,
                            onClicked = { openUrl(context, "$address/files/${fileKetQua.tenFile}") }
                        )
                    }
                }

            }
            //
//        }else{
//            Column(
//                modifier = Modifier
//                    .fillMaxSize(),
//                horizontalAlignment = Alignment.CenterHorizontally // Căn giữa các phần tử theo chiều ngang
//            ) {
//                Spacer(modifier = Modifier.height(16.dp))
//
//                // Hiển thị thông báo
//                Text(
//                    text = "Vui lòng thanh toán để xem kết quả khám bệnh",
//                    fontSize = 16.sp,
//                    modifier = Modifier.align(Alignment.CenterHorizontally)
//                )
//
//
//                Spacer(modifier = Modifier.weight(1f)) // Đẩy phần tử bên dưới xuống cuối màn hình
//
//                // Nút thanh toán
//                Button(
//                    onClick = { navController.navigate("MedicalExamination") },
//                    modifier = Modifier
//                        .width(350.dp)
//                        .align(Alignment.CenterHorizontally),
//                    colors = ButtonDefaults.buttonColors(
//                        backgroundColor = colorResource(id = R.color.darkblue),
//                        contentColor = Color.White,
//
//                        ),
//                    shape = RoundedCornerShape(50),
//
//                    ) {
//                    androidx.compose.material.Text(text = "Chuyển đến trang thanh toán")
//                }
//                Spacer(modifier = Modifier.height(16.dp)) // Thêm khoảng cách dưới cùng nếu cần
//            }
        }
    }


@Composable
fun fileKetQuaCard(
    fileKetQua: FileKetQua,
    onClicked: () -> Unit = {},
    chucNangViewModel: ChucNangViewModel = viewModel()
){
    var tenChucNang by remember { mutableStateOf("Đang tải...") }
    val chucNang = chucNangViewModel.chucNang.observeAsState()
    LaunchedEffect(fileKetQua.idChucNang) {
        chucNangViewModel.getChucNangByIdChucNang(fileKetQua.idChucNang)
    }
    LaunchedEffect(chucNang.value) {
        if (chucNang.value != null) {
            tenChucNang = chucNang.value!!.tenChucNang
        }
    }
    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 16.dp)
            .clickable { onClicked() },
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp
    ) {
        Text(
            text = "Kết quả khám: $tenChucNang",
            modifier = Modifier.padding(16.dp),
        )
    }
}
fun openUrl(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}