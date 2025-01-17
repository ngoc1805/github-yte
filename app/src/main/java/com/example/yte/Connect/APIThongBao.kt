package com.example.yte.Connect

import android.os.Parcelable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yte.address
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
data class ThongBao(
    val idThongBao: Int,
    val idBenhNhan: String,
    val noiDung: String,
    val daXem: Boolean,
    val daNhan: Boolean,
    val thoiGian: String,
    val  duongDan: String
): Parcelable
@Parcelize
data class ThongBaoBody(
    val idBenhNhan: String,
    val noiDung: String,
    val duongDan: String
): Parcelable

private val retrofit = Retrofit.Builder()
    .baseUrl(address)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
val recipeServiceThongBao = retrofit.create(APIThongBao::class.java)

interface APIThongBao {
    @POST("/post/add/thongbao")
    suspend fun addThongBao(
        @Body thongBaoBody: ThongBaoBody
    )
    @GET("/get/thongbao/idBenhNhan")
    suspend fun getThongBaobyId(@Query("idBenhNhan") idBenhNhan: String): List<ThongBao>

    @PUT("/put/thongbao/daxem")
    suspend fun updateDaXem(
        @Query("idThongBao") idThongBao: Int,
        @Body updateRequest: Map<String, String>
    )
    @PUT("/put/thongbao/danhan")
    suspend fun updateAllDaNhanByIdBenhNhan(@Query("idBenhNhan") idBenhNhan: String): Int
    @GET("/get/thongbao/chuanhan")
    suspend fun kiemTraThongBaoChuaNhan(@Query("idBenhNhan") idBenhNhan: String): Boolean

}

class ThongBaoViewModel : ViewModel(){
    var thongBaoList by mutableStateOf<List<ThongBao>>(emptyList())
    var errorMessage by mutableStateOf<String?>(null)
    var hasUnreceivedNotification by mutableStateOf<Boolean>(false)
    private var _hasNotification = MutableLiveData<Boolean>()
    val hasNotification: LiveData<Boolean> get() = _hasNotification
    fun addThongBao(idBenhNhan: String, noiDung: String, duongDan: String) {
        viewModelScope.launch {
            try {
                val thongBaoBody = ThongBaoBody(
                    idBenhNhan = idBenhNhan,
                    noiDung = noiDung,
                    duongDan = duongDan
                )
                recipeServiceThongBao.addThongBao(thongBaoBody)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun getThongBao(idBenhNhan: String){
        viewModelScope.launch {
            try {
                val thongbao = recipeServiceThongBao.getThongBaobyId(idBenhNhan)
                thongBaoList = thongbao
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun updateDaxem(idThongBao: Int, daXem: Boolean){
        viewModelScope.launch {
            try {
                val updateRequest = mapOf("daXem" to daXem.toString())
                recipeServiceThongBao.updateDaXem(idThongBao,updateRequest)
            }catch (e: Exception){
                errorMessage = "Error updating status: ${e.message}"
            }

        }
    }
    fun updateAllDaNhanByIdBenhNhan(idBenhNhan: String) {
        viewModelScope.launch {
            try {
                val updatedCount = recipeServiceThongBao.updateAllDaNhanByIdBenhNhan(idBenhNhan)
                if (updatedCount > 0) {
                    errorMessage = "$updatedCount thông báo đã được cập nhật thành đã nhận."
                } else {
                    errorMessage = "Không có thông báo nào cần cập nhật cho idBenhNhan: $idBenhNhan."
                }
            } catch (e: Exception) {
                errorMessage = "Error updating all notifications: ${e.message}"
            }
        }
    }
    fun kiemTraThongBaoChuaNhan(idBenhNhan: String) {
        viewModelScope.launch {
            try {
                // Gọi API kiểm tra thông báo chưa nhận từ server
                val result = recipeServiceThongBao.kiemTraThongBaoChuaNhan(idBenhNhan)
                _hasNotification.postValue(result) // Lưu lại kết quả trả về
            } catch (e: Exception) {
                errorMessage = "Error checking unreceived notifications: ${e.message}"
            }
        }
    }

}