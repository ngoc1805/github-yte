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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.yte.AppBarView
import com.example.yte.R
class paymentViewModel : ViewModel() {
    var balance by mutableStateOf("")
        private set
    var money by mutableStateOf("")
        private set
    fun onBalanceChanged( newBalance : String){
        balance = newBalance
    }
    fun onMoneyChanged( newMoney : String){
        money = newMoney
    }
}
@Composable
fun Payment(navController: NavController, viewModel: paymentViewModel = viewModel()){
    var balance by remember { mutableStateOf("") }
    var money by remember { mutableStateOf("") }

    val confirmButton = @Composable {
        Button(
            onClick = { /* TODO: Xử lý sự kiện xác nhận */ },
            modifier = Modifier.padding(start = 8.dp).height(56.dp)
        ) {
            Text(text = "Xác nhận")
        }
    }



    fun onMoneyChange(newMoney: String){
        money = newMoney
    }
    Column(modifier = Modifier.fillMaxSize()) {
        AppBarView(
            title = "Nạp tiền",
            color = R.color.white,
            backgroundColor = R.color.darkblue,
            alignment = Alignment.Center,
            onDeleteNavClicked = { navController.popBackStack() }
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 2.dp
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
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
                    text = "${if (balance.isNotEmpty()) balance.toIntOrNull() ?: 0 else 0}.000vnđ",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.cam)
                )
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
       Text(
           text = "  Đơn vị: 1000vnđ",
           fontSize = 16.sp,
           fontWeight = FontWeight.Bold,
           color = colorResource(id = R.color.darkblue)
       )
        Text(
            text = "  ví dụ bạn muốn nạp 300.000vnd thì nhập số 300",
            color = Color.Gray,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(32.dp))
        Row {
            Text(
                text = "  Số lượng:",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
                )
            OutlinedTextField(
                // Hiển thị balance dưới dạng chuỗi
                value = viewModel.money ,
                onValueChange = { viewModel.onMoneyChanged(it)},
                modifier = Modifier
                    .padding(start = 8.dp)
                    .width(200.dp)
                    .height(56.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            confirmButton()
        }
    }
}
@Preview(showBackground = true)
@Composable
fun paymentPr(){
    val navController = rememberNavController()
    Payment(navController = navController)
}