package com.example.yte.Home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.example.yte.Api.CreateOrder
import com.example.yte.AppBarView
import com.example.yte.IdTaiKhoan
import com.example.yte.Connect.NguoiDungViewModel
import com.example.yte.DismissKeyboard
import com.example.yte.R
import com.example.yte.formatNumber
import com.example.yte.idBenhNhan
import com.example.yte.soDu
import vn.zalopay.sdk.ZaloPayError
import vn.zalopay.sdk.ZaloPaySDK
import vn.zalopay.sdk.listeners.PayOrderListener

class OrderPayment : ComponentActivity() {
    private var moneyAmount: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Nhận số tiền từ Intent
        moneyAmount = intent.getStringExtra("moneyAmount") ?: ""

        // Thực hiện thanh toán
        thanhToan(moneyAmount)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        ZaloPaySDK.getInstance().onResult(intent)
    }

    private fun thanhToan(moneyAmount: String) {
        val orderApi = CreateOrder()
        try {
            val data = orderApi.createOrder(moneyAmount)
            val code = data.getString("return_code")
            if (code == "1") {
                val token = data.getString("zp_trans_token")
                ZaloPaySDK.getInstance().payOrder(
                    this,
                    token,
                    "demozpdk://app",
                    object : PayOrderListener {
                        override fun onPaymentSucceeded(transactionId: String, zpTransToken: String, appTransId: String) {
                            runOnUiThread {
                                Toast.makeText(this@OrderPayment, "Thanh toán thành công", Toast.LENGTH_SHORT).show()
                                // Trả kết quả về activity trước
                                val resultIntent = Intent()
                                resultIntent.putExtra("paymentResult", "success")
                                resultIntent.putExtra("paymentAmount", moneyAmount)
                                setResult(Activity.RESULT_OK, resultIntent)

                                finish()
                            }
                        }

                        override fun onPaymentCanceled(zpTransToken: String, appTransId: String) {
                            runOnUiThread {
                                Toast.makeText(this@OrderPayment, "Thanh toán bị hủy", Toast.LENGTH_SHORT).show()
                                val resultIntent = Intent()
                                resultIntent.putExtra("paymentResult", "canceled")
                                setResult(Activity.RESULT_CANCELED, resultIntent)
                                finish()
                            }
                        }

                        override fun onPaymentError(error: ZaloPayError, zpTransToken: String, appTransId: String) {
                            runOnUiThread {
                                Toast.makeText(this@OrderPayment, "Lỗi thanh toán", Toast.LENGTH_SHORT).show()
                                val resultIntent = Intent()
                                resultIntent.putExtra("paymentResult", "error")
                                setResult(Activity.RESULT_CANCELED, resultIntent)
                                finish()
                            }
                        }
                    }
                )
            } else {
                Toast.makeText(this, "Không thể tạo đơn hàng", Toast.LENGTH_SHORT).show()
                finish()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}

class PaymentViewModel : ViewModel() {
    var balance = mutableStateOf(soDu)
        private set

    var money = mutableStateOf("")
        private set

    var isLoading = mutableStateOf(false)
        private set

    fun onMoneyChanged(newMoney: String) {
        if (newMoney.all { it.isDigit() } || newMoney.isEmpty()) {
            money.value = newMoney
        }
    }

    fun updateLoadingState(isLoading: Boolean) {
        this.isLoading.value = isLoading
    }

    fun updateBalance(amount: Int) {
        balance.value += amount
    }


}


@Composable
fun Payment(
    navController: NavController,
    viewModel: PaymentViewModel = viewModel(),
    nguoiDungViewModel: NguoiDungViewModel = viewModel()
) {



    val context = LocalContext.current
    val balance by viewModel.balance
    val money by viewModel.money
    val isLoading by viewModel.isLoading

    val httpStatus by nguoiDungViewModel.httpStatus.observeAsState()
    var hasMapin by remember{ mutableStateOf(false) }
    var showPinCodeScreen by remember { mutableStateOf(false) } // Trạng thái để hiển thị PinCodeScreen
    LaunchedEffect(idBenhNhan) {

        nguoiDungViewModel.hasPin(idBenhNhan)
    }
    httpStatus?.let { status ->
        hasMapin = status == 200
    }


    // Sử dụng launcher để nhận kết quả từ OrderPayment activity
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        viewModel.updateLoadingState(false)
        if (result.resultCode == Activity.RESULT_OK) {
            val paymentResult = result.data?.getStringExtra("paymentResult")
            val paymentAmount = result.data?.getStringExtra("paymentAmount")?.toIntOrNull() ?: 0

            if (paymentResult == "success") {
                viewModel.updateBalance(paymentAmount)
                Toast.makeText(context, "Thanh toán thành công", Toast.LENGTH_SHORT).show()
                nguoiDungViewModel.UpdatSoDu(idBenhNhan,balance)
                nguoiDungViewModel.getNguoiDUngById(IdTaiKhoan)
                soDu =  nguoiDungViewModel.nguoiDung?.sodu ?: 0

            }
        } else {
            Toast.makeText(context, "Thanh toán không thành công", Toast.LENGTH_SHORT).show()
        }
    }
    LaunchedEffect(IdTaiKhoan) {
        nguoiDungViewModel.getNguoiDUngById(IdTaiKhoan)
    }
    soDu = nguoiDungViewModel.nguoiDung?.sodu ?: 0

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start
    ) {
        // Thanh tiêu đề
        AppBarView(
            title = "Nạp tiền",
            color = R.color.white,
            backgroundColor = R.color.darkblue,
            alignment = Alignment.Center,
            onDeleteNavClicked = { navController.popBackStack() },
            isVisible = true
        )

        // Hiển thị số dư
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 2.dp
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 24.dp, height = 30.dp)
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
                    text = "${formatNumber(soDu)} vnđ",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.cam)
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Nhập số tiền muốn nạp xuống ô bên dưới",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.darkblue),
            modifier = Modifier.padding(start = 8.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Ô nhập số tiền
        OutlinedTextField(
            value = money,
            onValueChange = { viewModel.onMoneyChanged(it) },
            label = { Text("Số tiền") },
            modifier = Modifier
                .padding(8.dp)
                .width(150.dp)
                .height(56.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )

        // Nút xác nhận
        Button(
            onClick = {
                val activity = context as? Activity
                val view = activity?.currentFocus
                if (view != null) {
                    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                }

                    if (hasMapin == false) {
                        navController.navigate("createPin")
                    } else {
                        showPinCodeScreen = true
                    }
            },
            modifier = Modifier
                .padding(start = 80.dp)
                .height(56.dp),
            enabled = money.isNotEmpty() && !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text(text = "Xác nhận")
            }
        }
        //-------------

        //-------------
    }
    if (showPinCodeScreen) {
        Box(
            modifier = Modifier
                .fillMaxSize() // Full màn hình
                .background(Color.White) // Nền trắng
        ) {
            PinCodeScreen(
                navController = navController,
                onPinEntered = {
                    showPinCodeScreen = false
//                        navController.navigate("Payment")
                    viewModel.updateLoadingState(true)
                    // Khởi động OrderPayment activity
                    val intent = Intent(context, OrderPayment::class.java)
                    intent.putExtra("moneyAmount", money)
                    launcher.launch(intent)

                },
                onClicCloseButtom = {
                    showPinCodeScreen = false
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun paymentP(){
    val navController = rememberNavController()

    Payment(navController = navController )
}
