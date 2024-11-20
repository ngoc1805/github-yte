package com.example.yte.Appointment

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Doctor(
    val bacSiId : Int,
    val hoten : String,
    val idTaiKhoan: Int,
    val khoa : String,
    val giakham : Int
):Parcelable

//data class DoctorResponse(val doctor : List<Doctor>)