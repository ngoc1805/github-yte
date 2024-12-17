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
import com.example.yte.address
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

@Parcelize
data class KetQuaKham(
    val idKetQua: Int,
    val idLichKham: Int,
    val nhanXet: String,
    val ngayTraKetQua: String,
    val daThanhToan: Boolean
): Parcelable

private val retrofit = Retrofit.Builder()
    .baseUrl(address)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
val recipeServiceKetQuaKham = retrofit.create(APIKetQuaKham::class.java)
interface APIKetQuaKham{
    @GET("/get/ketquakham/idlichkham")
    suspend fun getKetQuaKhamByIdLichKham(@Query("idlichkham") idLichKham: Int): KetQuaKham
    @GET("/get/ketquakham/idbenhnhan")
    suspend fun getKetQuaKhamByIdBenhNhan(@Query("idbenhnhan") idBenhNhan: String): List<KetQuaKham>
    @PUT("/put/update/ketquakham/thanhtoan")
    suspend fun updatTrangThaiThanhToan(@Query("idlichkham")idLichKham: Int)
}

class KetQuaKhamViewModel: ViewModel(){
    // Biến lưu trạng thái HTTP (200 hoặc 404)
    private val _httpStatus = MutableLiveData<Int>()
    val httpStatus: LiveData<Int> get() = _httpStatus

    // Biến lưu thông tin kết quả khám
    private val _ketQuaKham = MutableLiveData<KetQuaKham?>()
    val ketQuaKham: LiveData<KetQuaKham?> get() = _ketQuaKham

    var ketQuaKhamList by mutableStateOf<List<KetQuaKham>>(emptyList())

    fun getKetQuaKhamByIdLichKham(idLichKham: Int) {
        // Khởi chạy coroutine
        viewModelScope.launch {
            try {
                // Gọi API từ coroutine
                val ketQua = recipeServiceKetQuaKham.getKetQuaKhamByIdLichKham(idLichKham)
                _ketQuaKham.value = ketQua
                _httpStatus.value = 200 // Cập nhật trạng thái thành 200 OK
            } catch (e: HttpException) {
                if (e.code() == 404) {
                    // Trường hợp không tìm thấy bản ghi
                    _httpStatus.value = 404
                    _ketQuaKham.value = null
                } else {
                    // Các lỗi khác
                    _httpStatus.value = e.code()
                }
            } catch (e: Exception) {
                // Lỗi không mong muốn khác
                _httpStatus.value = -1
                e.printStackTrace()
            }
        }
    }

    fun getKetQuaKhamByIdBenhNhan(idBenhNhan: String){
        viewModelScope.launch {
            try {
                val ketquakham = recipeServiceKetQuaKham.getKetQuaKhamByIdBenhNhan(idBenhNhan)
                ketQuaKhamList = ketquakham
            }catch (e: Exception){
                Log.e("KetQuaKhamVỉewModel", "Error fetching doctor by ID: ${e.message}")
            }
        }
    }
    fun updateTrangThaiThanhToan(idLichKham: Int){
        viewModelScope.launch {
            try {
                recipeServiceKetQuaKham.updatTrangThaiThanhToan(idLichKham)
            }catch (e: Exception){

            }
        }
    }

}