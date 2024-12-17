package com.example.yte.Connect

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yte.address
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

@Parcelize
data class DangKyBody(
    val tentk: String,
    val matkhau: String,
    val loaitk: String,
    val hoten: String,
    val sdt: String,
    val ngaysinh: String, // Dữ liệu này sẽ có định dạng dd/MM/yyyy
    val cccd: String,
    val quequan: String,
    val gioitinh: String,
): Parcelable

data class ResponseMessage(
    val message: String?, // Dành cho phản hồi thành công
    val error: String?    // Dành cho phản hồi lỗi
)

private val retrofit = Retrofit.Builder()
    .baseUrl(address)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
val recipeServiceDangKy = retrofit.create(APIDangKy::class.java)
interface APIDangKy {
    @POST("/post/add/taikhoan_nguoidung")
    fun dangKyTaiKhoan(@Body body: DangKyBody): Call<ResponseMessage>
}

class DangKyViewModel : ViewModel(){
    fun dangKy(
        tentk: String,
        matkhau: String,
        loaitk: String,
        hoten: String,
        sdt: String,
        ngaysinh: String,
        cccd: String,
        quequan: String,
        gioitinh: String,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val body = DangKyBody(
                    tentk = tentk,
                    matkhau = matkhau,
                    loaitk = loaitk,
                    hoten = hoten,
                    sdt = sdt,
                    ngaysinh = ngaysinh,
                    cccd = cccd,
                    quequan = quequan,
                    gioitinh = gioitinh
                )

                val response = recipeServiceDangKy.dangKyTaiKhoan(body).execute()

                if (response.isSuccessful) {
                    val responseMessage = response.body()
                    if (responseMessage?.message != null) {
                        onSuccess(responseMessage.message)
                    } else {
                        onError("Phản hồi không hợp lệ từ server.")
                    }
                } else {
                    onError("Lỗi từ server: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                onError("Lỗi kết nối: ${e.message}")
            }
        }
    }
}