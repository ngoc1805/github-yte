package com.example.yte.Login_SignUp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yte.AppBarView

import com.example.yte.R

@Composable
fun Information(){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AppBarView(
            title = "Thông tin cá nhân",
            color = R.color.white  ,
            backgroundColor = R.color.darkblue ,
            alignment = Alignment.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        QRCard()
    }
}

@Composable
fun QRCard(onClick: () -> Unit = {}){
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.qrscan),
                contentDescription = "scan icon",
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Quét CCCD để nhập nhanh thông tin",
                color = Color.Black,
                fontSize = 16.sp
            )

        }

    }
}

@Preview(showBackground = true)
@Composable
fun InforPreview(){
    Information()
}