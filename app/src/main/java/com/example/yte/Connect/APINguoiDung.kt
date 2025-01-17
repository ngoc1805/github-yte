package com.example.yte.Connect

import android.os.Parcelable
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yte.address
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
import retrofit2.http.PUT
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
    val idBenhNhan: String,
    val hoten: String,
    val sdt: String,
    val ngaysinh: String,
    val cccd: String,
    val quequan:String,
    val gioitinh:String,
    val sodu: Int,
    val idTaiKhoan: Int

): Parcelable

data class updateSoDu(
    val idbenhnhan: String,
    val sodu : Int
)
data class updatePin(
    val idBenhNhan: String,
    val maPin: String
)
data class updateFCMTOKEN(
    val idBenhNhan: String,
    val fcmToken: String
)
data class updateSdt(
    val idBenhNhan: String,
    val sdt: String
)

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
    suspend fun getNguoiDungByIdTk(@Query("idTaiKhoan") idtaikhoan: Int): NguoiDung
    @POST("/post/update/sodu")
    suspend fun UpdateSodu(
        @Body updateSoDu: updateSoDu
    )

    @POST("/post/update/mapin")
    suspend fun UpdatePin(
        @Body updatePin: updatePin
    )
    @POST("/post/update/fcmtoken")
    suspend fun  UpdateFcmToken(
        @Body updateFcmToken: updateFCMTOKEN
    )
    @GET("/get/mapin")
    suspend fun hasMaPin(@Query("idBenhNhan") idBenhNhan: String)

    @GET("/get/checkmapin")
    suspend fun checkMaPin(
        @Query("idBenhNhan") idBenhNhan: String,
        @Query("maPin") maPin: String
    )

    @PUT("/put/sdt")
    suspend fun UpdateSdt(
        @Body updatesdt: updateSdt
    )

}

class NguoiDungViewModel : ViewModel(){

    private val _addUserResult = MutableStateFlow<ApiResponse?>(null)
    val addAccountResult: StateFlow<ApiResponse?> get() = _addUserResult

    var nguoiDung by mutableStateOf<NguoiDung?>(null)

    private val _httpStatus = MutableLiveData<Int>()
    val httpStatus: LiveData<Int> get() = _httpStatus

    private val _httpStatusCMP = MutableLiveData<Int?>()
    val httpStatusCMP: LiveData<Int?> get() = _httpStatusCMP


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
                Log.d("API_RESPONSEMP", "NguoiDung: $nguoiDung")
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
    fun UpdatSoDu(idbenhnhan: String, sodu: Int){
        viewModelScope.launch {
            val updatesodu = updateSoDu(idbenhnhan,sodu)
            recipeServiceNguoiDung.UpdateSodu(updatesodu)
        }
    }
    fun UpdatePin(idBenhNhan: String, maPin: String){
        viewModelScope.launch {
            val updatepin = updatePin(idBenhNhan,maPin)
            recipeServiceNguoiDung.UpdatePin(updatepin)
        }
    }
    fun UpdateSdt(idBenhNhan: String, sdt: String){
        viewModelScope.launch {
            try {
                val UpdateSdt = updateSdt(idBenhNhan,sdt)
                recipeServiceNguoiDung.UpdateSdt(UpdateSdt)

            }catch (e: Exception){
                Log.e("updatesdt", "${e.message}")
            }

        }
    }
    fun UpdateFcmToken(idBenhNhan: String, fcmToken: String) {
        viewModelScope.launch {
            try {
                // Gọi API để cập nhật FCM token cho bệnh nhân
                val updatefcmtoken = updateFCMTOKEN(idBenhNhan, fcmToken)
                recipeServiceNguoiDung.UpdateFcmToken(updatefcmtoken)
                Log.d("FCM", "FCM Token đã được cập nhật thành công")
            } catch (e: Exception) {
                Log.e("FCM", "Lỗi khi cập nhật FCM token: ${e.message}")
            }
        }
    }
    fun hasPin(idBenhNhan: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                recipeServiceNguoiDung.hasMaPin(idBenhNhan)
                _httpStatus.postValue(200)
            } catch (e: retrofit2.HttpException) {
                if (e.code() == 404) {
                    _httpStatus.postValue(404)
                } else {
                    Log.e("API_ERROR", "HTTP Error: ${e.code()} - ${e.message()}")
                    _httpStatus.postValue(e.code())
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "Unknown Error: ${e.message}")
                _httpStatus.postValue(500) // Trạng thái lỗi server 500
            }
        }
    }
    fun checkMaPin(idBenhNhan: String, maPin: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                recipeServiceNguoiDung.checkMaPin(idBenhNhan,maPin)
                _httpStatusCMP.postValue(200)
            } catch (e: retrofit2.HttpException) {
                if (e.code() == 404) {
                    _httpStatusCMP.postValue(404)
                } else {
                    Log.e("API_ERROR", "HTTP Error: ${e.code()} - ${e.message()}")
                    _httpStatusCMP.postValue(e.code())
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "Unknown Error: ${e.message}")
                _httpStatusCMP.postValue(500) // Trạng thái lỗi server 500
            }
        }
    }
    fun resetHttpStatus() {
        _httpStatusCMP.value = null
    }

}