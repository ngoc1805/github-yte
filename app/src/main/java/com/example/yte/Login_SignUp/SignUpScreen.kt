package com.example.yte.Login_SignUp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.yte.R

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = viewModel(),
    navController: NavController

){
    var passWordVisible by remember{ mutableStateOf(false) }
    var comfirmPassWordVisible by remember{ mutableStateOf(false) }






    Column(modifier = Modifier.fillMaxSize()) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
            elevation = 8.dp
        ) {
            Column(
                modifier = Modifier.padding(4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                // Phone number field
                OutlinedTextField(
                    value = viewModel.phoneNumber,
                    onValueChange = {viewModel.onPhoneNumberChanged(it)},
                    leadingIcon = { Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null)},
                    label = { Text(text = "Nhập số điện thoại")},
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = viewModel.numberPhoneErr != null
                )

                // Display phone number error if exists
                viewModel.numberPhoneErr?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colors.error,
                        modifier = Modifier.padding(start = 4.dp, top = 1.dp)

                    )

                }
                
                // PassWord field
                OutlinedTextField(
                    value =viewModel.passWord, 
                    onValueChange ={viewModel.onPassWordChanged(it)},
                    leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null)},
                    label = { Text(text = "Nhập mật khẩu")},
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = 
                        if(passWordVisible) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    trailingIcon =
                    {
                        val image = if(passWordVisible)
                            painterResource(id = R.drawable.ic_visibility)
                        else
                            painterResource(id = R.drawable.ic_visibility_off)
                        IconButton(onClick = { passWordVisible =! passWordVisible }) {
                            Icon(painter = image, contentDescription = if (passWordVisible) "Ẩn mật khẩu" else "Hiển thị mật khẩu" )
                            
                        }
                    },
                    isError = viewModel.passWordErr != null
                )

                //  Display password error if exists
                viewModel.passWordErr?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colors.error,
                        modifier = Modifier.padding(start = 4.dp, top = 1.dp)
                    )

                }

                // Confirm Password field
                OutlinedTextField(
                    value = viewModel.confirmPassWord ,
                    onValueChange = {viewModel.onConfirmPassWorkChanged(it)},
                    leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null)},
                    label = { Text(text = "Xác nhận mật khẩu")},
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation =
                        if(comfirmPassWordVisible) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    trailingIcon =
                    {
                        val image = if(comfirmPassWordVisible)
                            painterResource(id = R.drawable.ic_visibility)
                        else painterResource(id = R.drawable.ic_visibility_off)
                        IconButton(onClick = { comfirmPassWordVisible =! comfirmPassWordVisible}) {
                            Icon(painter = image, contentDescription = if(comfirmPassWordVisible) "Ẩn" else "Hiển thị")
                            
                        }
                    },
                    isError = viewModel.confirmPassWordErr != null
                )

                //  Display confirm password error if exists
                viewModel.confirmPassWordErr?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colors.error,
                        modifier = Modifier.padding(start = 4.dp, top = 1.dp)
                    )

                }

                // Sign up buton
                Button(
                    onClick = {
                        viewModel.validateAndSignUp()
                         if(viewModel.isSignUpSuccessful == true)navController.navigate("Information")


                              },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF007BFF))
                ) {
                    Text(text = "Đăng ký", color = Color.White)


                }

            }

        }

    }


}