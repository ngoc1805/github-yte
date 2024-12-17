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
data class FileKetQua(
    val idFileKetQua: Int,
    val idLichKham: Int,
    val tenFile: String,
    val ngayTraKetQua: String,
    val idChucNang: String,
    val fileUrl: String
): Parcelable

private val retrofit = Retrofit.Builder()
    .baseUrl(address)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
val recipeServiceFileKetQua = retrofit.create(APIFileKetQua::class.java)
interface APIFileKetQua{
    @GET("/get/files/idlichkham")
    suspend fun getFileKetQuaByIdLichKham(@Query("idlichkham") idLichKham: Int): List<FileKetQua>
}

class FileKetQuaViewModel: ViewModel(){
    var fileKetQuaList by mutableStateOf<List<FileKetQua>>(emptyList())
    fun getFileKetQuaByIdLichKham(idLichKham: Int){
        viewModelScope.launch {
            try {
                val fileKetQua = recipeServiceFileKetQua.getFileKetQuaByIdLichKham(idLichKham)
                fileKetQuaList = fileKetQua
            }catch (e: Exception){
                Log.e("FileKetQuaViewModel", "Error fetching doctor by ID: ${e.message}")
            }
        }
    }
}