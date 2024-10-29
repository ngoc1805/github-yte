package com.example.yte.HealthIndex

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Tab
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yte.AppBarView
import com.example.yte.R
@Composable
fun HealthIndex() {
    var selectedTab1 by remember { mutableStateOf(0) }
    Column(modifier = Modifier.fillMaxSize()) {
        AppBarView(
            title = "Chỉ số sức khỏe",
            color = R.color.white,
            backgroundColor = R.color.darkblue,
            alignment = Alignment.Center
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.healthindex) ,
                contentDescription = null,
                modifier = Modifier.size(56.dp)
            )

        }

        TabRow(
            modifier = Modifier.fillMaxWidth(),
            selectedTabIndex = selectedTab1,
            backgroundColor = Color.White,
            contentColor = Color(0xFF0856A8),
            divider = {
                      Divider(
                          color = Color.LightGray,
                          thickness = 1.dp
                      )
            },
            indicator = { tabPositions ->
                // Chỉ định thanh chỉ báo cho Tab được chọn
                TabRowDefaults.Indicator(
                    Modifier
                        .tabIndicatorOffset(tabPositions[selectedTab1])
                        .height(3.dp),
                    color =  Color(0xFF0856A8)
                )
            }
        ) {
            // tab BMI&BSA
            Tab(
                selected = selectedTab1 == 0,
                onClick = { selectedTab1 = 0 },
                modifier = Modifier.weight(1f),

            ){
                Text(
                    text = "BMI & BSA",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (selectedTab1 == 0) Color(0xFF0856A8) else Color.Gray,
                    modifier = Modifier.padding(8.dp)

                )
            }
            // tab huyết áp
            Tab(
                selected = selectedTab1 == 1,
                onClick = { selectedTab1 = 1 },
                modifier = Modifier.weight(1f),

                ){
                Text(
                    text = "Huyết áp",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (selectedTab1 == 1) Color(0xFF0856A8) else Color.Gray,
                    modifier = Modifier.padding(8.dp)

                )
            }
            //tab nhiệt độ
            Tab(
                selected = selectedTab1 == 2,
                onClick = { selectedTab1 = 2 },
                modifier = Modifier.weight(1f),

                ){
                Text(
                    text = "Nhiệt độ",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (selectedTab1 == 2) Color(0xFF0856A8) else Color.Gray,
                    modifier = Modifier.padding(8.dp)

                )
            }
        }
        LazyColumn(modifier = Modifier.weight(1f)) {
            item {

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HealthIndexPrivuew(){
    HealthIndex()
}
