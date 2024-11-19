package com.example.yte.News

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class NewsClass (
    val id_tintuc : Int,
    val tieuDe : String,
    val linkAnh : String,
    val noiDung : String

):Parcelable