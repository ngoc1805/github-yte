package com.example.yte.Login_SignUp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SignUpViewModel : ViewModel() {
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

         isSignUpSuccessful ==
                 isPhoneNotEmpty
                 && isPhoneVailiLength
                 && isPhoneNumberIc
                 && isPassWordNotEmpty
                 && isPassWordValidiLength
                 && isConfirmPassWorkNotEmpty
                 && ispassWordAndConfirmPassWordMatch

     }




}