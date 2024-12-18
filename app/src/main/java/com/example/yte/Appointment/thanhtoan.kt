package com.example.yte.Appointment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Button
import androidx.compose.material.TextButton
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yte.AppBarView
import com.example.yte.Connect.LichKhamViewModel
import com.example.yte.Connect.NguoiDungViewModel
import com.example.yte.Connect.ThongBaoViewModel
import com.example.yte.Home.PinCodeScreen

import com.example.yte.IdTaiKhoan
import com.example.yte.R
import com.example.yte.formatNumber
import com.example.yte.idBenhNhan
import com.example.yte.soDu


class  ThanhToanViewModel : ViewModel(){
    var lichKhamId = mutableStateOf(0)
    var bacSiId = mutableStateOf("")
    var ngayKham = mutableStateOf("")
    var gioKham = mutableStateOf("")
    var trangThai = mutableStateOf("Đã lên lịch")

}
@Composable
fun thanhtoan(
    navController: NavController,
    thanhToanViewModel: ThanhToanViewModel = viewModel(),
    lichKhamViewModel: LichKhamViewModel = viewModel(),
    nguoiDungViewModel: NguoiDungViewModel = viewModel(),
    thongBaoViewModel: ThongBaoViewModel = viewModel()
){
    var isAppoint by remember { mutableStateOf(false) }
    var tienCoc = 100000
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }

    //
    val httpStatus by nguoiDungViewModel.httpStatus.observeAsState()
    var hasMapin by remember{ mutableStateOf(false) }
    var showPinCodeScreen by remember { mutableStateOf(false) } // Trạng thái để hiển thị PinCodeScreen
    LaunchedEffect(idBenhNhan) {

        nguoiDungViewModel.hasPin(idBenhNhan)
    }
    httpStatus?.let { status ->
        hasMapin = status == 200
    }

    //


    LaunchedEffect(IdTaiKhoan) {
        nguoiDungViewModel.getNguoiDUngById(IdTaiKhoan)
    }
    soDu = nguoiDungViewModel.nguoiDung?.sodu ?:0
    Column(modifier = Modifier.fillMaxSize()) {
        AppBarView(
            title = "Thanh toán" ,
            color = R.color.white,
            backgroundColor = R.color.darkblue,
            alignment = Alignment.Center,
            onDeleteNavClicked = {if(isAppoint) navController.navigate("Home") else navController.popBackStack() },
            isVisible = true
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            backgroundColor = Color(0xFF2196F3)

        ) {
            Box(modifier = Modifier.fillMaxSize()) {  // Box to center content
                Text(
                    text = "${formatNumber(tienCoc)} VNĐ",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        // Spacer between card and next text
        Spacer(modifier = Modifier.height(16.dp))

        // Text description
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)) {
            Text(
                text = "!!! Số tiền còn lại sẽ được thanh toán vào hôm sau, nếu không đi khám số tiền cọc sẽ không được hoàn lại + $idBenhNhan + ${thanhToanViewModel.bacSiId.value} + ${thanhToanViewModel.ngayKham.value} + ${thanhToanViewModel.gioKham.value} + ${thanhToanViewModel.trangThai.value}",
                fontSize = 16.sp,

                modifier = Modifier.align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Số tiền: ${formatNumber(tienCoc)} VNĐ ",
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                fontSize = 16.sp
            )
            Button(
                onClick = {
                          /*TODO*/

                    isAppoint = true
                    if(soDu < tienCoc){
                        dialogMessage = "Số dư không đủ, hãy nạp thêm"
                        showDialog = true
                    }else{
                        //
                        if (hasMapin == false) {
                            navController.navigate("createPin")
                        } else {
                            showPinCodeScreen = true
                        }
                        //

//                        soDu = soDu - tienCoc
//                        nguoiDungViewModel.UpdatSoDu(idBenhNhan, soDu)
//                        dialogMessage = "Thanh toán thành công"
//                        showDialog = true
//                        lichKhamViewModel.addlichKham(
//                            thanhToanViewModel.lichKhamId.value,
//                            idBenhNhan,
//                            thanhToanViewModel.bacSiId.value,
//                            thanhToanViewModel.ngayKham.value,
//                            thanhToanViewModel.gioKham.value,
//                            thanhToanViewModel.trangThai.value
//                        )
//                        thongBaoViewModel.addThongBao(
//                            idBenhNhan,
//                            "Bạn đã thanh toán thành công ${formatNumber(tienCoc)}VNĐ",
//                            "Payment"
//                        )
//                        thongBaoViewModel.addThongBao(
//                            idBenhNhan,
//                            "Bạn đã đặt thành công một lịch khám",
//                            "Home/1"
//                        )
                    }

                          },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.darkblue)
                )
            ) {
                Text(
                    text = "Thanh toán",
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { androidx.compose.material.Text(text = "Thông báo") },
                text = { androidx.compose.material.Text(text = dialogMessage) },
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
                        androidx.compose.material.Text("OK")
                    }
                }
            )
        }
        //---------

        //---------

    }
    if (showPinCodeScreen) {
        Box(
            modifier = Modifier
                .fillMaxSize() // Full màn hình
                .background(Color.White) // Nền trắng
        ) {
            PinCodeScreen(
                navController = navController,
                onPinEntered = {
                    showPinCodeScreen = false
                    soDu = soDu - tienCoc
                    nguoiDungViewModel.UpdatSoDu(idBenhNhan, soDu)
                    dialogMessage = "Thanh toán thành công"
                    showDialog = true
                    lichKhamViewModel.addlichKham(
                        thanhToanViewModel.lichKhamId.value,
                        idBenhNhan,
                        thanhToanViewModel.bacSiId.value,
                        thanhToanViewModel.ngayKham.value,
                        thanhToanViewModel.gioKham.value,
                        thanhToanViewModel.trangThai.value
                    )
                    thongBaoViewModel.addThongBao(
                        idBenhNhan,
                        "Bạn đã thanh toán thành công ${formatNumber(tienCoc)}VNĐ",
                        "Payment"
                    )
                    thongBaoViewModel.addThongBao(
                        idBenhNhan,
                        "Bạn đã đặt thành công một lịch khám",
                        "Home/1"
                    )

                },
                onClicCloseButtom = {
                    showPinCodeScreen = false
                }
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun thanhtoanPreview(){
//    thanhtoan()
//}