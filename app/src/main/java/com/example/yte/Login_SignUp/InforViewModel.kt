package com.example.yte.Login_SignUp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class InforViewModel : ViewModel() {
    var name by mutableStateOf("")
        private set
    var birthday by mutableStateOf("")
        private set
    var gender by mutableStateOf("")
        private set
    var CCCD by mutableStateOf("")
        private set
    var quequan by mutableStateOf("")
        private set
    var ngayDKCCCD by mutableStateOf("")
        private set

    var isSuccessful by mutableStateOf(false)
        private set
    var nameError by mutableStateOf<String?>(null)
        private set
    var birthdayError by mutableStateOf<String?>(null)
        private set
    var genderError by mutableStateOf<String?>(null)
        private set
    var CCCDError by mutableStateOf<String?>(null)
        private set
    var quequanError by mutableStateOf<String?>(null)
        private set
    var ngayDKCCCDError by mutableStateOf<String?>(null)
        private set



    fun onNameChanged(newName: String){
        name = newName
        nameError = null
    }
    fun onBirthdayChanged(newBirthday: String){
        birthday = newBirthday
        birthdayError = null
    }
    fun onGenderChanged(newGenrder: String){
        gender = newGenrder
        genderError = null
    }
    fun onCCCDChanged(newCCCD: String){
        CCCD = newCCCD
        CCCDError = null
    }
    fun onQuequanChanged(newQuequan: String){
        quequan = newQuequan
        quequanError = null
    }
    fun onNgayDKCCCDhanged(newNgayDKCCCD: String){
        ngayDKCCCD = newNgayDKCCCD
        ngayDKCCCDError = null
    }

    fun validateInfor(){
        val isNameNotEmpty = name.isNotEmpty()
        val isBirthdayNotEmpty = birthday.isNotEmpty()
        val isGenderNotEmpty = gender.isNotEmpty()
        val isCCCDIsNotEmpty = CCCD.isNotEmpty()
        val isQuequanIsNotEmpty = quequan.isNotEmpty()
        val isNgayDKCCCDisNotEmpty = ngayDKCCCD.isNotEmpty()

        val isCCCDdValidLength = CCCD.length == 12
        val isCCCDic = CCCD.all{ it.isDigit()}

        nameError = when{
            !isNameNotEmpty -> "Tên không được bỏ trống"
            else -> null
        }
        birthdayError = when {
            !isBirthdayNotEmpty && !isGenderNotEmpty -> "Ngày sinh không được để trống, Giới tính không được để trống"
            !isBirthdayNotEmpty -> "Ngày sinh không được để trống"
            else -> null
        }

        genderError = when {
            !isBirthdayNotEmpty && !isGenderNotEmpty -> null // Đã hiển thị lỗi chung ở birthdayError
            !isGenderNotEmpty -> "Giới tính không được để trống"
            else -> null
        }
        CCCDError = when{
            !isCCCDIsNotEmpty -> "Số CCCD không được bỏ trống"
            !isCCCDic -> "Số CCCD chỉ bao gồm các chữ số"
            !isCCCDdValidLength -> "Số CCCD bao gồm 12 số"

            else -> null
        }
        quequanError = when{
            !isQuequanIsNotEmpty -> "Quê quán không được bỏ trống"
            else -> null
        }
        ngayDKCCCDError = when{
            !isNgayDKCCCDisNotEmpty -> "Ngày đăng ký không được bỏ trống"
            else -> null
        }

        isSuccessful = isNameNotEmpty && isBirthdayNotEmpty
                && isGenderNotEmpty && isCCCDIsNotEmpty
                && isQuequanIsNotEmpty && isNgayDKCCCDisNotEmpty
                && isCCCDic && isCCCDdValidLength
    }
    fun reset() {
        name = ""
        birthday = ""
        gender = ""
        CCCD = ""
        quequan = ""
        ngayDKCCCD = ""
        isSuccessful = false
        nameError = null
        birthdayError = null
        genderError = null
        CCCDError = null
        quequanError = null
        ngayDKCCCDError = null
    }

}