package com.example.yte.Home

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yte.AppBarView
import com.example.yte.Appointment.AppointmentViewModel
import com.example.yte.CCCD
import com.example.yte.Connect.ChucNang
import com.example.yte.Connect.ChucNangViewModel
import com.example.yte.Connect.DoctorViewModel
import com.example.yte.Connect.KetQuaKhamViewModel
import com.example.yte.Connect.KhamChucNang
import com.example.yte.Connect.KhamChucNangViewModel
import com.example.yte.Connect.LichKhamViewModel
import com.example.yte.Connect.NguoiDungViewModel
import com.example.yte.Connect.ThongBaoViewModel
import com.example.yte.R
import com.example.yte.chuyenDoiGio
import com.example.yte.chuyenDoiNgay
import com.example.yte.formatNumber
import com.example.yte.gioiTinh
import com.example.yte.hoTen
import com.example.yte.idBenhNhan
import com.example.yte.ngaySinh
import com.example.yte.soDu

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MedicalExamination(
    navController: NavController,
    appointmentViewModel: AppointmentViewModel = viewModel(),
    doctorViewModel: DoctorViewModel = viewModel(),
    khamChucNangViewModel: KhamChucNangViewModel = viewModel(),
    chucNangViewModel: ChucNangViewModel = viewModel(),
    ketQuaKhamViewModel: KetQuaKhamViewModel = viewModel(),
    nguoiDungViewModel: NguoiDungViewModel = viewModel(),
    thongBaoViewModel: ThongBaoViewModel = viewModel(),
    lichKhamViewModel: LichKhamViewModel = viewModel()
) {
    val selecLichKham = appointmentViewModel.selectedLichKham.value
    var idBacSi by remember { mutableStateOf("") }
    var gioKham by remember { mutableStateOf("") }
    var ngayKham by remember { mutableStateOf("") }
    var doctorName by remember { mutableStateOf("Đang tải...") }
    var khoa by remember { mutableStateOf("Đang tải...") }
    var giaKham by remember { mutableStateOf(0) }
    var idLichKham by remember { mutableStateOf(0) }
    var tongTienKhamChucNang by remember { mutableStateOf(0) }
    var isThanhToan by remember{ mutableStateOf(false) }
    val httpStatus by ketQuaKhamViewModel.httpStatus.observeAsState()
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }
    val lichkham by lichKhamViewModel.lichKham.observeAsState()
    var trangThai by remember { mutableStateOf("") }


    selecLichKham?.let { lichKham ->
        idBacSi = lichKham.idBacSi
        gioKham = lichKham.gioKham
        ngayKham = lichKham.ngayKham
        idLichKham = lichKham.idLichKham
        trangThai = lichKham.trangThai
    }
//    if (trangThai == "Đã thanh toán") isThanhToan = true
    LaunchedEffect(idBacSi) {
        val doctor = doctorViewModel.getDoctorByIdCached(idBacSi)
        if (doctor != null) {
            doctorName = doctor.hoTen
            khoa = doctor.khoa
            giaKham = doctor.giaKham
        }
    }
//    LaunchedEffect(idLichKham) {
//        ketQuaKhamViewModel.getKetQuaKhamByIdLichKham(idLichKham)
//        if(ketQuaKhamViewModel.httpStatus.value == 200){
//            isThanhToan = true
//        }else{
//            isThanhToan = false
//        }
//    }

    LaunchedEffect(idLichKham) {
        lichKhamViewModel.getLichHenByIdLichKham(idLichKham)
    }

//    LaunchedEffect(idLichKham) {
//        ketQuaKhamViewModel.getKetQuaKhamByIdLichKham(idLichKham)
//    }
    lichkham?.let { lichKham ->
        if (lichKham.trangThai == "y/c thanh toán"){
            isThanhToan = true
        }
    }

    // Theo dõi trạng thái HTTP và cập nhật isThanhToan
//    httpStatus?.let { status ->
//        isThanhToan = status == 200
//    }
    var tienKhamChuaTra = giaKham - 100000

    // Lấy danh sách KhamChucNang và thông tin ChucNang
    LaunchedEffect(idLichKham) {
        khamChucNangViewModel.getKhamChucNangByIdLichKham(idLichKham)
    }

    Column(modifier = Modifier.fillMaxSize()) {

        AppBarView(
            title = "Khám bệnh",
            color = R.color.black,
            backgroundColor = R.color.white,
            alignment = Alignment.Center,
            onDeleteNavClicked = { navController.popBackStack() },
            isVisible = true
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = 4.dp
        ) {
            Box(
                modifier = Modifier
                    .background(color = Color(0xFF1565C0))
                    .padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(top = 40.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.End, // Căn lề sang phải
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(horizontalAlignment = Alignment.End) {


                                Text(
                                    text = "Bác sĩ:  $doctorName",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Khoa: $khoa",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White

                                )
                        }
                        Spacer(modifier = Modifier.width(16.dp))

                        Image(
                            painter = painterResource(id = R.drawable.anhtrang),
                            contentDescription = null,
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(Color.White),
                            contentScale = ContentScale.Crop
                        )

                    }
                }
            }
            //
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier

                    .padding(top = 140.dp) // Đẩy phần giá xuống dưới
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Họ tên:  $hoTen",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Giới tính: $gioiTinh",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Ngày sinh: ${chuyenDoiNgay(ngaySinh)}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "CCCD: $CCCD",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }
        Text(
            text = "${chuyenDoiGio(gioKham)} ngày ${chuyenDoiNgay(ngayKham)} ",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Id lịch khám: $idLichKham",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Card(modifier = Modifier.padding(vertical = 8.dp)) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                Text(text = "Số tiền khám còn thiếu: ${formatNumber(tienKhamChuaTra)}VNĐ")
            }
        }
        LaunchedEffect(chucNangViewModel.chucnangMap) {
            // Tính tổng tiền từ chucnangMap
            tongTienKhamChucNang = chucNangViewModel.chucnangMap.values.sumOf { it.giaKham }
        }
        Card {
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(khamChucNangViewModel.khamChucNangList) { khamChucNang ->
                    // Lắng nghe thay đổi của idChucNang
                    LaunchedEffect(khamChucNang.idChucNang) {
                        chucNangViewModel.getChucNangByIdChucNang(khamChucNang.idChucNang)
                    }

                    // Kiểm tra xem có dữ liệu không từ Map
                    val chucNang = chucNangViewModel.chucnangMap[khamChucNang.idChucNang]
                    Spacer(modifier = Modifier.height(8.dp))
                    if (chucNang != null) {
                        rowKhamChucNang(chucNang = chucNang)

                    } else {
                        Text(text = "Đang tải dữ liệu...")
                    }
                }
            }
        }

        Card(modifier = Modifier.padding(vertical = 8.dp)) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                Text(text = "Tổng tiền: ${formatNumber(tienKhamChuaTra+tongTienKhamChucNang)}VNĐ")
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { /*TODO*/
                if(soDu < (tienKhamChuaTra+tongTienKhamChucNang) ){
                          dialogMessage = "Số dư không đủ, hãy nạp thêm"
                          showDialog = true
                      }
                else{
                    soDu = soDu - (tienKhamChuaTra+tongTienKhamChucNang)
                    nguoiDungViewModel.UpdatSoDu(idBenhNhan, soDu)

                    thongBaoViewModel.addThongBao(
                        idBenhNhan,
                        "Bạn đã thanh toán thành công ${formatNumber(tienKhamChuaTra+tongTienKhamChucNang)}VNĐ",
                        "Payment"
                    )
                    lichKhamViewModel.updateLichKhamTrangThai(idLichKham, "Đã thanh toán")
                    ketQuaKhamViewModel.updateTrangThaiThanhToan(idLichKham)
                    dialogMessage = "Thanh toán thành công"
                    showDialog = true
                }
                      },
            modifier = Modifier
                .width(200.dp)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.darkblue),
                contentColor = Color.White,

            ),
            shape = RoundedCornerShape(50),
            enabled = isThanhToan
        ) {
            Text(text = "Thanh toán")
        }
        Spacer(modifier = Modifier.height(8.dp))


    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Thông báo") },
            text = { Text(text = dialogMessage) },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false

                    if(dialogMessage == "Thanh toán thành công"){
                        navController.navigate("Home")
                    }
                    else if(dialogMessage == "Số dư không đủ, hãy nạp thêm"){
                        navController.navigate("Payment")

                    }

                }) {
                    Text("OK")
                }
            }
        )
    }
}

@Composable
fun rowKhamChucNang(
    chucNang: ChucNang,
   
){
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = " ${chucNang.tenChucNang}")
        Spacer(modifier = Modifier.weight(1f))
        Text(text = " ${formatNumber(chucNang.giaKham)}VNĐ")
    }
}