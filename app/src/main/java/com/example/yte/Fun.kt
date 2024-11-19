package com.example.yte

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController

fun formatNumber(number: Int): String {
    return "%,d".format(number).replace(',', '.')
}
@Composable
fun AppBarView(
    title: String,
    color: Int,
    backgroundColor: Int,
    alignment: Alignment,
    onDeleteNavClicked: () -> Unit = {},
    isVisible: Boolean


) {
    if (isVisible) {
        TopAppBar(
            backgroundColor = colorResource(id = backgroundColor),

            elevation = 3.dp,
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp), // Tăng chiều cao của AppBar

            title = {
                // Dùng Box để tiêu đề ở giữa và biểu tượng thùng rác ở góc phải
                Box(modifier = Modifier.fillMaxWidth()) {
                    // Tiêu đề căn giữa
                    Text(
                        text = title,
                        color = colorResource(id = color),
                        fontSize = 20.sp, // Tăng kích thước chữ
                        modifier = Modifier.align(alignment)
                    )

                    // Biểu tượng thùng rác ở góc phải nếu tiêu đề là "Thông báo"
                    if (title.contains("Thông báo")) {
                        IconButton(
                            onClick = onDeleteNavClicked,
                            modifier = Modifier.align(Alignment.BottomEnd) // Đặt biểu tượng về góc phải
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                tint = Color.White,
                                contentDescription = "Delete Icon"
                            )
                        }
                    }
                    // Biểu tượng ArrowBack nếu tiêu đề là "Thông tin cá nhân"
                    if (title.contains("Thông tin cá nhân")) {
                        IconButton(
                            onClick = onDeleteNavClicked,
                            modifier = Modifier.align(Alignment.BottomStart)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                tint = Color.White,
                                contentDescription = "ArrowBack Icon"
                            )

                        }
                    }
                    //
                    if (title.contains("Chỉ số sức khỏe")) {
                        IconButton(
                            onClick = onDeleteNavClicked,
                            modifier = Modifier.align(Alignment.BottomStart)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                tint = Color.White,
                                contentDescription = "ArrowBack Icon"
                            )

                        }
                    }
                    //
                    if (title.contains("Hồ sơ sức khỏe")) {
                        IconButton(
                            onClick = onDeleteNavClicked,
                            modifier = Modifier.align(Alignment.BottomStart)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                tint = Color.White,
                                contentDescription = "ArrowBack Icon"
                            )

                        }
                    }
                    //
                    if (title.contains("Nạp tiền")) {
                        IconButton(
                            onClick = onDeleteNavClicked,
                            modifier = Modifier.align(Alignment.BottomStart)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                tint = Color.White,
                                contentDescription = "ArrowBack Icon"
                            )

                        }
                    }
                    //
                    if (title.contains("Đặt lịch khám")) {
                        IconButton(
                            onClick = onDeleteNavClicked,
                            modifier = Modifier.align(Alignment.BottomStart)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                tint = Color.White,
                                contentDescription = "ArrowBack Icon"
                            )

                        }
                    }
                    //
                    if (title.contains("Chọn bác sĩ")) {
                        IconButton(
                            onClick = onDeleteNavClicked,
                            modifier = Modifier.align(Alignment.BottomStart)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                tint = Color.White,
                                contentDescription = "ArrowBack Icon"
                            )

                        }
                    }
                    //
                    if (title.contains("Lịch hẹn")) {
                        IconButton(
                            onClick = onDeleteNavClicked,
                            modifier = Modifier.align(Alignment.BottomStart)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                tint = Color.Black,
                                contentDescription = "ArrowBack Icon"
                            )

                        }
                    }
                    //
                    if (title.contains("Nội dung chi tiết")) {
                        IconButton(
                            onClick = onDeleteNavClicked,
                            modifier = Modifier.align(Alignment.BottomStart)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                tint = Color.White,
                                contentDescription = "ArrowBack Icon"
                            )

                        }
                    }
                    //
                }
            }
        )
    }
}

@Composable
fun DismissKeyboard(content: @Composable () -> Unit) {
    val focusManager = LocalFocusManager.current
    androidx.compose.foundation.layout.Box(
        modifier = Modifier
            .fillMaxSize()//.clickable
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
    ) {
        content()
    }
}

@Composable
fun keyboardAsState(): State<Boolean> {
    val view = LocalView.current
    val isKeyboardOpen = remember { mutableStateOf(false) }

    DisposableEffect(view) {
        val listener = ViewCompat.setOnApplyWindowInsetsListener(view) { _, insets ->
            val isKeyboardVisible = insets.isVisible(WindowInsetsCompat.Type.ime())
            isKeyboardOpen.value = isKeyboardVisible
            insets
        }
        onDispose {
            ViewCompat.setOnApplyWindowInsetsListener(view, null)
        }
    }

    return isKeyboardOpen
}
