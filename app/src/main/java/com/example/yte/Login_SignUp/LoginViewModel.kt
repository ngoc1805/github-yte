package com.example.yte.Login_SignUp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


class LoginViewModel : ViewModel() {
    var numberPhone by mutableStateOf("")
        private set

    var passWord by mutableStateOf("")
        private set

    var isLoginSuccessful by mutableStateOf(false)
        private set

    var phoneNumberError by mutableStateOf<String?>(null)
        private set

    var passwordError by mutableStateOf<String?>(null)
        private set

    fun onNumberPhoneChanged(newNumberPhone: String) {
        numberPhone = newNumberPhone
        phoneNumberError = null // Reset error when user types
    }

    fun onPassWordChanged(newPassWord: String) {
        passWord = newPassWord
        passwordError = null // Reset error when user types
    }

    fun validateAndLogin() {
        // Check if phone number is not empty and has 10 characters
        val isPhoneNotEmpty = numberPhone.isNotEmpty()
        val isPhoneValidLength = numberPhone.length == 10
        val isPhoneNumberic = numberPhone.all{ it.isDigit()}

        // Check if password is not empty and has 10 characters
        val isPasswordNotEmpty = passWord.isNotEmpty()
        val isPasswordValidLength = passWord.length >= 6

        // Set error messages based on validation
        phoneNumberError = when {
            !isPhoneNotEmpty -> "Số điện thoại không được bỏ trống"
            !isPhoneValidLength -> "Số điện thoại phải đúng 10 ký tự"
            !isPhoneNumberic -> "số điện thoại chỉ bao gồm các số"
            else -> null
        }

        passwordError = when {
            !isPasswordNotEmpty -> "Mật khẩu không được bỏ trống"
            !isPasswordValidLength -> "Mật khẩu tối thiểu 6 ký tự"
            else -> null
        }

        // Login successful if both phone number and password are valid
        isLoginSuccessful = isPhoneNotEmpty && isPhoneValidLength && isPhoneNumberic && isPasswordNotEmpty && isPasswordValidLength
    }
    fun reset() {
        numberPhone = ""
        passWord = ""
        isLoginSuccessful = false
        phoneNumberError = null
        passwordError = null
    }

}
