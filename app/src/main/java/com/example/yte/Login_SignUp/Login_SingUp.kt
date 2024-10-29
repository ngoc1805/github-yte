package com.example.yte.Login_SignUp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.IntOffset
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.yte.R
import com.example.yte.keyboardAsState
import kotlin.math.roundToInt

@Composable
fun LoginSignUpScreen(navController: NavController) {
    var isLogin by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableStateOf(0) }


    val isKeyboardVisible by keyboardAsState()

    // State to manage the offset of the card when the keyboard is shown
    var offsetY by remember { mutableStateOf(0f) }
    val density = LocalDensity.current

    var image by remember { mutableStateOf(R.drawable.background1)}
    var arrowdownColor by remember { mutableStateOf(Color.Blue) }

    // Automatically adjust offsetY when the keyboard is visible
    LaunchedEffect(isKeyboardVisible, selectedTab) {
        if (isKeyboardVisible) {
            // Move the card up when the keyboard appears (adjust this value for how far the card moves up)
            offsetY = -200f * density.density
        } else {
            // Reset the card to the original position when the keyboard is hidden
            offsetY = 0f
        }
    }

    // Column to structure the screen layout
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background Image - Takes 3/7 of the screen
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f / 5f) // Adjust to 3/7 of the screen
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            IconButton(
                onClick = { /* todo chuyen huong */
                          navController.navigate("Home")
                          },
                modifier = Modifier.statusBarsPadding()) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    tint = arrowdownColor,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),

                )
            }

        }



        // Foreground Card - Takes 4/7 of the screen
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f / 5f) // Adjust to 4/7 of the screen
                .offset { IntOffset(x = 0, y = offsetY.roundToInt()) }, // Apply offset for keyboard
            contentAlignment = Alignment.TopCenter
        ) {
            // Card for Tabs and Forms
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(), // Card takes full height of this section
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                elevation = 8.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // TabRow for switching between Login and Sign Up
                    TabRow(
                        selectedTabIndex = selectedTab,
                        backgroundColor = Color.White,
                        contentColor = Color.Blue,
                    ) {
                        Tab(
                            selected = selectedTab == 0,
                            onClick = { selectedTab = 0 },
                            text = { Text("Đăng nhập", fontSize = 18.sp) }
                        )
                        Tab(
                            selected = selectedTab == 1,
                            onClick = { selectedTab = 1 },
                            text = { Text("Đăng ký", fontSize = 18.sp) }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Show Login or Sign Up form based on selected tab
                    if (selectedTab == 0) {
                        // Show Login Form
                        image = R.drawable.background1
                        arrowdownColor = Color.Blue
//                        LoginForm(viewModel = viewModel)
                       LoginScreen()
                    } else {
                        // Show Sign Up Form

                        image = R.drawable.background2
                        arrowdownColor = Color.Yellow
                        SignUpScreen(navController = navController)
                    }
                }
            }
        }
    }
}


// Utility function to detect keyboard state

//@Preview(showBackground = true)
//@Composable
////fun LoginSignUpScreenPreview() {
////    LoginSignUpScreen()
////}
