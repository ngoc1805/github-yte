package com.example.yte.Connect

import android.os.Parcelable
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
data class KhamChucNang (
    val idKhamChucNang: Int,
    val idLichKham: Int,
    val idChucNang: String,
    val gioKham: String,
    val trangThai: String
): Parcelable
private val retrofit = Retrofit.Builder()
    .baseUrl(address)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
val recipeServiceKhamChucNang = retrofit.create(APIKhamChucNang::class.java)
interface APIKhamChucNang{
    @GET("/get/khamchucnang/idlickham")
    suspend fun getKhamChucNangByIdLichKham(@Query("idlichkham") idLichKham: Int) :List<KhamChucNang>
}

class KhamChucNangViewModel: ViewModel(){
    var khamchucnang by mutableStateOf<KhamChucNang?>(null)
    var khamChucNangList by mutableStateOf<List<KhamChucNang>>(emptyList())
    fun getKhamChucNangByIdLichKham(idLichKham: Int){
        viewModelScope.launch {
            try {
                val khamchucnang = recipeServiceKhamChucNang.getKhamChucNangByIdLichKham(idLichKham)
                khamChucNangList = khamchucnang
            }catch (e: Exception) {
                Log.e("KhamChucNangVỉewModel", "Error fetching doctor by ID: ${e.message}")

            }
        }
    }
}