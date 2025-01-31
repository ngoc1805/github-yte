package com.example.yte.Home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yte.AppBarView
import com.example.yte.Appointment.AppointmentViewModel
import com.example.yte.IdTaiKhoan
import com.example.yte.Connect.NguoiDungViewModel
import com.example.yte.Login_SignUp.SignUpViewModel
import com.example.yte.News.detailViewModel
import com.example.yte.R
import com.example.yte.hoTen
import com.example.yte.isLogin
import com.example.yte.CCCD
import com.example.yte.Connect.ThongBaoViewModel

import com.example.yte.Sdt
import com.example.yte.fcmToken
import com.example.yte.gioiTinh
import com.example.yte.idBenhNhan
import com.example.yte.ngaySinh
import com.example.yte.queQuan
import com.example.yte.soDu
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import kotlinx.coroutines.tasks.await


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Home(
    navController: NavController,
    detailViewModel: detailViewModel = viewModel(),
    signUpViewModel: SignUpViewModel = viewModel(),
    nguoiDungViewModel: NguoiDungViewModel = viewModel(),
    thongBaoViewModel: ThongBaoViewModel = viewModel(),
    appointmentViewModel: AppointmentViewModel = viewModel(),
    initialTab: Int = 0
){
    var selectedTab by rememberSaveable { mutableStateOf(initialTab) }
    var color by remember { mutableStateOf(R.color.black) }
    var appbarBackgroundColor by remember { mutableStateOf(R.color.white) }
    var titles by remember{ mutableStateOf(" Xin chào") }
    var alignment by remember { mutableStateOf(Alignment.TopStart) }
    var isVisible by rememberSaveable { mutableStateOf(true) }
    var isLoading by remember { mutableStateOf(true) }
    val hasnoitice by thongBaoViewModel.hasNotification.observeAsState()


    LaunchedEffect(idBenhNhan) {
        thongBaoViewModel.kiemTraThongBaoChuaNhan(idBenhNhan)
    }
    LaunchedEffect(IdTaiKhoan) {
        nguoiDungViewModel.getNguoiDUngById(IdTaiKhoan)

        isLoading = false
    }
    idBenhNhan = nguoiDungViewModel.nguoiDung?.idBenhNhan?:""
    hoTen = nguoiDungViewModel.nguoiDung?.hoten ?: ""
    Sdt = nguoiDungViewModel.nguoiDung?.sdt ?: ""
    ngaySinh = nguoiDungViewModel.nguoiDung?.ngaysinh ?: ""
    CCCD = nguoiDungViewModel.nguoiDung?.cccd ?: ""
    queQuan = nguoiDungViewModel.nguoiDung?.quequan ?: ""
    gioiTinh = nguoiDungViewModel.nguoiDung?.gioitinh ?: ""
    soDu = nguoiDungViewModel.nguoiDung?.sodu ?: 0
    LaunchedEffect(idBenhNhan) {
        fcmToken = Firebase.messaging.token.await()
        Log.d("fmctoken","$fcmToken")
        nguoiDungViewModel.UpdateFcmToken(idBenhNhan, fcmToken)
    }
    LaunchedEffect(Unit) {
        FirebaseInstallations.getInstance().id.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val installationId = task.result
                Log.d("Firebase", "Installation ID: $installationId")
            } else {
                Log.e("Firebase", "Không thể lấy Installation ID", task.exception)
            }
        }
    }


    Column(modifier = Modifier.fillMaxSize()) {
        AppBarView(
            title = titles,
            color = color,
            backgroundColor = appbarBackgroundColor,
            alignment = alignment,
            isVisible = isVisible
            )
        if (isLoading) {
            // Nếu đang tải dữ liệu, hiển thị loading
            androidx.compose.material.CircularProgressIndicator(
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier.size(24.dp)
            )
        } else {
            if (selectedTab == 0) {
                Card(modifier = Modifier.weight(1f)) {
                        HomeScreen(navController, detailViewModel = detailViewModel, appointmentViewModel = appointmentViewModel)
                        color = R.color.black
                        appbarBackgroundColor = R.color.white
                        titles = "Xin chào $hoTen"
                        alignment = Alignment.BottomStart
                    LaunchedEffect(idBenhNhan) {
                        thongBaoViewModel.kiemTraThongBaoChuaNhan(idBenhNhan)
                    }
                }
            }
            else if(selectedTab == 1){
                Card(modifier = Modifier.weight(1f)) {
                    if (isLogin) {
                        AppoimentmentScreen(
                            navController = navController,
                            appointmentViewModel = appointmentViewModel
                        )
                        color = R.color.white
                        appbarBackgroundColor = R.color.darkblue
                        titles = "Lịch khám"
                        alignment = Alignment.Center
                        LaunchedEffect(idBenhNhan) {
                            thongBaoViewModel.kiemTraThongBaoChuaNhan(idBenhNhan)
                        }

                    } else {
                        isVisible = false
                        navController.navigate("LoginSignUpScreen")
                    }
                }
            }
            else if (selectedTab == 2){
                Card(modifier = Modifier.weight(1f)) {
                    if (isLogin) {
                        Notice(navController = navController)
                        color = R.color.black
                        appbarBackgroundColor = R.color.white
                        titles = "Thông báo"
                        alignment = Alignment.Center
                        LaunchedEffect(idBenhNhan) {
                            thongBaoViewModel.kiemTraThongBaoChuaNhan(idBenhNhan)
                        }
                        thongBaoViewModel.updateAllDaNhanByIdBenhNhan(idBenhNhan)
                    } else {
                        isVisible = false
                        navController.navigate("LoginSignUpScreen")
                    }
                }
            }
            else if( selectedTab == 3){
               Card(modifier = Modifier.weight(1f)){
                    if (isLogin) {
                        // Đảm bảo rằng PersonalScreen hiển thị đầy đủ
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = Color.White
                        ) {
                            PersonalScreen(navController = navController)
                        }
                        color = R.color.white
                        appbarBackgroundColor = R.color.darkblue
                        titles = "Cá nhân"
                        alignment = Alignment.BottomCenter
                        LaunchedEffect(idBenhNhan) {
                            thongBaoViewModel.kiemTraThongBaoChuaNhan(idBenhNhan)
                        }
                    } else {
                        isVisible = false
                        navController.navigate("LoginSignUpScreen")
                    }
                }

            }
//            else {
//                LazyColumn(modifier = Modifier.weight(1f)) {
//                    item {
//                        if (selectedTab == 3) {
//                            if (isLogin) {
//                                PersonalScreen(navController = navController)
//                                color = R.color.white
//                                appbarBackgroundColor = R.color.darkblue
//                                titles = "Cá nhân"
//                                alignment = Alignment.BottomCenter
//                            } else {
//                                isVisible = false
//                                navController.navigate("LoginSignUpScreen")
//                            }
//                        }
//                    }
//
//                }
//            }
        }
        TabRow(
            modifier = Modifier.fillMaxWidth(),
            selectedTabIndex = selectedTab,
            backgroundColor = Color.White,
            contentColor = Color.Blue,
            indicator = { tabPositions ->
                // Chỉ định thanh chỉ báo cho Tab được chọn
                TabRowDefaults.Indicator(
                    Modifier
                        .tabIndicatorOffset(tabPositions[selectedTab])
                        .height(0.dp) // Không cần chỉ báo hiển thị trên ảnh mẫu
                )
            }
            ) {
            // tab trang chủ
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                modifier = Modifier.weight(1f),
                selectedContentColor = Color.White,
                unselectedContentColor =  Color(0xFF0856A8)
            ){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .background(
                            if (selectedTab == 0) Color(0xFF0856A8) else Color.Transparent,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(horizontal = 20.dp, vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Home Icon",
                        tint = if(selectedTab == 0) Color.White else Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Trang chủ",
                        fontSize = 12.sp,
                        color = if(selectedTab == 0) Color.White else Color.Gray

                    )
                }
            }

            // tab lịch hẹn
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                modifier = Modifier.weight(1f),
                selectedContentColor = Color.White,
                unselectedContentColor =  Color(0xFF0856A8),
                ){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .background(
                            if (selectedTab == 1) Color(0xFF0856A8) else Color.Transparent,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(horizontal = 20.dp, vertical = 4.dp)
                ) {
                    Icon(
                        imageVector =Icons.Default.DateRange ,
                        contentDescription ="DateRange Icon",
                        tint = if(selectedTab == 1) Color.White else Color.Gray,
                        modifier = Modifier.size(24.dp)
                        )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Lịch hẹn",
                        fontSize = 12.sp,
                        color = if(selectedTab == 1) Color.White else Color.Gray
                    )
                }
            }
            // tab thông báo
            Tab(selected = selectedTab == 2,
                onClick = {selectedTab = 2 },
                modifier = Modifier.weight(1f),
                selectedContentColor = Color.White,
                unselectedContentColor =  Color(0xFF0856A8),
                ){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .background(
                            if (selectedTab == 2) Color(0xFF0856A8) else Color.Transparent,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(horizontal = 20.dp, vertical = 4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription ="Notificatios Icon",
                            tint = if(selectedTab == 2) Color.White else Color.Gray,
                            modifier = Modifier.size(24.dp)
                        )
                        if(hasnoitice == true){
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(Color.Red, shape = CircleShape)
                                    .align(Alignment.TopEnd)
                            ) {

                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Thông báo",
                        fontSize = 11.sp,
                        color = if(selectedTab == 2) Color.White else Color.Gray
                    )
                }
            }
            //tab cá nhân
            Tab(
                selected = selectedTab == 3,
                onClick = { selectedTab = 3 },
                modifier = Modifier.weight(1f),
                selectedContentColor = Color.White,
                unselectedContentColor =  Color(0xFF0856A8),
            ){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .background(
                            if (selectedTab == 3) Color(0xFF0856A8) else Color.Transparent,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(horizontal = 20.dp, vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Person ,
                        contentDescription = "Person Icon",
                        tint = if(selectedTab == 3) Color.White else Color.Gray,
                        modifier = Modifier.size(24.dp)
                        )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text ="Cá nhân",
                        fontSize = 12.sp,
                        color = if(selectedTab == 3) Color.White else Color.Gray
                    )
                }
            }
        }
    }
}





