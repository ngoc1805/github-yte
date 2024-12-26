package com.example.yte

import android.Manifest
import android.app.Activity
import android.app.Application
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
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
import com.example.yte.Appointment.ThanhToanViewModel
import com.example.yte.Appointment.thanhtoan
import com.example.yte.ChatBot.ChatPage

import com.example.yte.Home.HealthRecords
import com.example.yte.Home.History
import com.example.yte.Home.Home
import com.example.yte.Home.MedicalExamination
import com.example.yte.Home.Payment
import com.example.yte.Home.PersonalScreen
import com.example.yte.Home.createPin
import com.example.yte.Home.medicalExaminationResults
import com.example.yte.Home.reEnterPin
import com.example.yte.Login_SignUp.Information

import com.example.yte.Login_SignUp.LoginSignUpScreen
import com.example.yte.Login_SignUp.SignUpViewModel
import com.example.yte.News.DisplayNewsLazy
import com.example.yte.News.detailViewModel
import com.example.yte.News.newsDetail
import com.example.yte.News.newsViewModel
import com.example.yte.ui.theme.YTETheme
import com.google.firebase.messaging.FirebaseMessaging
import vn.zalopay.sdk.Environment
import vn.zalopay.sdk.ZaloPaySDK
import android.app.NotificationChannel
import com.example.yte.Login_SignUp.LoginViewModel
//import com.example.yte.Otp.PhoneAuthScreen
//import dagger.hilt.android.AndroidEntryPoint
//import dagger.hilt.android.HiltAndroidApp



class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        // ZaloPay SDK Init
        ZaloPaySDK.init(2553, Environment.SANDBOX)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        requestNotificationPermission()




        enableEdgeToEdge()

        setContent {
            YTETheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                    /*.statusBarsPadding()*/
                ) { innerPadding ->
                    DismissKeyboard {
                        val navController = rememberNavController()
                        val appointmentViewModel: AppointmentViewModel = viewModel()
                        val detailViewModel: detailViewModel = viewModel()
                        val signUpViewModel: SignUpViewModel = viewModel()
                        val loginViewModel: LoginViewModel = viewModel()
                        val thanhToanViewModel: ThanhToanViewModel = viewModel()
                        AppnavHost(
                            navController = navController,
                            appointmentViewModel = appointmentViewModel,
                            detailViewModel = detailViewModel,
                            signUpViewModel = signUpViewModel,
                            loginViewModel=loginViewModel,
                            thanhToanViewModel = thanhToanViewModel,
//                            activity = this
                        )
                    }


                }
            }
        }
    }

    private fun requestNotificationPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val hasPermission = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
            if (!hasPermission) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    0
                )
            }
        }
    }


}
val ipCuaNgoc = "192.168.0.102"
val address = "http://$ipCuaNgoc:8080/"
//"192.168.0.101"
val loaitaikhoa = "benhnhan"
var isLogin by mutableStateOf(false)

var idBenhNhan  by mutableStateOf("")
var hoTen  by mutableStateOf("")
var Sdt  by mutableStateOf("")
var ngaySinh by mutableStateOf("")
var CCCD  by mutableStateOf("")
var queQuan  by mutableStateOf("")
var gioiTinh  by mutableStateOf("")
var soDu  by mutableStateOf(0)
var IdTaiKhoan  by mutableStateOf(0)
var fcmToken by mutableStateOf("")


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppnavHost(
    navController: NavHostController,
    appointmentViewModel: AppointmentViewModel,
    detailViewModel: detailViewModel,
    signUpViewModel: SignUpViewModel,
    thanhToanViewModel: ThanhToanViewModel,
    loginViewModel: LoginViewModel,
//    activity: Activity

    ){
    NavHost(
        navController = navController,
        startDestination = "LoginSignUpScreen"
    ) {
        composable("LoginSignUpScreen") { LoginSignUpScreen(navController,signUpViewModel=signUpViewModel, loginViewModel = loginViewModel)}
        composable("Home/{initialTab}") {backStackEntry ->
            val initialTab = backStackEntry.arguments?.getString("initialTab")?.toIntOrNull() ?: 0
            Home(
            navController,
                appointmentViewModel = appointmentViewModel,
            detailViewModel=detailViewModel,
            signUpViewModel=signUpViewModel,
            initialTab = initialTab
        )}
        composable("Home") {
            Home(
                navController,
                appointmentViewModel = appointmentViewModel,
                detailViewModel = detailViewModel,
                signUpViewModel = signUpViewModel,
                initialTab = 0 // Mặc định là tab đầu tiên
            )
        }
        composable("Information") { Information(navController,signUpViewModel = signUpViewModel)}
        composable("HealthIndex") { HealthIndex(navController)}
        composable("PersonalScreen"){ PersonalScreen(navController)}
        composable("HealthRecords"){ HealthRecords(navController, appointmentViewModel=appointmentViewModel)}
        composable("Payment"){ Payment(navController)}
        composable("Booking"){ Booking(navController) }
        composable("clinicDetail/{clinicName}") { backStackEntry ->
            val clinicName = backStackEntry.arguments?.getString("clinicName") ?: ""
            ClinicDetailScreen(clinicName,navController,appointmentViewModel = appointmentViewModel)
        }
        composable("Appointment"){ Appointment(
            navController,
            appointmentViewModel = appointmentViewModel,
            thanhToanViewModel = thanhToanViewModel
        )}
        composable("newsDetail"){ newsDetail(navController, detailViewModel = detailViewModel)}
        composable("thanhtoan"){ thanhtoan(navController, thanhToanViewModel = thanhToanViewModel)}
        composable("History"){ History(navController = navController)}
        composable("MedicalExamination"){ MedicalExamination(navController=navController, appointmentViewModel = appointmentViewModel)}
        composable("medicalExaminationResults"){ medicalExaminationResults(navController=navController, appointmentViewModel = appointmentViewModel)}
        composable("ChatPage"){ ChatPage(navController)}
        composable("createPin"){ createPin(navController, appointmentViewModel=appointmentViewModel)}
        composable("reEnterPin"){ reEnterPin(navController, appointmentViewModel=appointmentViewModel)}
        composable("ChangePassWord"){ ChangePassWord(navController)}
//        composable("PhoneAuthScreen"){ PhoneAuthScreen(navController = navController, activity = activity, loginViewModel = loginViewModel)}
    }

}





