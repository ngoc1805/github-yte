package com.example.yte.APIs

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Doctor(
    val bac_si_id : Int,
    val hoten : String,
    val khoa : String,
    val giakham : Int
):Parcelable

//data class DoctorResponse(val doctor : List<Doctor>)