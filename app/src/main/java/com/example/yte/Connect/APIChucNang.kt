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
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

@Parcelize
data class ChucNang(
    val idChucNang: String,
    val tenChucNang: String,
    val idTaiKhoan: Int,
    val giaKham: Int
): Parcelable

private val retrofit = Retrofit.Builder()
    .baseUrl(address)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
val recipeServiceChucNang = retrofit.create(APIChucNang::class.java)

interface APIChucNang{
    @GET("/get/chucnang/idchucnang")
    suspend fun getChucNangByIdChucNang(@Query("idchucnang") idChucNang: String): ChucNang
}

class ChucNangViewModel : ViewModel() {
    // Sử dụng Map để lưu trữ dữ liệu cho từng idChucNang
    var chucnangMap by mutableStateOf<Map<String, ChucNang>>(emptyMap())

    private val _chucNang = MutableLiveData<ChucNang?>()
    val chucNang: LiveData<ChucNang?> get() = _chucNang

    fun getChucNangByIdChucNang(idChucNang: String) {
        viewModelScope.launch {
            try {
                // Kiểm tra nếu dữ liệu đã có trong chucnangMap rồi thì không cần gọi API
                if (!chucnangMap.containsKey(idChucNang)) {
                    val chucNang = recipeServiceChucNang.getChucNangByIdChucNang(idChucNang)
                    // Lưu dữ liệu vào chucnangMap với idChucNang là key
                    chucnangMap = chucnangMap + (idChucNang to chucNang)
                    _chucNang.value = chucNang
                }
            } catch (e: Exception) {
                Log.e("ChucNangVỉewModel", "Error fetching doctor by ID: ${e.message}")
            }
        }
    }
}
