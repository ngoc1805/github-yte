package com.example.yte

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import java.time.Duration
import java.time.format.DateTimeParseException


fun formatNumber(number: Int): String {
    return "%,d".format(number).replace(',', '.')
}
@RequiresApi(Build.VERSION_CODES.O)
fun chuyenDoiNgay(date: String): String {
    if (date.isEmpty()) {
        // Nếu chuỗi rỗng, trả về chuỗi mặc định hoặc thông báo lỗi
        return "Ngày không hợp lệ"
    }

    return try {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd") // Định dạng đầu vào
        val outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy") // Định dạng đầu ra
        val parsedDate = LocalDate.parse(date, inputFormatter) // Chuyển từ chuỗi sang LocalDate
        parsedDate.format(outputFormatter) // Chuyển từ LocalDate sang chuỗi theo định dạng mới
    } catch (e: DateTimeParseException) {
        // Xử lý khi không thể phân tích được ngày (ngày không hợp lệ)
        "Ngày không hợp lệ"
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun chuyenDoiGio(time: String): String {
    val inputFormatter = DateTimeFormatter.ofPattern("HH:mm:ss") // Định dạng đầu vào
    val outputFormatter = DateTimeFormatter.ofPattern("HH:mm")  // Định dạng đầu ra
    val parsedTime = LocalTime.parse(time, inputFormatter)      // Chuyển từ chuỗi sang LocalTime
    return parsedTime.format(outputFormatter)                  // Chuyển từ LocalTime sang chuỗi theo định dạng mới
}
@RequiresApi(Build.VERSION_CODES.O)
fun soSanhThoiGian(thoiGian: String): String {
    // Parse thời gian từ chuỗi đầu vào (ISO format)
    val parsedTime = LocalDateTime.parse(thoiGian)

    // Lấy thời gian hiện tại
    val now = LocalDateTime.now()

    // Chuyển đổi sang Instant với ZoneOffset.UTC
    val nowInstant = now.toInstant(ZoneOffset.UTC)
    val parsedTimeInstant = parsedTime.toInstant(ZoneOffset.UTC)

    // Tính khoảng cách giữa hai thời điểm
    val duration = Duration.between(parsedTimeInstant, nowInstant)

    // Convert sang các đơn vị thời gian
    val minutes = duration.toMinutes()
    val hours = duration.toHours()
    val days = ChronoUnit.DAYS.between(parsedTime, now)

    // Định dạng thời gian theo định dạng ngày
    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    // Trả về chuỗi
    return when {
        days >= 3 -> parsedTime.format(dateFormatter) // Nếu lớn hơn hoặc bằng 3 ngày, trả về ngày cụ thể
        hours >= 24 -> "$days ngày trước"            // Nếu từ 1 đến dưới 3 ngày, trả về "x ngày trước"
        hours > 0 -> "$hours giờ trước"              // Nếu dưới 1 ngày, trả về "x giờ trước"
        else -> "$minutes phút trước"                // Nếu dưới 1 giờ, trả về "x phút trước"
    }
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
                        modifier = Modifier.align(alignment).offset(y = 16.dp)
                    )

                    // Biểu tượng thùng rác ở góc phải nếu tiêu đề là "Thông báo"
//                    if (title.contains("Thông báo")) {
//                        IconButton(
//                            onClick = onDeleteNavClicked,
//                            modifier = Modifier.align(Alignment.BottomEnd) // Đặt biểu tượng về góc phải
//                        ) {
//                            Icon(
//                                imageVector = Icons.Default.Delete,
//                                tint = Color.White,
//                                contentDescription = "Delete Icon"
//                            )
//                        }
//                    }
                    // Biểu tượng ArrowBack nếu tiêu đề là "Thông tin cá nhân"
                    if (title.contains("Thông tin cá nhân")) {
                        IconButton(
                            onClick = onDeleteNavClicked,
                            modifier = Modifier.align(Alignment.BottomStart).offset(y = 8.dp)
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
                            modifier = Modifier
                                .align(Alignment.BottomStart).offset(y = 8.dp)
                                .padding(16.dp)
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
                            modifier = Modifier.align(Alignment.BottomStart).offset(y = 8.dp)
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
                            modifier = Modifier.align(Alignment.BottomStart).offset(y = 8.dp)
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
                            modifier = Modifier.align(Alignment.BottomStart).offset(y = 8.dp)
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
                            modifier = Modifier.align(Alignment.BottomStart).offset(y = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                tint = Color.White,
                                contentDescription = "ArrowBack Icon"
                            )

                        }
                    }

                    if (title.contains("Lịch hẹn")) {
                        IconButton(
                            onClick = onDeleteNavClicked,
                            modifier = Modifier.align(Alignment.BottomStart).offset(y = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                tint = Color.Black,
                                contentDescription = "ArrowBack Icon"
                            )

                        }
                    }

                    if (title.contains("Nội dung chi tiết")) {
                        IconButton(
                            onClick = onDeleteNavClicked,
                            modifier = Modifier.align(Alignment.BottomStart).offset(y = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                tint = Color.White,
                                contentDescription = "ArrowBack Icon"
                            )

                        }
                    }
                    //
                    if (title.contains("Thanh toán")) {
                        IconButton(
                            onClick = onDeleteNavClicked,
                            modifier = Modifier.align(Alignment.BottomStart).offset(y = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                tint = Color.White,
                                contentDescription = "ArrowBack Icon"
                            )

                        }
                    }
                    //
                    if (title.contains("Lịch sử đặt khám")) {
                        IconButton(
                            onClick = onDeleteNavClicked,
                            modifier = Modifier.align(Alignment.BottomStart).offset(y = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                tint = Color.White,
                                contentDescription = "ArrowBack Icon"
                            )

                        }
                    }
                    //
                    if (title.contains("Khám bệnh")) {
                        IconButton(
                            onClick = onDeleteNavClicked,
                            modifier = Modifier.align(Alignment.BottomStart).offset(y = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                tint = Color.Black,
                                contentDescription = "ArrowBack Icon"
                            )

                        }
                    }
                    //
                    if (title.contains("Kết quả khám bệnh")) {
                        IconButton(
                            onClick = onDeleteNavClicked,
                            modifier = Modifier.align(Alignment.BottomStart).offset(y = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                tint = Color.White,
                                contentDescription = "ArrowBack Icon"
                            )

                        }
                    }
                    //
                    if (title.contains("Trò chuyện cùng AI")) {
                        IconButton(
                            onClick = onDeleteNavClicked,
                            modifier = Modifier.align(Alignment.BottomStart).offset(y = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                tint = Color.White,
                                contentDescription = "ArrowBack Icon"
                            )

                        }
                    }
                    //
                    if (title.contains("Đổi mật khẩu")) {
                        IconButton(
                            onClick = onDeleteNavClicked,
                            modifier = Modifier.align(Alignment.BottomStart).offset(y = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                tint = Color.White,
                                contentDescription = "ArrowBack Icon"
                            )

                        }
                    }
                    //
                    if (title.contains("Thông tin người dùng")) {
                        IconButton(
                            onClick = onDeleteNavClicked,
                            modifier = Modifier.align(Alignment.BottomStart).offset(y = 8.dp)
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


@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    // Thời gian chờ 3 giây
    LaunchedEffect(Unit) {
        delay(2000) // 3 giây
        onTimeout()
    }

    // Giao diện SplashScreen
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Hiển thị logo hoặc ảnh
       Image(painter = painterResource(id = R.drawable.medicalteam), contentDescription = null)
    }
}
