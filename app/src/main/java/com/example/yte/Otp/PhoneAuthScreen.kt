//package com.example.yte.Otp
//
//import android.app.Activity
//import android.content.Context
//import android.widget.Toast
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.layout.wrapContentHeight
//import androidx.compose.foundation.text.BasicTextField
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.CircularProgressIndicator
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Text
//import androidx.compose.material3.Button
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.Dp
//import androidx.compose.ui.unit.TextUnit
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.compose.ui.window.Dialog
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavController
//import com.example.yte.Login_SignUp.LoginViewModel
//import com.example.yte.Sdt
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//
//const val OTP_VIEW_TYPE_NONE = 0
//const val OTP_VIEW_TYPE_UNDERLINE = 1
//const val OTP_VIEW_TYPE_BORDER = 2
//
//@Composable
//fun PhoneAuthScreen(
//    navController: NavController,
//    activity: Activity,
//    loginViewModel: LoginViewModel = viewModel(),
//    authViewModel: AuthViewModel = hiltViewModel(),
//){
//
//    var mobile by remember { mutableStateOf("") }
//    var otp by remember { mutableStateOf("") }
//    var scope = rememberCoroutineScope()
//    var isDialog by remember { mutableStateOf(false) }
//
//    if(isDialog)
//        CommonDialog()
//
//    Box (
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(horizontal = 20.dp),
//        contentAlignment = Alignment.Center
//    ){
//        Column(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(text = "Số điện thoại")
//            Spacer(modifier = Modifier.height(20.dp))
//            OutlinedTextField(
//                value = loginViewModel.numberPhone,
//                onValueChange = {},
//                label = { Text(text = "+84")},
//                modifier = Modifier.fillMaxWidth(),
//                enabled = false
//            )
//            Spacer(modifier = Modifier.height(20.dp))
//            Button(onClick = {
//                scope.launch(Dispatchers.Main){
//                    authViewModel.createUserWithPhone(
//                        loginViewModel.numberPhone,
//                        activity
//                    ).collect{
//                        when(it){
//                            is ResultState.Success->{
//                                isDialog = false
//                                activity.showMsg(it.data)
//                            }
//                            is ResultState.Failure->{
//                                isDialog = false
//                                activity.showMsg(it.msg.toString())
//                            }
//                            ResultState.Loading -> {
//                                isDialog = true
//                            }
//                        }
//                    }
//                }
//            }) {
//                Text(text = "Gửi")
//            }
//            Spacer(modifier = Modifier.height(20.dp))
//            Text(text = "Nhập Otp")
//            Spacer(modifier = Modifier.height(20.dp))
//            OtpView(
//                otpText = otp
//            ){
//                otp = it
//            }
//            Spacer(modifier = Modifier.height(20.dp))
//            Button(onClick = {
//                scope.launch(Dispatchers.Main){
//                    authViewModel.signInWithCredential(
//                        otp
//                    ).collect{
//                        when(it){
//                            is ResultState.Success->{
//                                isDialog = false
//                                activity.showMsg(it.data)
//                            }
//                            is ResultState.Failure->{
//                                isDialog = false
//                                activity.showMsg(it.msg.toString())
//                            }
//                            ResultState.Loading -> {
//                                isDialog = true
//                            }
//                        }
//                    }
//                }
//            }) {
//                Text(text = "Xác minh")
//            }
//
//        }
//
//    }
//
//}
//
//@Composable
//fun CommonDialog() {
//
//    Dialog(onDismissRequest = { }) {
//        CircularProgressIndicator()
//    }
//
//}
//@Composable
//fun OtpView(
//    modifier: Modifier = Modifier,
//    otpText: String = "",
//    charColor: Color = Color(0XFFE8E8E8),
//    charBackground: Color = Color.Transparent,
//    charSize: TextUnit = 20.sp,
//    containerSize: Dp = charSize.value.dp * 2,
//    otpCount: Int = 6,
//    type: Int = OTP_VIEW_TYPE_BORDER,
//    enabled: Boolean = true,
//    password: Boolean = false,
//    passwordChar: String = "",
//    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
//    onOtpTextChange: (String) -> Unit
//) {
//    BasicTextField(
//        modifier = modifier,
//        value = otpText,
//        onValueChange = {
//            if (it.length <= otpCount) {
//                onOtpTextChange.invoke(it)
//            }
//        },
//        enabled = enabled,
//        keyboardOptions = keyboardOptions,
//        decorationBox = {
//            Row(horizontalArrangement = Arrangement.SpaceAround) {
//                repeat(otpCount) { index ->
//                    Spacer(modifier = Modifier.width(2.dp))
//                    CharView(
//                        index = index,
//                        text = otpText,
//                        charColor = charColor,
//                        charSize = charSize,
//                        containerSize = containerSize,
//                        type = type,
//                        charBackground = charBackground,
//                        password = password,
//                        passwordChar = passwordChar,
//                    )
//                    Spacer(modifier = Modifier.width(2.dp))
//                }
//            }
//        })
//}
//@Composable
//private fun CharView(
//    index: Int,
//    text: String,
//    charColor: Color,
//    charSize: TextUnit,
//    containerSize: Dp,
//    type: Int = OTP_VIEW_TYPE_UNDERLINE,
//    charBackground: Color = Color.Transparent,
//    password: Boolean = false,
//    passwordChar: String = ""
//) {
//    val modifier = if (type == OTP_VIEW_TYPE_BORDER) {
//        Modifier
//            .size(containerSize)
//            .border(
//                width = 1.dp,
//                color = charColor,
//                shape = MaterialTheme.shapes.medium
//            )
//            .padding(bottom = 4.dp)
//            .background(charBackground)
//    } else Modifier
//        .width(containerSize)
//        .background(charBackground)
//
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center,
//    ) {
//        val char = when {
//            index >= text.length -> ""
//            password -> passwordChar
//            else -> text[index].toString()
//        }
//        Text(
//            text = char,
//            color = Color.Black,
//            modifier = modifier.wrapContentHeight(),
//            style = MaterialTheme.typography.body1,
//            fontSize = charSize,
//            textAlign = TextAlign.Center,
//        )
//        if (type == OTP_VIEW_TYPE_UNDERLINE) {
//            Spacer(modifier = Modifier.height(2.dp))
//            Box(
//                modifier = Modifier
//                    .background(charColor)
//                    .height(1.dp)
//                    .width(containerSize)
//            )
//        }
//    }
//}
//fun Context.showMsg(
//    msg:String,
//    duration:Int = Toast.LENGTH_SHORT
//) = Toast.makeText(this,msg,duration).show()
