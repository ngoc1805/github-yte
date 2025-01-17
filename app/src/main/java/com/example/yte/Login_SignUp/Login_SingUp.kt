package com.example.yte.Login_SignUp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.IntOffset
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yte.R
import com.example.yte.keyboardAsState
import kotlin.math.roundToInt

@Composable
fun LoginSignUpScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = viewModel(),
    signUpViewModel: SignUpViewModel = viewModel()
) {
    var isLogin by remember { mutableStateOf(false) }
    var selectedTab by rememberSaveable { mutableStateOf(0) }


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
                modifier = Modifier.statusBarsPadding().offset(y = 32.dp)) {
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
                        indicator = { tabPositions ->
                            // Chỉ định thanh chỉ báo cho Tab được chọn
                            TabRowDefaults.Indicator(
                                Modifier
                                    .tabIndicatorOffset(tabPositions[selectedTab])
                                    .height(0.dp) // Không cần chỉ báo hiển thị trên ảnh mẫu
                            )
                        }
                    ) {
                        Tab(
                            selected = selectedTab == 0,
                            onClick = { selectedTab = 0 },
                            text = { Text("Đăng nhập",
                                fontSize = 18.sp,
                                color = colorResource(id = R.color.darkblue),
                                fontWeight = FontWeight.Bold
                                ) }
                        )
                        Tab(
                            selected = selectedTab == 1,
                            onClick = { selectedTab = 1 },
                            text = { Text("Đăng ký",
                                fontSize = 18.sp,
                                color = colorResource(id = R.color.darkblue),
                                fontWeight = FontWeight.Bold
                            ) }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Show Login or Sign Up form based on selected tab
                    if (selectedTab == 0) {
                        // Show Login Form
                        image = R.drawable.background1
                        arrowdownColor = colorResource(id = R.color.darkblue)
//                        LoginForm(viewModel = viewModel)
                        LoginScreen(navController = navController)
                        signUpViewModel.reset()
                    } else {
                        // Show Sign Up Form

                        image = R.drawable.background2
                        arrowdownColor = colorResource(id = R.color.cam)
                        SignUpScreen(navController = navController, viewModel = signUpViewModel)
                        loginViewModel.reset()
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
