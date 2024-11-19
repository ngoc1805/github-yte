package com.example.yte.Login_SignUp

import android.os.Parcelable
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yte.Appointment.Doctor
import com.example.yte.address
import com.example.yte.ipCuaNgoc
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

@Parcelize
data class NguoiDungRequest(

    val tentk: String,
    val hoten: String,
    val sdt: String,
    val ngaysinh: String,
    val cccd: String,
    val quequan:String,
    val gioitinh:String,
    val sodu: Int

): Parcelable

@Parcelize
data class NguoiDung(
    val nguoiDungId: Int,
    val hoten: String,
    val sdt: String,
    val ngaysinh: String,
    val cccd: String,
    val quequan:String,
    val gioitinh:String,
    val sodu: Int,
    val idtaikhoan: Int

): Parcelable

private val retrofit = Retrofit.Builder()
    .baseUrl(address)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val recipeServiceNguoiDung = retrofit.create(APINguoiDung::class.java)
interface APINguoiDung{
    @POST("/post/add/nguoidung")
    suspend fun addNguoiDung(
        @Body nguoiDungRequest: NguoiDungRequest
    )
    @GET("/get/nguoidung")
    suspend fun getNguoiDungByIdTk(@Query("idtaikhoan") idtaikhoan: Int): NguoiDung
}

class NguoiDungViewModel : ViewModel(){

    private val _addUserResult = MutableStateFlow<ApiResponse?>(null)
    val addAccountResult: StateFlow<ApiResponse?> get() = _addUserResult

    var nguoiDung by mutableStateOf<NguoiDung?>(null)


    fun addNguoiDung(
        tentk: String,
        hoten: String,
        sdt: String,
        ngaysinh: String,
        cccd: String,
        quequan: String,
        gioitinh: String,
        sodu: Int


    ){
        viewModelScope.launch(Dispatchers.IO){
            try {
                // Tạo đối tượng request từ các tham số
                val nguoiDungRequest = NguoiDungRequest(
                    tentk = tentk,
                    hoten = hoten,
                    sdt = sdt,
                    ngaysinh = ngaysinh,
                    cccd = cccd,
                    quequan = quequan,
                    gioitinh = gioitinh,
                    sodu = sodu

                )

                // Gửi yêu cầu tới API
                recipeServiceNguoiDung.addNguoiDung(nguoiDungRequest)

                // Nếu thành công, cập nhật trạng thái với phản hồi
                _addUserResult.value = ApiResponse(
                    exists = true,
                    message = "Thêm người dùng thành công"
                )
            } catch (e: Exception) {
                // Xử lý lỗi nếu có
                _addUserResult.value = ApiResponse(
                    exists = false,
                    message = "Lỗi khi thêm người dùng: ${e.message}"
                )
            }
        }
    }

    fun getNguoiDUngById(idtaikhoan: Int) {
        viewModelScope.launch {
            try {
                val nguoiDung = recipeServiceNguoiDung.getNguoiDungByIdTk(idtaikhoan)
                this@NguoiDungViewModel.nguoiDung = nguoiDung
                // Log dữ liệu để kiểm tra
                Log.d("API_RESPONSE", "NguoiDung: $nguoiDung")
            } catch (e: retrofit2.HttpException) {
                if (e.code() == 404) {
                    Log.e("API_ERROR", "User not found for idtaikhoan: $idtaikhoan")
                } else {
                    Log.e("API_ERROR", "HTTP Error: ${e.code()} - ${e.message()}")
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "Unknown Error: ${e.message}")
            }
        }
    }

}