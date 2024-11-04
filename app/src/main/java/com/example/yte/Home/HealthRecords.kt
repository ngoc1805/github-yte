package com.example.yte.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.yte.AppBarView
import com.example.yte.R
@Composable
fun HealthRecords(navController: NavController){
    Column(modifier = Modifier.fillMaxSize().background(color = Color.White)) {
        AppBarView(
            title = "Hồ sơ sức khỏe" ,
            color = R.color.white,
            backgroundColor = R.color.darkblue,
            alignment = Alignment.Center,
            onDeleteNavClicked = {/*todo*/ navController.popBackStack()}
            )
        Spacer(modifier = Modifier.height(16.dp))
        //
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(color = Color.White),
        ) {
            Image(
                painter = painterResource(id = R.drawable.noresult),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun HealthrecordsPreview(){
//    HealthRecords()
//}