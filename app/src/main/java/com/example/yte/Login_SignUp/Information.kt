package com.example.yte.Login_SignUp

import android.app.DatePickerDialog
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.yte.AppBarView
import com.example.yte.R
import com.example.yte.loaitaikhoa
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

@Composable
fun Information(
    navController: NavController,
    inforViewModel: InforViewModel = viewModel(),
    signUpViewModel: SignUpViewModel = viewModel(),
    taiKhoanViewModel: TaiKhoanViewModel = viewModel(),
    nguoiDungViewModel: NguoiDungViewModel = viewModel()

){
    val tenSĐT = signUpViewModel.phoneNumber
    val matKhau = signUpViewModel.passWord
    val loaiTk = loaitaikhoa
    var showDialog by remember { mutableStateOf(false) }



    var scanResult by remember{ mutableStateOf("Chưa có kết quả") }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${month + 1}/$year"
            inforViewModel.onBirthdayChanged(selectedDate) // Sử dụng hàm để cập nhật
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
//    LaunchedEffect(tenSĐT) {
//        taiKhoanViewModel.fetchIdByTenTK(tenSĐT)
//
//    }
//    val idTaiKhoan = taiKhoanViewModel.id_taikhoan

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AppBarView(
            title = "Thông tin cá nhân",
            color = R.color.white  ,
            backgroundColor = R.color.darkblue ,
            alignment = Alignment.Center,
            onDeleteNavClicked = {
                taiKhoanViewModel.deleteTaiKhoa(tenSĐT)
                navController.popBackStack()
                                 },
            isVisible = true

        )

        Spacer(modifier = Modifier.height(24.dp))
        QRCard {
            // Khi bấm vào QRCard, khởi chạy quét mã QR
            val options = GmsBarcodeScannerOptions.Builder()
                .setBarcodeFormats(
                    Barcode.FORMAT_QR_CODE,
                    Barcode.FORMAT_AZTEC,
                    Barcode.FORMAT_ALL_FORMATS
                )
                .build()

            val scanner: GmsBarcodeScanner = GmsBarcodeScanning.getClient(context, options)

            scanner.startScan()
                .addOnSuccessListener { barcode ->
                    // Cập nhật scanResult khi quét thành công
                    scanResult = barcode.rawValue ?: "Không có dữ liệu"
                    val parts = scanResult.split("|")
                    inforViewModel.onCCCDChanged(parts.get(0))
                    inforViewModel.onNameChanged(parts.get(2))
                    inforViewModel.onBirthdayChanged(parts.get(3).substring(0,2)+"/"+parts.get(3).substring(2,4)+"/"+parts.get(3).substring(4))
                    inforViewModel.onGenderChanged(parts.get(4))
                    inforViewModel.onQuequanChanged(parts.get(5))

                }
                .addOnFailureListener { e ->
                    // Hiển thị lỗi nếu có
                    scanResult = "Lỗi: ${e.message}"
                }
        }
        
        // ho ten
        OutlinedTextField(
            value = inforViewModel.name ,
            onValueChange = {inforViewModel.onNameChanged(it)},
            label = { Text(text = "Họ và tên")},
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorResource(id = R.color.darkblue),    // Màu của đường viền khi trường được chọn
                unfocusedBorderColor = Color.Gray,                            // Màu của đường viền khi trường không được chọn
                focusedLabelColor = colorResource(id = R.color.darkblue),     // Màu của nhãn khi trường được chọn
                unfocusedLabelColor = Color.Gray,                             // Màu của nhãn khi trường không được chọn
                cursorColor = Color.Gray                                      // Màu của con trỏ
            ),
            isError = inforViewModel.nameError != null
        )
        //kiem tra loi
        inforViewModel.nameError?.let{
            Text(
                text = it,
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
        //
        Spacer(modifier = Modifier.height(8.dp))
        //ngay sinh & gioi tinh
        Row(verticalAlignment = Alignment.CenterVertically,) {
            OutlinedTextField(
                value = inforViewModel.birthday,
                onValueChange = {},
                label = { Text(text = "Ngày sinh")},
                trailingIcon = {
                               Image(
                                   painter = painterResource(id = R.drawable.baseline_keyboard_arrow_down_24) ,
                                   contentDescription = null,
                                   colorFilter = ColorFilter.tint(Color.Gray)
                               )
                },
                readOnly = true,
                enabled = false,
                modifier = Modifier
                    .weight(1f)
                    .clickable { datePickerDialog.show() }
                    .padding(horizontal = 16.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = colorResource(id = R.color.darkblue),    // Màu của đường viền khi trường được chọn
                    unfocusedBorderColor = Color.Gray,                            // Màu của đường viền khi trường không được chọn
                    focusedLabelColor = colorResource(id = R.color.darkblue),     // Màu của nhãn khi trường được chọn
                    unfocusedLabelColor = Color.Gray,                             // Màu của nhãn khi trường không được chọn
                    cursorColor = Color.Gray                                      // Màu của con trỏ
                ),
                isError = inforViewModel.birthdayError != null
            )


            Row(  verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = inforViewModel.gender == "Nam",
                    onClick = { inforViewModel.onGenderChanged("Nam") },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = colorResource(id = R.color.darkblue),
                        colorResource(id = R.color.darkblue))
                )
                Text(
                    text = "Nam",
                    color = colorResource(id = R.color.darkblue),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 4.dp
                    )
                )

                Spacer(modifier = Modifier.width(16.dp))

                RadioButton(
                    selected = inforViewModel.gender == "Nữ",
                    onClick = { inforViewModel.onGenderChanged("Nữ") },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = colorResource(id = R.color.darkblue),
                        colorResource(id = R.color.darkblue)),
                )
                Text(
                    text = "Nữ",
                    color = colorResource(id = R.color.darkblue),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 4.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
            }


        }
        // kiem tra loi
        inforViewModel.birthdayError?.let{
            Text(
                text = it,
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
        //kiem tra loi
        inforViewModel.genderError?.let {
            Text(
                text = it,
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
        //
        //
        Spacer(modifier = Modifier.height(8.dp))
        // so cccd
        OutlinedTextField(
            value = inforViewModel.CCCD ,
            onValueChange = {inforViewModel.onCCCDChanged(it)},
            label = { Text(text = "Số CCCD")},
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorResource(id = R.color.darkblue),    // Màu của đường viền khi trường được chọn
                unfocusedBorderColor = Color.Gray,                            // Màu của đường viền khi trường không được chọn
                focusedLabelColor = colorResource(id = R.color.darkblue),     // Màu của nhãn khi trường được chọn
                unfocusedLabelColor = Color.Gray,                             // Màu của nhãn khi trường không được chọn
                cursorColor = Color.Gray                                      // Màu của con trỏ
            ),
            isError = inforViewModel.CCCDError != null
        )
        inforViewModel.CCCDError?.let {
            Text(
                text = it,
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        // que quan
        OutlinedTextField(
            value = inforViewModel.quequan ,
            onValueChange = {inforViewModel.onQuequanChanged(it)},
            label = { Text(text = "Quê quán")},
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorResource(id = R.color.darkblue),    // Màu của đường viền khi trường được chọn
                unfocusedBorderColor = Color.Gray,                            // Màu của đường viền khi trường không được chọn
                focusedLabelColor = colorResource(id = R.color.darkblue),     // Màu của nhãn khi trường được chọn
                unfocusedLabelColor = Color.Gray,                             // Màu của nhãn khi trường không được chọn
                cursorColor = Color.Gray                                      // Màu của con trỏ
            ),
            isError = inforViewModel.quequanError != null

        )
        // kiem tra loi
        inforViewModel.quequanError?.let{
            Text(
                text = it,
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
        //
        // nut hoan thanh
        Spacer(modifier = Modifier.height(80.dp))
        Button(
            onClick = {
                try {

                    // Gọi tuần tự
                    taiKhoanViewModel.addTaiKhoan(tenSĐT, matKhau, loaiTk)
//
//                    // Sau khi thêm tài khoản thành công, gọi API để lấy id_taikhoan theo tenSĐT
//                    taiKhoanViewModel.fetchIdByTenTK(tenSĐT)
//
//                    // Bạn có thể theo dõi idTaiKhoan trong UI



                    showDialog = true
                } catch (e: Exception) {
                    Log.e("Button", "Error: ${e.message}")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.darkblue)
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Hoàn thành",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            
        }
        //
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = {
                    Text(text = "Bạn xác nhận đăng ký tài khoản ",)

                },

                confirmButton = {
                    Button(onClick = {
                        CoroutineScope(Dispatchers.IO).launch{
                            nguoiDungViewModel.addNguoiDung(
                                tenSĐT,
                                inforViewModel.name,
                                tenSĐT,
                                inforViewModel.birthday,
                                inforViewModel.CCCD,
                                inforViewModel.quequan,
                                inforViewModel.gender,
                                0

                            )
                        }

                        showDialog = false
                        navController.navigate("LoginSignUpScreen") // Điều hướng sau khi tắt dialog

                    },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = colorResource(id = R.color.darkblue)
                        )
                        ) {
                        Text(text = "Xác nhận", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton = {
                    Button(onClick = {

                        showDialog = false },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = colorResource(id = R.color.darkblue)
                        )
                        ) {
                        Text(text = "Hủy",  color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            )
        }
    }
}
        //



@Composable
fun QRCard(onClick: () -> Unit = {}){
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.qrscan),
                contentDescription = "scan icon",
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Quét CCCD để nhập nhanh thông tin",
                color = Color.Black,
                fontSize = 16.sp
            )

        }


    }

}



@Preview(showBackground = true)
@Composable
fun InforPreview(){

    val navController = rememberNavController()
    Information(navController = navController)
}