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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yte.DismissKeyboard
import com.example.yte.R


@Composable
fun LoginScreen(viewModel: LoginViewModel = viewModel()) {
    var passwordVisible by remember { mutableStateOf(false) } // Quản lý trạng thái hiển thị/ẩn mật khẩu
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
                        onClick = { viewModel.validateAndLogin() },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF007BFF))
                    ) {
                        Text("Đăng nhập", color = Color.White)
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

    }
}
}






@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}
