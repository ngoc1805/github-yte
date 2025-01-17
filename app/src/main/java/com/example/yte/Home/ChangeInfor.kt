package com.example.yte.Home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.yte.AppBarView
import com.example.yte.CCCD
import com.example.yte.Connect.NguoiDungViewModel
import com.example.yte.R
import com.example.yte.Sdt
import com.example.yte.chuyenDoiNgay
import com.example.yte.gioiTinh
import com.example.yte.hoTen
import com.example.yte.idBenhNhan
import com.example.yte.ngaySinh
import com.example.yte.queQuan


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChangeInFor(
    navController: NavController,
    nguoiDungViewModel: NguoiDungViewModel = viewModel()
){
    Column(modifier = Modifier.fillMaxSize()) {
        var Name by remember { mutableStateOf(hoTen) }
        var Id by remember { mutableStateOf(idBenhNhan) }
        var Birth by remember { mutableStateOf(ngaySinh) }
        var Gender by remember { mutableStateOf(gioiTinh) }
        var sdt by remember { mutableStateOf(Sdt) }
        var cccd by remember{ mutableStateOf(CCCD) }
        var quequan by remember{ mutableStateOf(queQuan) }
        var showDialog by remember { mutableStateOf(false) }
        var dialogMessage by remember { mutableStateOf("") }
        AppBarView(
            title ="Thông tin người dùng" ,
            color = R.color.white ,
            backgroundColor = R.color.darkblue,
            alignment = Alignment.Center,
            onDeleteNavClicked = {navController.popBackStack()},
            isVisible =true

        )
        Spacer(modifier = Modifier.height(8.dp))
        // Họ tên và ID
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = Name,
                onValueChange = { Name = it },
                label = { Text(text = "Họ tên") },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(2f),
                enabled = false
            )

            OutlinedTextField(
                value = Id,
                onValueChange = { },
                label = { Text(text = "ID bệnh nhân") },
                shape = RoundedCornerShape(8.dp),
                enabled = false,
                modifier = Modifier.weight(1f)
            )
        }


        // ngay sinh & gioi tinh
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            OutlinedTextField(
                value = chuyenDoiNgay( Birth) ,
                onValueChange = {Birth = it},
                label = { Text(text = "Ngày sinh")},
                shape = RoundedCornerShape(8.dp),
                enabled = false
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column() {
                RadioButton(
                    selected = Gender == "Nam",
                    onClick = { Gender = "Nam" },
                    enabled = false
                )
                Text(
                    text = "Nam",
                    color = colorResource(id = R.color.darkblue),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 4.dp
                    )
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column() {
                RadioButton(
                    selected = Gender == "Nữ",
                    onClick = { Gender = "Nữ" },
                    enabled = false
                )
                Text(
                    text = "  Nữ",
                    color = colorResource(id = R.color.darkblue),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 4.dp
                    )
                )
            }
        }
        //sdt
        OutlinedTextField(
            value = sdt,
            onValueChange = {sdt = it},
            label = { Text(text = "Số điện thoại") },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
        )
        // cccd
        OutlinedTextField(
            value = cccd,
            onValueChange = {cccd = it},
            label = { Text(text = "Căn cước công dân") },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            enabled = false
        )
        //que quan
        OutlinedTextField(
            value = quequan,
            onValueChange = {quequan = it},
            label = { Text(text = "Căn cước công dân") },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            enabled = false
        )

//        Spacer(modifier = Modifier.height(64.dp))
//        Text(
//            text = "$Name + ${chuyenDoiNgay(Birth)} + $Gender + $sdt + $cccd + $quequan"
//        )

        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { /*TODO*/
                      nguoiDungViewModel.UpdateSdt(Id, sdt)
                        dialogMessage = "Lưu thông tin thành công"
                        showDialog = true
                      },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = androidx.compose.material.ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.darkblue)
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Lưu",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )
            
        }
        Spacer(modifier = Modifier.height(24.dp))
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(text = "Thông báo") },
                text = { Text(text = dialogMessage) },
                confirmButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text(text = "OK")
                    }
                }
            )
        }
    }


}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ChangePr(){
    val navController = rememberNavController()
    ChangeInFor(navController)
}