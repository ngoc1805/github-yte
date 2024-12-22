package com.example.yte.Home


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Lock
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yte.CCCD
import com.example.yte.Connect.NguoiDungViewModel
import com.example.yte.IdTaiKhoan
import com.example.yte.R
import com.example.yte.Sđt
import com.example.yte.gioiTinh
import com.example.yte.hoTen
import com.example.yte.idBenhNhan
import com.example.yte.isLogin
import com.example.yte.ngaySinh
import com.example.yte.queQuan
import com.example.yte.soDu

@Composable
fun PersonalScreen(
    navController: NavController,
    nguoiDungViewModel: NguoiDungViewModel = viewModel()
){
    val httpStatus by nguoiDungViewModel.httpStatus.observeAsState()
    var hasMapin by remember{ mutableStateOf(false) }
    var showPinCodeScreen by remember { mutableStateOf(false) } // Trạng thái để hiển thị PinCodeScreen
    LaunchedEffect(idBenhNhan) {

        nguoiDungViewModel.hasPin(idBenhNhan)
    }
        httpStatus?.let { status ->
        hasMapin = status == 200
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            FirstCard()
            SecondCard(navController= navController)
            Spacer(modifier = Modifier.height(16.dp))
            setCard(
                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_monetization_on_24),
                text = "Nạp tiền",
                onClick = {

//                    if(hasMapin == false){
//                        navController.navigate("createPin")
//                    }
//                    else{
//                        showPinCodeScreen = true
//                    }
//                }
                    navController.navigate("Payment")
                }

            )
            Spacer(modifier = Modifier.height(24.dp))
            setCard(imageVector = Icons.Default.Lock, text = "Đổi mật khẩu",
                onClick = {navController.navigate("ChangePassWord")})
            Spacer(modifier = Modifier.height(2.dp))
            setCard(imageVector = Icons.Default.Delete, text = "Xóa tài khoản")
            Spacer(modifier = Modifier.height(2.dp))
            setCard(
                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_logout_24),
                text = "Đăng xuất"
            ) {
                IdTaiKhoan = 0
                isLogin = false
                idBenhNhan = ""
                hoTen = ""
                Sđt = ""
                ngaySinh =""
                CCCD =""
                queQuan =""
                gioiTinh=""
                soDu =0

                navController.navigate("LoginSignUpScreen")
            }

        }
//        if (showPinCodeScreen) {
//            Box(
//                modifier = Modifier
//                    .fillMaxSize() // Full màn hình
//                    .background(Color.White) // Nền trắng
//            ) {
//                PinCodeScreen(
//                    navController = navController,
//                    onPinEntered = {
//                        showPinCodeScreen = false
//                        navController.navigate("Payment")
//
//                    },
//                    onClicCloseButtom = {
//                        showPinCodeScreen = false
//                    }
//                )
//            }
//        }
    }
}




@Composable
fun FirstCard(){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp, vertical = 0.dp),
        elevation = 4.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Box( modifier = Modifier
                .size(48.dp)
                .background(Color(0xFFE0E0E0), shape = CircleShape),
                contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.drawable.anhtrang),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "$hoTen",
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray
            )

        }
    }
}

@Composable
fun SecondCard(navController: NavController){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp, vertical = 0.dp),
        elevation = 8.dp
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "Tiện ích", fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row {
                //button1
                Box(modifier = Modifier
                    .weight(1f)
                    .clickable { navController.navigate("HealthRecords") }, contentAlignment = Alignment.Center) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth() // Đảm bảo căn đều trong phần tử con của Row
                            .padding(vertical = 8.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.healthrecord) ,
                            contentDescription = null,
                            tint = Color(0xFF0856A8),
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text ="Hồ sơ sức khỏe",
                            fontSize = 14.sp
                        )
                    }
                }
                //button2
                Box(modifier = Modifier
                    .weight(1f)
                    .clickable { navController.navigate("History") },
                    contentAlignment = Alignment.Center) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth() // Đảm bảo căn đều trong phần tử con của Row
                            .padding(vertical = 8.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.lichsudatkham) ,
                            contentDescription = null,
                            tint = Color(0xFF0856A8),
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text ="Lịch sử đặt khám",
                            fontSize = 14.sp
                        )
                    }
                }
                //button3
                Box(modifier = Modifier
                    .weight(1f)
                    .clickable { navController.navigate("HealthIndex") }, contentAlignment = Alignment.Center) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth() // Đảm bảo căn đều trong phần tử con của Row
                            .padding(vertical = 8.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.theodoichiso) ,
                            contentDescription = null,
                            tint = Color(0xFF0856A8),
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text ="Theo dõi chỉ số",
                            fontSize = 14.sp
                        )
                    }
                }

            }
        }
    }
}
@Composable
fun setCard(imageVector: ImageVector,text: String, onClick: () -> Unit = {}){
    Card(
        modifier = Modifier
            .padding(horizontal = 0.dp, vertical = 0.dp)
            .fillMaxWidth()

            .clickable(onClick = onClick),
        elevation = 2.dp,

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp) // Padding bên trong card
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp) // Kích thước Box cho icon
                    .background(
                        color = Color(0xFFE0E0E0), // Màu nền xám nhạt
                        shape = RoundedCornerShape(8.dp) // Bo góc 8.dp cho Box
                    ),
                contentAlignment = Alignment.Center

            ) {
                Icon(
                    imageVector = imageVector,
                    contentDescription = null,
                    tint = Color.DarkGray,
                    modifier = Modifier.size(20.dp)
                    )

            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = text,
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.weight(1f) // Đẩy Text sang bên trái
            )
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_arrow_forward_ios_24), // Thêm icon mũi tên
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(12.dp)
            )
        }

    }
}
@Composable
fun Cards(navController: NavController){

}




