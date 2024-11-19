package com.example.yte.Login_SignUp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(/*private val apiService: APITaiKhoan*/) : ViewModel() {

//    private val _accountExistsMessage = MutableStateFlow<String?>(null)
//    val accountExistsMessage: StateFlow<String?> = _accountExistsMessage.asStateFlow()

    var phoneNumber by mutableStateOf("")
        private set
    var passWord by mutableStateOf("")
        private set
    var confirmPassWord by mutableStateOf("")
        private set
    var isSignUpSuccessful by mutableStateOf(false)
        private set

    var numberPhoneErr by mutableStateOf<String?>(null)
        private set
    var passWordErr by mutableStateOf<String?>(null)
        private set
    var confirmPassWordErr by mutableStateOf<String?>(null)
        private set

    fun onPhoneNumberChanged(newPhoneNumber: String){
        phoneNumber = newPhoneNumber
        numberPhoneErr = null

    }
     fun onPassWordChanged(newPassWord: String){
         passWord = newPassWord
         passWordErr = null
     }

    fun onConfirmPassWorkChanged(newConfirmPassWork: String){
        confirmPassWord = newConfirmPassWork
        confirmPassWordErr = null
    }
//    // kiem tra tai khoan ton tai
//    fun checkIfAccountExists(onCompletion: (Boolean) -> Unit) {
//        viewModelScope.launch {
//            try {
//                val response = apiService.checkTk(phoneNumber)
//                if (response.exists) {
//                    _accountExistsMessage.value = "Tên tài khoản đã tồn tại"
//                    onCompletion(false) // Không cho phép đăng ký vì tài khoản đã tồn tại
//                } else {
//                    onCompletion(true) // Tài khoản chưa tồn tại, cho phép đăng ký
//                }
//            } catch (e: Exception) {
//                _accountExistsMessage.value = "Có lỗi xảy ra: ${e.message}"
//                onCompletion(false)
//            }
//        }
//    }
     fun validateAndSignUp(){
         val isPhoneNotEmpty = phoneNumber.isNotEmpty()
         val isPhoneVailiLength = phoneNumber.length == 10
         val isPhoneNumberIc = phoneNumber.all { it.isDigit() }

         val isPassWordNotEmpty = passWord.isNotEmpty()
         val isPassWordValidiLength = passWord.length >=6

         val ispassWordAndConfirmPassWordMatch = passWord == confirmPassWord
         val isConfirmPassWorkNotEmpty = confirmPassWord.isNotEmpty()

         numberPhoneErr = when
         {
             !isPhoneNotEmpty -> "Số điện thoại không được bỏ trống"
             !isPhoneVailiLength -> "Số điện thoại phải đúng 10 ký tự"
             !isPhoneNumberIc -> "Số điện thoại chỉ bao gồm các số"
             else -> null
         }

         passWordErr = when
         {
             !isPassWordNotEmpty -> "Mật khẩu không được bỏ trống"
             !isPassWordValidiLength -> "Mật khẩu tối thiểu 6 ký tự"
             else -> null
         }

         confirmPassWordErr = when
         {
             !isConfirmPassWorkNotEmpty -> "Xác nhận mật khẩu không được bỏ trống"
             !ispassWordAndConfirmPassWordMatch -> "Xác nhận mật khẩu không khớp"
             else -> null
         }
    isSignUpSuccessful =
        isPhoneNotEmpty
                && isPhoneVailiLength
                && isPhoneNumberIc
                && isPassWordNotEmpty
                && isPassWordValidiLength
                && isConfirmPassWorkNotEmpty
                && ispassWordAndConfirmPassWordMatch

//         if (numberPhoneErr == null && passWordErr == null && confirmPassWordErr == null) {
//             checkIfAccountExists { accountDoesNotExist ->
//                 if (accountDoesNotExist) {
//                     isSignUpSuccessful = true
//                 } else {
//                     isSignUpSuccessful = false
//                 }
//             }
//         }

     }
    fun reset() {
        phoneNumber = ""
        passWord = ""
        confirmPassWord = ""
        isSignUpSuccessful = false
        numberPhoneErr = null
        passWordErr = null
        confirmPassWordErr = null
    }





}