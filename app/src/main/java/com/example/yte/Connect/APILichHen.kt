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
data class LichKham (
    val idLichKham: Int,
    val idBenhNhan : String,
    val idBacSi : String,
    val ngayKham : String,
    val gioKham : String,
    val trangThai : String

    ): Parcelable

private val retrofit = Retrofit.Builder()
    .baseUrl(address)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
val recipeServiceLichKham = retrofit.create(APIlichHen::class.java)

interface APIlichHen{
    @POST("/post/add/lichkham")
    suspend fun addlichKham(
        @Body lichKham: LichKham
    )
    @GET("get/lichhen")
    suspend fun getLichhenByIdbn( @Query("benhNhanId") benhNhanId: String) : List<LichKham>

    @GET("get/all/lichhen")
    suspend fun getallLichhenByIdbn( @Query("benhNhanId") benhNhanId: String) : List<LichKham>
    @PUT("put/update/lichkham")
    suspend fun updateLichKhamTrangThai(
        @Query("lichKhamId") lichKhamId: Int,
        @Body updateRequest: Map<String, String>
    )
    @GET("/get/kiemtra/lichkham")
    suspend fun kiemTraLichKham(
        @Query("idBacSi") idBacSi: String,
        @Query("ngayKham") ngayKham: String,
        @Query("gioKham") gioKham: String
    ): Map<String, Boolean> // API trả về `available` dưới dạng boolean

    @GET("/get/lichhen/idlichkham")
    suspend fun  getLichHenByIdLichKham(
        @Query("idlichkham") idLichKham: Int
    ): LichKham

}

class LichKhamViewModel : ViewModel(){
    var lichKhamList by mutableStateOf<List<LichKham>>(emptyList())
    var isLoading by mutableStateOf(false) // To manage loading state
    var errorMessage by mutableStateOf<String?>(null) // For error handling

    private val _lichKham = MutableLiveData<LichKham?>()
    val lichKham: LiveData<LichKham?> get() = _lichKham

    var isAvailable by mutableStateOf(false)

    fun addlichKham(idLichKham: Int,idBenhNhan: String,idBacSi: String, ngayKham: String, gioKham: String, trangThai: String){
        viewModelScope.launch {
            val lichKham = LichKham(
                idLichKham = idLichKham ?: 0,
                idBenhNhan = idBenhNhan,
                idBacSi = idBacSi,
                ngayKham = ngayKham,
                gioKham = gioKham,
                trangThai = trangThai
            )
            recipeServiceLichKham.addlichKham(lichKham)
        }
    }
    fun getLichKhamByIdbn(idBenhNhan: String) {
        errorMessage = null
        viewModelScope.launch {
            try {
                val lichKham = recipeServiceLichKham.getLichhenByIdbn(idBenhNhan)
                lichKhamList = lichKham
            } catch (e: Exception) {
                isLoading = false
                errorMessage = "Error fetching appointment: ${e.message}"
            }
        }
    }
    fun getallLichKhamByIdbn(idBenhNhan: String) {
        errorMessage = null
        viewModelScope.launch {
            try {
                val lichKham = recipeServiceLichKham.getallLichhenByIdbn(idBenhNhan)
                lichKhamList = lichKham
            } catch (e: Exception) {
                isLoading = false
                errorMessage = "Error fetching appointment: ${e.message}"
            }
        }
    }

    fun updateLichKhamTrangThai(idLichKham: Int, trangThaiMoi: String) {
        viewModelScope.launch {
            try {
                val updateRequest = mapOf("trangThai" to trangThaiMoi)
                recipeServiceLichKham.updateLichKhamTrangThai(idLichKham, updateRequest)

                // Loại bỏ lịch khám đã hủy khỏi danh sách cục bộ
                if (trangThaiMoi != "Đã lên lịch") {
                    lichKhamList = lichKhamList.filter { it.idLichKham != idLichKham }
                }

                errorMessage = null // Xóa lỗi nếu có
            } catch (e: Exception) {
                errorMessage = "Error updating status: ${e.message}"
            }
        }
    }
    fun kiemTraLichKham(idBacSi: String, ngayKham: String, gioKham: String) {
        errorMessage = null
        viewModelScope.launch {
            try {
                val response = recipeServiceLichKham.kiemTraLichKham(idBacSi, ngayKham, gioKham)
                isAvailable = response["available"] ?: false
            } catch (e: Exception) {
                errorMessage = "Error checking availability: ${e.message}"
            }
        }
    }
    fun getLichHenByIdLichKham(idLichKham: Int){
        viewModelScope.launch {
            try {
                val lichkham = recipeServiceLichKham.getLichHenByIdLichKham(idLichKham)
                _lichKham.value = lichkham
            }catch (e: Exception){

            }
        }
    }

}