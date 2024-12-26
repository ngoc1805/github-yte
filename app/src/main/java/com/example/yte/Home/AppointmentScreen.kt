package com.example.yte.Home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.AlertDialog
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yte.Appointment.AppointmentViewModel
import com.example.yte.Connect.DoctorViewModel
import com.example.yte.Connect.LichKham
import com.example.yte.Connect.LichKhamViewModel
import com.example.yte.Connect.NguoiDungViewModel
import com.example.yte.Connect.ThongBaoViewModel
import com.example.yte.Firebase.ChatViewModel
import com.example.yte.R
import com.example.yte.chuyenDoiGio
import com.example.yte.chuyenDoiNgay
import com.example.yte.fcmToken
import com.example.yte.formatNumber
import com.example.yte.hoTen
import com.example.yte.idBenhNhan
import com.example.yte.soDu
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.temporal.ChronoUnit

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppoimentmentScreen(
    lichKhamViewModel: LichKhamViewModel = viewModel(),
    appointmentViewModel: AppointmentViewModel = viewModel(),
    navController: NavController
){
    LaunchedEffect(idBenhNhan) {
        lichKhamViewModel.getLichKhamByIdbn(idBenhNhan)
    }
    val lichKhams = lichKhamViewModel.lichKhamList
    if(lichKhams.size == 0){
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
    else {
        LazyColumn {
            items(lichKhams) { lichKham ->
                ApoimentCard(
                    lichKham = lichKham,
                    onClicked = {
                        appointmentViewModel.selectedLichKham.value = lichKham
                        navController.navigate("MedicalExamination")
                    }
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ApoimentCard(
    lichKham: LichKham,
    doctorViewModel: DoctorViewModel = viewModel(),
    lichKhamViewModel: LichKhamViewModel = viewModel(),
    thongBaoViewModel: ThongBaoViewModel = viewModel(),
    appointmentViewModel: AppointmentViewModel = viewModel(),
    nguoiDungViewModel: NguoiDungViewModel = viewModel(),
    chatViewModel: ChatViewModel = viewModel(),
    onClicked: () -> Unit = {}
){
    var doctorName by remember { mutableStateOf("Đang tải...") }
    var khoa by remember { mutableStateOf("Đang tải...") }
    var giaKham by remember { mutableStateOf(0) }

    LaunchedEffect(lichKham.idBacSi) {
        val doctor = doctorViewModel.getDoctorByIdCached(lichKham.idBacSi)
        if (doctor != null) {
            doctorName = doctor.hoTen
            khoa = doctor.khoa
            giaKham = doctor.giaKham
        }
    }

//    LaunchedEffect(lichKham.idBacSi) {
//         doctorViewModel.getDoctorById(lichKham.idBacSi)
//    }
//    val doctor = doctorViewModel.doctor
//    val doctorName = doctor?.hoTen ?: "Đang tải..."
//    val khoa = doctor?.khoa ?: "Đang tải..."
//    val giaKham = doctor?.giaKham ?: 0



    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }

    // Lấy ngày hiện tại
    val currentDate = java.time.LocalDate.now()

    // Chuyển ngày đặt lịch từ String sang LocalDate
    val appointmentDate = try {
        java.time.LocalDate.parse(chuyenDoiNgay( lichKham.ngayKham), java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    } catch (e: Exception) {
        null
    }

    // Kiểm tra nếu ngày hiện tại lớn hơn ngày đặt lịch
//    val isPastAppointment = appointmentDate?.isEqual(currentDate) == true
    val isAppointmentValid = appointmentDate?.let {
        val daysDifference = ChronoUnit.DAYS.between(currentDate, it)
        daysDifference >= 3 // Ngày hiện tại bé hơn ngày được đặt 3 ngày hoặc hơn thì true
    } ?: false



    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClicked() },
        elevation = 4.dp
    ) {
       Row(
           modifier = Modifier
               .padding(16.dp)
               .fillMaxWidth(),
           verticalAlignment = Alignment.CenterVertically
       ) {
           Image(
               painter = painterResource(id = R.drawable.doctor_appointment),
               contentDescription = null,
               modifier = Modifier
                   .size(48.dp)
                   .padding(end = 0.dp),
               colorFilter = ColorFilter.tint(colorResource(id = R.color.darkblue))
           )
           Spacer(modifier = Modifier.width(16.dp))
           Column {
               Text(
                   text = "Bác sĩ: $doctorName",
                   fontSize = 17.sp,
                   fontWeight = FontWeight.Bold
               )
               Spacer(modifier = Modifier.height(4.dp))
               Text(
                   text = "$khoa",
                   fontSize = 14.sp,
                   color = Color.Gray
               )
               Spacer(modifier = Modifier.height(4.dp))
               Text(
                   text = "${chuyenDoiGio(lichKham.gioKham)} ngày ${chuyenDoiNgay(lichKham.ngayKham)}",
                   fontSize = 14.sp,
                   color = Color.Gray
               )
               Spacer(modifier = Modifier.height(16.dp))
               Row {
                   Image(
                       painter = painterResource(id = R.drawable.coin) ,
                       contentDescription =null,
                       modifier = Modifier.size(13.dp),
                       colorFilter = ColorFilter.tint(colorResource(id = R.color.darkblue))
                   )
                   Spacer(modifier = Modifier.width(4.dp))
                   Text(
                       text = "Giá khám: ${formatNumber(giaKham)} VND",
                       fontSize = 12.sp,
                       fontWeight = FontWeight.Bold,
                       color = colorResource(id = R.color.darkblue)
                   )
               }

           }
           Spacer(modifier = Modifier.weight(1f))
           Button(
               onClick = {
                         /*TODO*/
                       dialogMessage = "Bạn chắc chắn muốn hủy"
                       showDialog =true
                         },
               colors = ButtonDefaults.buttonColors(
                   backgroundColor = colorResource(id = R.color.darkblue)
               ),
               enabled = isAppointmentValid
           ) {
               Text(
                   text = "Hủy ",
                   fontWeight = FontWeight.Bold,
                   color = Color.White
               )

           }
       }
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Xác nhận hủy") },
            text = { Text(text = dialogMessage) },
            confirmButton = {
                TextButton(onClick = {
                    lichKhamViewModel.updateLichKhamTrangThai(lichKham.idLichKham, "Đã hủy")
                    thongBaoViewModel.addThongBao(
                        idBenhNhan,
                        "Bạn đã hủy thành công một lịch hẹn",
                        "History"
                    )
                    soDu = soDu + 100000
                    nguoiDungViewModel.UpdatSoDu(idBenhNhan, soDu)
                    chatViewModel.sendMessage(
                        title = "Biến động số dư",
                        body = "$hoTen ơi \nBạn đã được hoàn 100.000VNĐ\nSố dư: ${formatNumber(
                            soDu)}VNĐ",
                        remoteToken = fcmToken,
                        isBroadcast = false
                    )
                    thongBaoViewModel.addThongBao(
                        idBenhNhan,
                        "Bạn đã được hoàn 100.000VNĐ, số dư: ${formatNumber(
                            soDu)}VNĐ",
                        "Payment"
                    )

                    showDialog = false

                }) {
                    Text(text = "OK", color = colorResource(id = R.color.darkblue))
                }
            }
        )
    }
}




