package com.example.yte.Login_SignUp


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yte.Connect.NguoiDungViewModel
import com.example.yte.Connect.TaiKhoanViewModel
import com.example.yte.DismissKeyboard
import com.example.yte.IdTaiKhoan
import com.example.yte.R
import com.example.yte.isLogin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    taiKhoanViewModel: TaiKhoanViewModel = viewModel(),
    nguoiDungViewModel: NguoiDungViewModel = viewModel(),
    navController: NavController
) {
    var passwordVisible by remember { mutableStateOf(false) } // Quản lý trạng thái hiển thị/ẩn mật khẩu
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }

    DismissKeyboard {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
            // Card for Tabs and Login Form
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(), // Card takes full height of this section
                shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
                elevation = 4.dp
            ) {
                Column(
                    modifier = Modifier
                        .padding(4.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
//

                    // Phone number field
                    OutlinedTextField(
                        value = viewModel.numberPhone,
                        onValueChange = { viewModel.onNumberPhoneChanged(it) },
                        leadingIcon = { Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null)},
                        label = { Text("Số điện thoại") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = colorResource(id = R.color.darkblue),    // Màu của đường viền khi trường được chọn
                            unfocusedBorderColor = Color.Gray,                            // Màu của đường viền khi trường không được chọn
                            focusedLabelColor = colorResource(id = R.color.darkblue),     // Màu của nhãn khi trường được chọn
                            unfocusedLabelColor = Color.Gray,                             // Màu của nhãn khi trường không được chọn
                            cursorColor = Color.Gray                                      // Màu của con trỏ
                        ),
                        isError = viewModel.phoneNumberError != null
                    )
                    // Display phone number error if exists
                    viewModel.phoneNumberError?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colors.error,
                            modifier = Modifier.padding(start = 4.dp, top = 1.dp)
                        )
                    }

                    // Password field with visibility toggle
                    OutlinedTextField(
                        value = viewModel.passWord,
                        onValueChange = { viewModel.onPassWordChanged(it) },
                        leadingIcon = { Icon(imageVector = Icons.Default.Lock , contentDescription = null)},
                        label = { Text("Mật khẩu") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation =
                        if (passwordVisible) VisualTransformation.None
                        else
                            PasswordVisualTransformation(),
                        trailingIcon = {
                            val image = if (passwordVisible)
                                painterResource(id = R.drawable.ic_visibility) // Icon hiện mật khẩu
                            else
                                painterResource(id = R.drawable.ic_visibility_off) // Icon ẩn mật khẩu

                            IconButton(onClick = {
                                passwordVisible = !passwordVisible
                            }) {
                                Icon(painter = image, contentDescription = if (passwordVisible) "Ẩn mật khẩu" else "Hiển thị mật khẩu")
                            }
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = colorResource(id = R.color.darkblue),    // Màu của đường viền khi trường được chọn
                            unfocusedBorderColor = Color.Gray,                            // Màu của đường viền khi trường không được chọn
                            focusedLabelColor = colorResource(id = R.color.darkblue),     // Màu của nhãn khi trường được chọn
                            unfocusedLabelColor = Color.Gray,                             // Màu của nhãn khi trường không được chọn
                            cursorColor = Color.Gray                                      // Màu của con trỏ
                        ),
                        isError = viewModel.passwordError != null
                    )
                    // Display password error if exists
                    viewModel.passwordError?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colors.error,
                            modifier = Modifier.padding(start = 4.dp, top = 1.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Login button
                    Button(
                        onClick = {
                            viewModel.validateAndLogin()
                            if(viewModel.isLoginSuccessful){
                                CoroutineScope(Dispatchers.IO).launch{
                                    taiKhoanViewModel.loGin(viewModel.numberPhone, viewModel.passWord)
                                    taiKhoanViewModel.kq.collect {apiResponse1->
                                        if(apiResponse1?.exists == true){
                                            isLogin = true
                                            CoroutineScope(Dispatchers.IO).launch{
                                                taiKhoanViewModel.getIdbyTenTk(viewModel.numberPhone)
                                                taiKhoanViewModel.id.collect{IdResponse->
                                                    IdTaiKhoan = IdResponse?.rowsDeleted ?:0
                                                    withContext(Dispatchers.Main) {
                                                        dialogMessage = "Đăng nhập thành công "
                                                        navController.navigate("Home")
                                                    }

                                                }
                                            }
                                        }
                                        else if(isLogin == false){
                                            withContext(Dispatchers.Main) {
                                                dialogMessage = "Đăng nhập thất bại"
                                                showDialog = true
                                            }
                                        }
                                    }

                                }
                            }

                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.darkblue))
                    ) {
                        Text("Đăng nhập", color = Color.White, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Forgot password text
                    Text(
                        "Quên mật khẩu?",
                        color = Color(0xFF007BFF),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                }
            }

//        LaunchedEffect(isLogin) {
//            if(isLogin == true){
//                dialogMessage = "Đăng nhập thành công "
//                showDialog = true
//            }
//            else{
//                dialogMessage = "Đăng nhập thất bại"
//                showDialog = true
//            }
//        }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text(text = "Thông báo") },
                    text = { Text(text = dialogMessage) },
                    confirmButton = {
                        TextButton(onClick = {
                            showDialog = false

                        }) {
                            Text(text = "OK", color = colorResource(id = R.color.darkblue))
                        }
                    }
                )
            }
        }
    }
}






//@Preview(showBackground = true)
//@Composable
//fun LoginScreenPreview() {
//    LoginScreen()
//}
