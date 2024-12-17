package com.example.yte.Home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.Circle
import androidx.compose.material3.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.yte.Appointment.AppointmentViewModel
import com.example.yte.R

@Composable
fun PinCodeScreen(onPinEntered: (String) -> Unit) {
    var pin by remember { mutableStateOf("") } // Trạng thái mã PIN
    var errorMessage by remember { mutableStateOf("") } // Lưu thông báo lỗi nếu nhập sai mã PIN
    val maxPinLength = 6 // Số ký tự tối đa

    // Mã PIN đúng (có thể lấy từ cơ sở dữ liệu, hoặc giá trị lưu trong ứng dụng)
    val correctPin = "123456" // Thay thế với mã PIN thực tế

    Box(modifier = Modifier.fillMaxSize()) {
        // Đặt icon "X" ở góc phải trên cùng
        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .align(Alignment.TopEnd) // Đưa lên góc phải
                .padding(16.dp) // Thêm khoảng cách nếu cần
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Đóng"
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Tiêu đề
            Text(
                text = "Nhập mã PIN",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Hiển thị thông báo lỗi nếu mã PIN sai
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                repeat(maxPinLength) { index ->
                    val isFilled = index < pin.length
                    Circle(isFilled)
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                // Các nút số
                (1..9).forEach { number ->
                    item {
                        NumberButton(
                            number = number.toString(),
                            onClick = {
                                // Reset error message khi người dùng bắt đầu nhập lại
                                if (errorMessage.isNotEmpty()) {
                                    errorMessage = "" // Xóa thông báo lỗi
                                }

                                if (pin.length < maxPinLength) {
                                    pin += number.toString()
                                }
                                if (pin.length == maxPinLength) {
                                    // Kiểm tra mã PIN nhập vào
                                    if (pin == correctPin) {
                                        onPinEntered(pin) // Mã PIN đúng
                                    } else {
                                        // Mã PIN sai, hiển thị thông báo lỗi
                                        errorMessage = "Mã PIN sai, vui lòng thử lại!"
                                        pin = "" // Xóa mã PIN đã nhập để thử lại
                                    }
                                }
                            }
                        )
                    }
                }
                // Nút "0"
                item { Spacer(modifier = Modifier.size(64.dp)) } // Chừa khoảng trống
                item {
                    NumberButton(
                        number = "0",
                        onClick = {
                            // Reset error message khi người dùng bắt đầu nhập lại
                            if (errorMessage.isNotEmpty()) {
                                errorMessage = "" // Xóa thông báo lỗi
                            }

                            if (pin.length < maxPinLength) {
                                pin += "0"
                            }
                            if (pin.length == maxPinLength) {
                                // Kiểm tra mã PIN nhập vào
                                if (pin == correctPin) {
                                    onPinEntered(pin) // Mã PIN đúng
                                } else {
                                    // Mã PIN sai, hiển thị thông báo lỗi
                                    errorMessage = "Mã PIN sai, vui lòng thử lại!"
                                    pin = "" // Xóa mã PIN đã nhập để thử lại
                                }
                            }
                        }
                    )
                }
                // Nút xóa
                item {
                    IconButton(
                        onClick = {
                            if (pin.isNotEmpty()) {
                                pin = pin.dropLast(1)
                            }
                        },
                        modifier = Modifier.size(64.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.delete), // Thay bằng icon xóa
                            contentDescription = "Xóa",
                            tint = Color.Black
                        )
                    }
                }
            }
            TextButton(onClick = { /*TODO*/ }) {
                Text(
                    text = " Quên mã PIN",
                    color = Color.Blue
                )
            }
        }
    }
}
//--------------------------------------------------------------
@Composable
fun createPin(
    navController: NavController,
    appointmentViewModel: AppointmentViewModel = viewModel()
){
    var pin by remember { mutableStateOf("") } // Trạng thái mã PIN
    val maxPinLength = 6 // Số ký tự tối đa

    Box(modifier = Modifier.fillMaxSize()) {
        // Đặt icon "X" ở góc phải trên cùng
        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .align(Alignment.TopEnd) // Đưa lên góc phải
                .padding(16.dp) // Thêm khoảng cách nếu cần
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Đóng"
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Tiêu đề
            Text(
                text = "Tạo mã PIN",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                repeat(maxPinLength) { index ->
                    val isFilled = index < pin.length
                    Circle(isFilled)
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                // Các nút số
                (1..9).forEach { number ->
                    item {
                        NumberButton(
                            number = number.toString(),
                            onClick = {
                                if (pin.length < maxPinLength) {
                                    pin += number.toString()
                                }
                                if (pin.length == maxPinLength) {
                                    //----
                                    appointmentViewModel.maPin.value=pin
                                    navController.navigate("reEnterPin"){
                                        popUpTo("createPin") { inclusive = true }
                                    }
                                }
                            }
                        )
                    }
                }
                // Nút "0"
                item { Spacer(modifier = Modifier.size(64.dp)) } // Chừa khoảng trống
                item {
                    NumberButton(
                        number = "0",
                        onClick = {
                            if (pin.length < maxPinLength) {
                                pin += "0"
                            }
                            if (pin.length == maxPinLength) {
                                //--------
                                appointmentViewModel.maPin.value=pin
                                navController.navigate("reEnterPin"){
                                    popUpTo("createPin") { inclusive = true }
                                }

                            }
                        }
                    )
                }
                // Nút xóa
                item {
                    IconButton(
                        onClick = {
                            if (pin.isNotEmpty()) {
                                pin = pin.dropLast(1)
                            }
                        },
                        modifier = Modifier.size(64.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.delete), // Thay bằng icon xóa
                            contentDescription = "Xóa",
                            tint = Color.Black
                        )
                    }
                }
            }
        }
    }
}
//--------------------------------------------------------------
//--------------------------------------------------------------
@Composable
fun reEnterPin(
    navController: NavController,
    appointmentViewModel: AppointmentViewModel = viewModel()
) {
    var pin by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val maxPinLength = 6 // Số ký tự tối đa
    val originalPin = appointmentViewModel.maPin.value

    Box(modifier = Modifier.fillMaxSize()) {
        // Đặt icon "X" ở góc phải trên cùng
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .align(Alignment.TopEnd) // Đưa lên góc phải
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Đóng"
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Tiêu đề
            Text(
                text = "Nhập lại mã PIN",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                repeat(maxPinLength) { index ->
                    val isFilled = index < pin.length
                    Circle(isFilled)
                }
            }

            // Hiển thị thông báo lỗi nếu có
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    modifier = Modifier.padding(8.dp)
                )
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                // Các nút số
                (1..9).forEach { number ->
                    item {
                        NumberButton(
                            number = number.toString(),
                            onClick = {
                                if (pin.length < maxPinLength) {
                                    pin += number.toString()
                                    errorMessage = "" // Reset thông báo lỗi mỗi khi nhập ký tự mới
                                }
                                if (pin.length == maxPinLength) {
                                    // Kiểm tra xem mã PIN có khớp không
                                    if (pin == originalPin) {
                                        navController.popBackStack() // Chuyển sang màn hình trước đó
                                    } else {
                                        errorMessage = "Mã PIN không khớp. Vui lòng thử lại."
                                        pin = "" // Reset lại mã PIN
                                    }
                                }
                            }
                        )
                    }
                }

                // Nút "0"
                item { Spacer(modifier = Modifier.size(64.dp)) }
                item {
                    NumberButton(
                        number = "0",
                        onClick = {
                            if (pin.length < maxPinLength) {
                                pin += "0"
                                errorMessage = "" // Reset thông báo lỗi mỗi khi nhập ký tự mới
                            }
                            if (pin.length == maxPinLength) {
                                if (pin == originalPin) {
                                    navController.popBackStack() // Chuyển sang màn hình trước đó
                                } else {
                                    errorMessage = "Mã PIN không khớp. Vui lòng thử lại."
                                    pin = "" // Reset lại mã PIN
                                }
                            }
                        }
                    )
                }

                // Nút xóa
                item {
                    IconButton(
                        onClick = {
                            if (pin.isNotEmpty()) {
                                pin = pin.dropLast(1)
                            }
                        },
                        modifier = Modifier.size(64.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.delete),
                            contentDescription = "Xóa",
                            tint = Color.Black
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun Circle(isFilled: Boolean) {
    Box(
        modifier = Modifier
            .size(16.dp)
            .clip(CircleShape)
            .background(if (isFilled) Color.Black else Color.Gray)
    )
}

@Composable
fun NumberButton(number: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(64.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)
    ) {
        Text(
            text = number,
            style = MaterialTheme.typography.h6,
            color = Color.Black
        )
    }
}



@Preview(showBackground = true)
@Composable
fun PinCodeScreenPreview(){
    PinCodeScreen(
        onPinEntered = { pin ->
            // Print PIN for debugging in preview
            println("Pin Entered: ")
        }
    )
}