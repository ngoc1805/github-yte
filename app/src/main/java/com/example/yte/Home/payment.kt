package com.example.yte.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.yte.AppBarView
import com.example.yte.R
@Composable
fun Payment(navController: NavController){
    var balance by remember { mutableStateOf("0") }
    Column(modifier = Modifier.fillMaxSize()) {
        AppBarView(
            title = "Nạp tiền" ,
            color = R.color.white ,
            backgroundColor = R.color.darkblue ,
            alignment = Alignment.Center,
            onDeleteNavClicked = {navController.popBackStack()}
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 2.dp
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.width(4.dp))
                Box(
                    modifier = Modifier
                        .size(width = 40.dp, height = 40.dp)
                        .background(color = Color(0xFFE0F7FA), shape = RoundedCornerShape(24.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.wallet),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "$balance vnđ",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.cam)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                    contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(id =R.drawable.qrpayment ),
                        contentDescription = null,
                        modifier = Modifier.size(120.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier.height(24.dp)
                ) {

                        Row {
                            Text(text = "Ngân hàng: ", fontWeight = FontWeight.Bold, color = Color.Gray)
                            Text(
                                text = "Ví điện tử Momo",
                                color = Color.Red,
                                fontWeight = FontWeight.Bold
                            )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Box(modifier = Modifier.height(24.dp)) {
                    Row {
                        Text(text = "Số tài khoản: ", fontWeight = FontWeight.Bold, color = Color.Gray)
                        Text(
                            text = "99MM23365M51459530",
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Box(modifier = Modifier.height(24.dp)) {
                    Row {
                        Text(text = "Chủ tài khoản: ", fontWeight = FontWeight.Bold, color = Color.Gray)
                        Text(
                            text = "MOMO_LE CONG BAO NGOC",
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Box(modifier = Modifier.height(24.dp)) {
                    Row {
                        Text(text = "Tối thiểu: ", fontWeight = FontWeight.Bold, color = Color.Gray)
                        Text(
                            text = "10,000vnđ",
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Box(modifier = Modifier
                    .height(24.dp)
                    .background(Color(0xFFededed))
                   ) {
                    Row( ) {
                        Text(text = "Nội dung chuyển: ", fontWeight = FontWeight.Bold,)
                        Text(
                            text = "bn01",
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(40.dp))
                Row {
                    Text(
                        text = " -> ",
                        color = Color.Red,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,

                        )
                    Text(
                        text = " Chú ý: Khi nhập tiền phải nhập đúng nội dung chuyển tiền, nếu không tiền sẽ không về tài khoản",
                        color = colorResource(id = R.color.cam),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,

                        )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    Text(
                        text = " -> ",
                        color = Color.Red,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,

                        )
                    Text(
                        text = " Nếu sau 10p tiền không vào ví, liên hệ 'SĐT: 0329645776' để giải quyết",
                        color = colorResource(id = R.color.cam),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,

                        )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                ) {
                    Column {
                        Text(
                            text = "  Lịch sử nạp tiền:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        LazyColumn(modifier = Modifier.fillMaxWidth()) {

                        }
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun paymentPr(){
    val navController = rememberNavController()
    Payment(navController = navController)
}