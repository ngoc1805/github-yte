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

    var isSuccessful by mutableStateOf(false)
        private set

    fun onNameChanged(newName: String){
        name = newName
    }
    fun onBirthdayChanged(newBirthday: String){
        birthday = newBirthday
    }
    fun onGenderChanged(newGenrder: String){
        gender = newGenrder
    }
    fun onCCCDChanged(newCCCD: String){
        CCCD = newCCCD
    }
    fun onQuequanChanged(newQuequan: String){
        quequan = newQuequan
    }
    fun onNgayDKCCCDhanged(newNgayDKCCCD: String){
        ngayDKCCCD = newNgayDKCCCD
    }

    fun validateInfor(){
        val isNameNotEmpty = name.isNotEmpty()
        val isBirthdayNotEmpty = birthday.isNotEmpty()
        val isGenderIsNotEmpty = gender.isNotEmpty()
        val isCCCDIsNotEmpty = CCCD.isNotEmpty()
        val isQuequanIsNotEmpty = quequan.isNotEmpty()
        val isNgayDKCCCDisNotEmpty = ngayDKCCCD.isNotEmpty()

        val isCCCDdValidLength = CCCD.length == 12
        val isCCCDic = CCCD.all{ it.isDigit()}

        nameError = when{
            !isNameNotEmpty -> "Tên không được bỏ trống"
            else -> null
        }
        birthdayError = when{
            !isBirthdayNotEmpty -> "Ngày sinh không được bỏ trống"
            else -> null
        }
        genderError = when{
            !isGenderIsNotEmpty -> "Giới tính không được bỏ trống"
            else -> null
        }
        CCCDError = when{
            !isCCCDIsNotEmpty -> "Số CCCD không được bỏ trống"
            !isCCCDdValidLength -> "Số CCCD bao gồm 12 số"
            !isCCCDic -> "Số CCCD chỉ bao gồm các chữ số"
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
                && isGenderIsNotEmpty && isCCCDIsNotEmpty
                && isQuequanIsNotEmpty && isNgayDKCCCDisNotEmpty
                && isCCCDic && isCCCDdValidLength
    }
}