package com.example.yte

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.yte.HealthIndex.HealthIndex
import com.example.yte.Home.Home
import com.example.yte.Login_SignUp.Information

import com.example.yte.Login_SignUp.LoginSignUpScreen
import com.example.yte.ui.theme.YTETheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        enableEdgeToEdge()
        setContent {
            YTETheme {
                Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
                   DismissKeyboard {
                       val navController = rememberNavController()
                       AppnavHost(navController = navController)
                   }


                }
            }
        }
    }
}

@Composable
fun AppnavHost(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = "LoginSignUpScreen"
    ) {
        composable("LoginSignUpScreen") { LoginSignUpScreen(navController)}
        composable("Home") { Home(navController)}
        composable("Information") { Information()}
        composable("HealthIndex") { HealthIndex()}

    }

}



