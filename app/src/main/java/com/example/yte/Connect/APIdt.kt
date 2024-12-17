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
data class Doctor(
    val idBacSi : String,
    val hoTen : String,
    val idTaiKhoan: Int,
    val khoa : String,
    val giaKham : Int
): Parcelable

private val retrofit = Retrofit.Builder()
    .baseUrl(address)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
val recipeServiceDoctor = retrofit.create(APIdt::class.java)

interface APIdt {
    @GET("/bacsi")
    suspend fun getDoctor(@Query("khoa") clinicName: String): List<Doctor>

    @GET("/get/bacsi/id")
    suspend fun getDoctorbyId(@Query("id") bacSiId: String) : Doctor

}

class DoctorViewModel : ViewModel() {
    var doctorList by mutableStateOf<List<Doctor>>(emptyList())
        private set
    val isLoading = mutableStateOf(false)

    var doctor by mutableStateOf<Doctor?>(null)
    private val doctorCache = mutableMapOf<String, Doctor?>()

    fun fetchDoctors(clinicName: String) {
        Log.d("DoctorViewModel", "fetchDoctors called with clinicName: $clinicName")
        viewModelScope.launch {
            try {
                val response = recipeServiceDoctor.getDoctor(clinicName)
                doctorList = response
            } catch (e: Exception) {
                Log.e("DoctorViewModel", "Error fetching doctors: ${e.message}")
            }
        }
    }
    fun getDoctorById(bacSiId: String) {
        viewModelScope.launch {
            try {
                val doctor = recipeServiceDoctor.getDoctorbyId(bacSiId)
                this@DoctorViewModel.doctor = doctor

            } catch (e: Exception) {
                Log.e("DoctorViewModel", "Error fetching doctor by ID: ${e.message}")

            }
        }
    }
    suspend fun getDoctorByIdCached(bacSiId: String): Doctor? {
        if (doctorCache.containsKey(bacSiId)) {
            return doctorCache[bacSiId]
        }
        return try {
            val doctor = recipeServiceDoctor.getDoctorbyId(bacSiId)
            doctorCache[bacSiId] = doctor
            doctor
        } catch (e: Exception) {
            Log.e("DoctorViewModel", "Error fetching doctor by ID: ${e.message}")
            doctorCache[bacSiId] = null
            null
        }
    }

}