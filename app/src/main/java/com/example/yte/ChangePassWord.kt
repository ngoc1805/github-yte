package com.example.yte

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.OutlinedTextField
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yte.Connect.TaiKhoanViewModel
import com.example.yte.R

class ChangePassWordViewModel : ViewModel(){
    var currentPassWord by mutableStateOf("")
        private set
    var newPassWord by mutableStateOf("")
        private set
    var confirmPassWord by mutableStateOf("")
        private set
    var currentPassWordErr by mutableStateOf<String?>(null)
        private set
    var newPassWordErr by mutableStateOf<String?>(null)
        private set
    var confirmPassWordErr by mutableStateOf<String?>(null)
        private set
    var isChangePassWord by mutableStateOf(false)
        private set

    fun onCurrentPassWordChanged(newCurrentPassWord: String){
        currentPassWord = newCurrentPassWord
        currentPassWordErr = null
    }
    fun onNewPasWordChanged(newNewPassWord: String){
        newPassWord = newNewPassWord
        newPassWordErr = null
    }
    fun onConfirmPassWorkChanged(newConfirmPassWork: String){
        confirmPassWord = newConfirmPassWork
        confirmPassWordErr = null
    }

    fun validateChangePassWord(){
        val isCurrentPassWordNotEmpty = currentPassWord.isNotEmpty()
        val isNewPassWordNotEmpty =  newPassWord.isNotEmpty()
        val isNewPassWordLength = newPassWord.length >=6
        val isNewPassWordAndConfirmPassWordMatch = newPassWord == confirmPassWord
        val isConfirmPassWorkNotEmpty = confirmPassWord.isNotEmpty()

        currentPassWordErr = when{
            !isCurrentPassWordNotEmpty -> "Mật khẩu hiện tại không được bỏ trống"
            else -> null
        }
        newPassWordErr = when{
            !isNewPassWordNotEmpty -> "Mật khẩu mới không được bỏ trống"
            !isNewPassWordLength -> "Mật khẩu tối thiểu 6 ký tự"
            else -> null
        }
        confirmPassWordErr = when{
            !isConfirmPassWorkNotEmpty -> "Xác nhận mật khẩu không được bỏ trống"
            !isNewPassWordAndConfirmPassWordMatch -> "Xác nhận mật khẩu không khớp"
            else -> null
        }
        isChangePassWord = isCurrentPassWordNotEmpty
                &&isNewPassWordNotEmpty
                &&isNewPassWordLength
                &&isConfirmPassWorkNotEmpty
                &&isNewPassWordAndConfirmPassWordMatch
    }
    fun reset(){
        currentPassWord = ""
        newPassWord = ""
        confirmPassWord = ""
        isChangePassWord = false
        currentPassWordErr = null
        newPassWordErr = null
        confirmPassWordErr = null
    }

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePassWord(
    navController: NavController,
    changePassWordViewModel: ChangePassWordViewModel = viewModel(),
    taiKhoanViewModel: TaiKhoanViewModel = viewModel()
){
    var currentPassWordVisible by remember{ mutableStateOf(false) }
    var newPassWordVisible by remember{ mutableStateOf(false) }
    var comfirmPassWordVisible by remember{ mutableStateOf(false) }
    var isSuccess by remember{ mutableStateOf(false) }
    val httpStatus by taiKhoanViewModel.httpStatus.observeAsState()
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize()) {
        AppBarView(
            title = "Đổi mật khẩu",
            color = R.color.white,
            backgroundColor = R.color.darkblue,
            alignment = Alignment.Center,
            isVisible = true,
            onDeleteNavClicked = {
                navController.popBackStack()
                changePassWordViewModel.reset()
            }
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //-------------------------------------
                OutlinedTextField(
                    value = changePassWordViewModel.currentPassWord,
                    onValueChange = {changePassWordViewModel.onCurrentPassWordChanged(it)},
                    leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) },
                    label = { Text(text = "Nhập mật khẩu hiện tại") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation =
                    if(currentPassWordVisible) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    trailingIcon =
                    {
                        val image = if(currentPassWordVisible)
                            painterResource(id = R.drawable.ic_visibility)
                        else
                            painterResource(id = R.drawable.ic_visibility_off)
                        IconButton(onClick = { currentPassWordVisible =! currentPassWordVisible }) {
                            Icon(painter = image, contentDescription = if (currentPassWordVisible) "Ẩn mật khẩu" else "Hiển thị mật khẩu" )

                        }
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = colorResource(id = R.color.darkblue),    // Màu của đường viền khi trường được chọn
                        unfocusedBorderColor = Color.Gray,                            // Màu của đường viền khi trường không được chọn
                        focusedLabelColor = colorResource(id = R.color.darkblue),     // Màu của nhãn khi trường được chọn
                        unfocusedLabelColor = Color.Gray,                             // Màu của nhãn khi trường không được chọn
                        cursorColor = Color.Gray                                      // Màu của con trỏ
                    ),
                    isError = changePassWordViewModel.currentPassWordErr != null
                )
                //
                changePassWordViewModel.currentPassWordErr?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colors.error,
                        modifier = Modifier.padding(start = 4.dp, top = 1.dp)
                    )
                }
                //
                //--------------------------------------------
                //--------------------------------------------
                OutlinedTextField(
                    value = changePassWordViewModel.newPassWord,
                    onValueChange = {changePassWordViewModel.onNewPasWordChanged(it)},
                    leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) },
                    label = { Text(text = "Nhập mật khẩu mới") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation =
                    if(newPassWordVisible) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    trailingIcon =
                    {
                        val image = if(newPassWordVisible)
                            painterResource(id = R.drawable.ic_visibility)
                        else
                            painterResource(id = R.drawable.ic_visibility_off)
                        IconButton(onClick = { newPassWordVisible =! newPassWordVisible }) {
                            Icon(painter = image, contentDescription = if (newPassWordVisible) "Ẩn mật khẩu" else "Hiển thị mật khẩu" )

                        }
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = colorResource(id = R.color.darkblue),    // Màu của đường viền khi trường được chọn
                        unfocusedBorderColor = Color.Gray,                            // Màu của đường viền khi trường không được chọn
                        focusedLabelColor = colorResource(id = R.color.darkblue),     // Màu của nhãn khi trường được chọn
                        unfocusedLabelColor = Color.Gray,                             // Màu của nhãn khi trường không được chọn
                        cursorColor = Color.Gray                                      // Màu của con trỏ
                    ),
                    isError = changePassWordViewModel.newPassWordErr != null
                )
                //
                changePassWordViewModel.newPassWordErr?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colors.error,
                        modifier = Modifier.padding(start = 4.dp, top = 1.dp)
                    )
                }
                //
                //--------------------------------------------

                //--------------------------------------------
                OutlinedTextField(
                    value = changePassWordViewModel.confirmPassWord,
                    onValueChange = {changePassWordViewModel.onConfirmPassWorkChanged(it)},
                    leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) },
                    label = { Text(text = "Xác nhận mật khẩu mới") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation =
                    if(comfirmPassWordVisible) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    trailingIcon =
                    {
                        val image = if(comfirmPassWordVisible)
                            painterResource(id = R.drawable.ic_visibility)
                        else
                            painterResource(id = R.drawable.ic_visibility_off)
                        IconButton(onClick = { comfirmPassWordVisible =! comfirmPassWordVisible }) {
                            Icon(painter = image, contentDescription = if (comfirmPassWordVisible) "Ẩn mật khẩu" else "Hiển thị mật khẩu" )

                        }
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = colorResource(id = R.color.darkblue),    // Màu của đường viền khi trường được chọn
                        unfocusedBorderColor = Color.Gray,                            // Màu của đường viền khi trường không được chọn
                        focusedLabelColor = colorResource(id = R.color.darkblue),     // Màu của nhãn khi trường được chọn
                        unfocusedLabelColor = Color.Gray,                             // Màu của nhãn khi trường không được chọn
                        cursorColor = Color.Gray                                      // Màu của con trỏ
                    ),
                    isError = changePassWordViewModel.confirmPassWordErr != null
                )
                //
                changePassWordViewModel.confirmPassWordErr?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colors.error,
                        modifier = Modifier.padding(start = 4.dp, top = 1.dp)
                    )
                }
                //
                //--------------------------------------------
                Button(
                    onClick = {
                              changePassWordViewModel.validateChangePassWord()
                        if(changePassWordViewModel.isChangePassWord){
                            taiKhoanViewModel.changePassWord(IdTaiKhoan, changePassWordViewModel.currentPassWord, changePassWordViewModel.newPassWord)
                        }
                    },
                    modifier = Modifier
                        .width(200.dp)
                        .align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(id = R.color.darkblue),
                        contentColor = Color.White,

                        ),
                    shape = RoundedCornerShape(50),
                ) {
                    Text(text = "Đổi mật khẩu")

                }
            }
        }

    }
    LaunchedEffect(httpStatus) {
        httpStatus?.let { status ->
            dialogMessage = if (status == 200) {
                "Đổi mật khẩu thành công"
            } else if (status == 404) {
                "Bạn đã nhập sai mật khẩu hiện tại"
            } else {
                "Có lỗi xảy ra"
            }
            showDialog = true
        }
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Thông báo") },
            text = { Text(text = dialogMessage) },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
//                    changePassWordViewModel.reset()
                }
                ) {
                    Text("OK")
                }
            }
        )
    }

}

