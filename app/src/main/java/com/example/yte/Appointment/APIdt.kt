package com.example.yte.Appointment

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yte.address
import com.example.yte.ipCuaNgoc
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query



private val retrofit = Retrofit.Builder()
    .baseUrl(address)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
val recipeService = retrofit.create(APIdt::class.java)

interface APIdt {
    @GET("bacsi")
    suspend fun getDoctor(@Query("khoa") clinicName: String): List<Doctor>

}

class DoctorViewModel : ViewModel() {
    var doctorList by mutableStateOf<List<Doctor>>(emptyList())
        private set
    val isLoading = mutableStateOf(false)

    fun fetchDoctors(clinicName: String) {
        Log.d("DoctorViewModel", "fetchDoctors called with clinicName: $clinicName")
        viewModelScope.launch {
            try {
                val response = recipeService.getDoctor(clinicName)
                doctorList = response
            } catch (e: Exception) {
                Log.e("DoctorViewModel", "Error fetching doctors: ${e.message}")
            }
        }
    }
}