package com.example.yte.Connect

import android.os.Parcelable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import com.example.yte.address


@Parcelize
data class TaiKhoan(
    val tenTk: String,
    val matKhau: String,
    val loaiTk: String,
    val idTaiKhoan: Int
): Parcelable
data class ApiResponse(
    val exists: Boolean,
    val message: String
)
data class ApiResponse1(
    val exists: Boolean
)

data class IdResponse(
    val rowsDeleted: Int
)
data class LoGin(
    val tentk: String,
    val matkhau: String
)
data class tenTkResponse(
    val tentk : String
)
// Data class cho request body khi tạo tài khoản
@Parcelize
data class TaiKhoanRequest(
    val tentk: String,
    val matkhau: String,
    val loaitk: String
): Parcelable

private val retrofit = Retrofit.Builder()
    .baseUrl(address)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
val recipeServiceTaiKhoan = retrofit.create(APITaiKhoan::class.java)

interface APITaiKhoan{
    @GET("check/taikhoan")
    suspend fun checkTk(@Query("tenTK") tenTk:String): ApiResponse

    @POST("post/add/taikhoan")
    suspend fun addTaiKhoan(
        @Body taiKhoan: TaiKhoanRequest
    ): ApiResponse

   @POST("/post/id/bytentk")
   suspend fun getId(
       @Body tenTkResponse: tenTkResponse
   ): IdResponse

    @POST("post/delete_account")
    suspend fun deleteTaiKhoa(
        @Body tenTkResponse: tenTkResponse
    )
    @POST("post/login")
    suspend fun login(
        @Body tkmk: LoGin
    ): ApiResponse1
}

class TaiKhoanViewModel : ViewModel(){

    var id_taikhoan by mutableStateOf<IdResponse?>(null)
        private set

    private val _checkResult = MutableStateFlow<ApiResponse?>(null)
    val checkResult: StateFlow<ApiResponse?> get() = _checkResult

    private val _addAccountResult = MutableStateFlow<ApiResponse?>(null)
    val addAccountResult: StateFlow<ApiResponse?> get() = _addAccountResult

    private val _kqlogin = MutableStateFlow<ApiResponse1?>(null)
    val kq: StateFlow<ApiResponse1?> get() = _kqlogin

    private val _id = MutableStateFlow<IdResponse?>(null)
    val id: StateFlow<IdResponse?> get() = _id

    // Hàm gọi API để kiểm tra tài khoản
    fun checkTaiKhoan(tenTk: String) {
        viewModelScope.launch {
            try {
                val response = recipeServiceTaiKhoan.checkTk(tenTk)
                _checkResult.value = response
            } catch (e: Exception) {
                // Xử lý lỗi nếu có
                _checkResult.value = ApiResponse(
                    exists = false,
                    message = "Có lỗi xảy ra: ${e.message}"
                )
            }
        }
    }
    // Hàm gọi API để thêm tài khoản
    fun addTaiKhoan(tentk: String, matkhau: String, loaitk: String) {
        viewModelScope.launch {
            try {
                val taiKhoanRequest = TaiKhoanRequest(tentk, matkhau, loaitk)
                val response = recipeServiceTaiKhoan.addTaiKhoan(taiKhoanRequest)
                _addAccountResult.value = response
            } catch (e: Exception) {
                _addAccountResult.value = ApiResponse(
                    exists = false,
                    message = "Có lỗi xảy ra: ${e.message}"
                )
            }
        }
    }

    // Lấy id_taikhoan theo tên tài khoản
//    fun fetchIdByTenTK(tenTK: String) {
//        viewModelScope.launch {
//            try {
//                // Gọi API để lấy id_taikhoan theo tenTK
//                val response = recipeServiceTaiKhoan.getIdByTenTK(tenTK)
//                id_taikhoan = response.id_taikhoan // Lấy giá trị id_taikhoan từ phản hồi
//            } catch (e: Exception) {
//                // Xử lý lỗi (nếu có)
//                id_taikhoan = null
//            }
//        }
//    }
    fun deleteTaiKhoa(tenTk: String){
        viewModelScope.launch {
            val tenTkResponse = tenTkResponse(tenTk)
            recipeServiceTaiKhoan.deleteTaiKhoa(tenTkResponse)
        }
    }
    fun loGin(tentk: String,matkhau: String){
        viewModelScope.launch {
            val login = LoGin(tentk,matkhau)
            _kqlogin.value = recipeServiceTaiKhoan.login(login)
        }
    }
    fun getIdbyTenTk(tenTk: String){
        viewModelScope.launch {
            val tenTkResponse = tenTkResponse(tenTk)
            _id.value = recipeServiceTaiKhoan.getId(tenTkResponse)
//            val id_taikhoan = recipeServiceTaiKhoan.getId(tenTkResponse)
//            this@TaiKhoanViewModel.id_taikhoan = id_taikhoan
        }
    }
}