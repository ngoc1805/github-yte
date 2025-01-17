package com.example.yte.Appointment


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yte.AppBarView
import com.example.yte.Connect.Doctor
import com.example.yte.Connect.KetQuaKham
import com.example.yte.Connect.LichKham
import com.example.yte.Connect.LichKhamViewModel
import com.example.yte.Connect.recipeServiceLichKham

import com.example.yte.R
import com.example.yte.formatNumber
import com.example.yte.idBenhNhan
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AppointmentViewModel : ViewModel(){
    val selectedDate = mutableStateOf<Day?>(null)
    val selectedDoctor = mutableStateOf<Doctor?>(null)
    val isTimeSelected = mutableStateOf(false)
    val isTime = mutableStateOf("")
    val idLichKham = mutableStateOf(0)
    val selectedLichKham = mutableStateOf<LichKham?>(null)
    val ketQuaKham = mutableStateOf<KetQuaKham?>(null)
    val maPin = mutableStateOf("")
    val maPin2 = mutableStateOf("")


}
@Composable
fun Appointment(
    navController: NavController,
    appointmentViewModel: AppointmentViewModel = viewModel(),
    lichKhamViewModel: LichKhamViewModel = viewModel(),
    thanhToanViewModel: ThanhToanViewModel = viewModel()
){

    LaunchedEffect(Unit) {
        appointmentViewModel.isTimeSelected.value = false
        appointmentViewModel.isTime.value = ""
    }

    val selectedDoctor = appointmentViewModel.selectedDoctor.value
    val selectedDate = appointmentViewModel.selectedDate.value
    val isTimeSelected = appointmentViewModel.isTimeSelected.value

    var bacSiId by remember{ mutableStateOf("") }
    var ngayKham by remember{ mutableStateOf("") }
    var gioKham = appointmentViewModel.isTime.value
    var trangThai by remember{ mutableStateOf("Đã lên lịch") }
    val tienCoc = 100000

    Column(modifier = Modifier.fillMaxSize()) {
        AppBarView(
            title = "Lịch hẹn" ,
            color = R.color.black,
            backgroundColor = R.color.white ,
            alignment = Alignment.Center,
            onDeleteNavClicked = { navController.popBackStack()},
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
                        Column( horizontalAlignment = Alignment.End) {
                            selectedDoctor?.let { doctor ->

                                bacSiId = doctor.idBacSi

                                Text(
                                    text = "Bác sĩ: ${doctor.idBacSi} - ${doctor.hoTen}",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Khoa: ${doctor.khoa}",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White

                                )
                            }
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
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier

                    .padding(top = 140.dp) // Đẩy phần giá xuống dưới
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                selectedDoctor?.let { doctor ->

                     Text(
                         text = "Giá khám: ${formatNumber(doctor.giaKham)} VNĐ",
                         color = Color(0xFF1565C0),
                         fontWeight = FontWeight.Bold,
                         fontSize = 16.sp
                         )
                    Text(
                        text = "Đóng trước: ${formatNumber(tienCoc  )} VNĐ",
                        color = Color(0xFF1565C0),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }

            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        selectedDate?.let {
            ngayKham = it.fullDate
            Text(
                text = "Ngày hẹn: ${it.fullDate}",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = colorResource(id = R.color.teal_700),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        AppointmentTimesCard(selectedDate, appointmentViewModel = appointmentViewModel)
        Spacer(modifier = Modifier.height(8.dp))


        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                      /*TODO*/
                thanhToanViewModel.bacSiId.value = bacSiId
                thanhToanViewModel.ngayKham.value = ngayKham
                thanhToanViewModel.gioKham.value = gioKham

                navController.navigate("thanhtoan")

                      },
            modifier = Modifier
                .width(200.dp)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.darkblue),
                contentColor = Color.White
                ),
            shape = RoundedCornerShape(50),
            enabled = appointmentViewModel.isTimeSelected.value

            ) {
            Text(text = "Xác nhận")
        }
//        Text(text = "$idBenhNhan+ $bacSiId + $ngayKham +$gioKham+ $trangThai")



    }
}

@Composable
fun AppointmentTimesCard(
    selectedDate: Day?,
    appointmentViewModel: AppointmentViewModel = viewModel(),
    lichKhamViewModel: LichKhamViewModel = viewModel()
) {
    val times = listOf(
        "7:00", "7:15", "7:30", "7:45", "8:00", "8:15",
        "8:30", "8:45", "9:00", "14:00", "14:15", "14:30",
        "14:45", "15:00","15:15", "15:30", "15:45"
    )
    var selectedTime by remember { mutableStateOf<String?>(null) }
    val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
    val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
    val isToday = selectedDate?.fullDate == currentDate

    // Trạng thái khả dụng của từng khung giờ
    val timeAvailability = remember { mutableStateOf<Map<String, Boolean>>(emptyMap()) }

    // Gọi API kiểm tra giờ khả dụng
    LaunchedEffect(selectedDate) {
        if (selectedDate != null) {
            val availability = mutableMapOf<String, Boolean>()
            times.forEach { time ->
                try {
                    val result = recipeServiceLichKham.kiemTraLichKham(
                        idBacSi = appointmentViewModel.selectedDoctor.value?.idBacSi ?: "",
                        ngayKham = selectedDate.fullDate,
                        gioKham = time
                    )
                    availability[time] = result["available"] ?: false
                } catch (e: Exception) {
                    availability[time] = false // Nếu lỗi, mặc định không khả dụng
                }
            }
            timeAvailability.value = availability
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Chọn giờ khám",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.darkblue)
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(4.dp)
            ) {
                items(times.size) { index ->
                    val time = times[index]
                    val isPast = if (isToday) {
                        val timeAsDate = SimpleDateFormat("H:mm", Locale.getDefault()).parse(time)
                        timeAsDate?.before(SimpleDateFormat("H:mm", Locale.getDefault()).parse(currentTime)) ?: true
                    } else {
                        false
                    }
                    val isUnavailable = timeAvailability.value[time] == false // Không khả dụng
                    val isDisabled = isPast || isUnavailable

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(80.dp, 40.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                color = when {
                                    isDisabled -> Color.Gray
                                    selectedTime == time -> colorResource(id = R.color.teal_700)
                                    else -> Color.LightGray
                                }
                            )
                            .clickable(enabled = !isDisabled) {
                                selectedTime = time
                                appointmentViewModel.isTime.value = time
                                appointmentViewModel.isTimeSelected.value = true // Cập nhật trạng thái
                            }
                    ) {
                        Text(
                            text = time,
                            color = if (isDisabled) Color.White else Color.Black,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}



//@Preview(showBackground = true)
//@Composable
//fun PreviewAppointment() {
//    // Tạo instance của AppointmentViewModel và thiết lập dữ liệu mẫu
//    val appointmentViewModel = AppointmentViewModel().apply {
//        selectedDoctor.value = Doctor(
//            bac_si_id = 1, // Thêm giá trị cho bac_si_id
//            hoten = "PGS.TS Trần Văn A",
//            khoa = "Nội khoa",
//            giakham = 350000 // Sử dụng Int cho giá trị giakham nếu cần thiết
//        )
//        selectedDate.value = Day(
//            name = "Thứ 2",
//            date = "12/11",
//            fullDate = "12/11/2023"
//        )
//    }
//
//    // Gọi hàm Appointment với NavController tạm thời để xem trước
//    Appointment(navController = rememberNavController(), appointmentViewModel = appointmentViewModel)
//}



