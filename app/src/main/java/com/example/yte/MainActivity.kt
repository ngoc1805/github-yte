package com.example.yte

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.StrictMode
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.yte.Appointment.Appointment
import com.example.yte.Appointment.AppointmentViewModel
import com.example.yte.Appointment.ClinicDetailScreen
import com.example.yte.HealthIndex.HealthIndex
import com.example.yte.Appointment.Booking

import com.example.yte.Home.HealthRecords
import com.example.yte.Home.Home
import com.example.yte.Home.Payment
import com.example.yte.Home.PersonalScreen
import com.example.yte.Login_SignUp.Information

import com.example.yte.Login_SignUp.LoginSignUpScreen
import com.example.yte.Login_SignUp.SignUpViewModel
import com.example.yte.News.DisplayNewsLazy
import com.example.yte.News.detailViewModel
import com.example.yte.News.newsDetail
import com.example.yte.News.newsViewModel
import com.example.yte.ui.theme.YTETheme
import vn.zalopay.sdk.Environment
import vn.zalopay.sdk.ZaloPaySDK

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        // ZaloPay SDK Init
        ZaloPaySDK.init(2553, Environment.SANDBOX)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        enableEdgeToEdge()
        setContent {
            YTETheme {
                Scaffold( modifier = Modifier.fillMaxSize()
                    /*.statusBarsPadding()*/) { innerPadding ->
                   DismissKeyboard {
                       val navController = rememberNavController()
                       val appointmentViewModel: AppointmentViewModel = viewModel()
                       val detailViewModel: detailViewModel = viewModel()
                       val signUpViewModel: SignUpViewModel = viewModel()
                       AppnavHost(
                           navController = navController,
                           appointmentViewModel = appointmentViewModel,
                           detailViewModel = detailViewModel,
                           signUpViewModel = signUpViewModel

                       )
                   }


                }
            }
        }
    }
}
val ipCuaNgoc = "192.168.0.101"
val address = "http://$ipCuaNgoc:8080/"
// "172.20.10.7"
//"192.168.0.101"
val loaitaikhoa = "benhnhan"
var isLogin by mutableStateOf(false)
var hoTen  by mutableStateOf("")
var IdTaiKhoan  by mutableStateOf(0)
var soSu  by mutableStateOf(0)
@Composable
fun AppnavHost(
    navController: NavHostController,
    appointmentViewModel: AppointmentViewModel,
    detailViewModel: detailViewModel,
    signUpViewModel: SignUpViewModel,

    ){
    NavHost(
        navController = navController,
        startDestination = "LoginSignUpScreen"
    ) {
        composable("LoginSignUpScreen") { LoginSignUpScreen(navController,signUpViewModel=signUpViewModel)}
        composable("Home") { Home(
            navController,
            detailViewModel=detailViewModel,
            signUpViewModel=signUpViewModel
        )}
        composable("Information") { Information(navController,signUpViewModel = signUpViewModel)}
        composable("HealthIndex") { HealthIndex(navController)}
        composable("PersonalScreen"){ PersonalScreen(navController)}
        composable("HealthRecords"){ HealthRecords(navController)}
        composable("Payment"){ Payment(navController)}
        composable("Booking"){ Booking(navController) }
        composable("clinicDetail/{clinicName}") { backStackEntry ->
            val clinicName = backStackEntry.arguments?.getString("clinicName") ?: ""
            ClinicDetailScreen(clinicName,navController,appointmentViewModel = appointmentViewModel)
        }
        composable("Appointment"){ Appointment(navController,appointmentViewModel = appointmentViewModel)}
        composable("newsDetail"){ newsDetail(navController, detailViewModel = detailViewModel)}


    }

}



