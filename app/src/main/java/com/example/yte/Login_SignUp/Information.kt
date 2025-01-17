package com.example.yte.Login_SignUp

import android.app.DatePickerDialog
import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.camera.core.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.yte.AppBarView
import com.example.yte.Connect.DangKyViewModel
import com.example.yte.Connect.NguoiDungViewModel
import com.example.yte.Connect.TaiKhoanViewModel
import com.example.yte.R
import com.example.yte.loaitaikhoa
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.concurrent.Executors
import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.viewinterop.AndroidView
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.ExecutorService
import androidx.camera.core.ImageProxy
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import com.google.ar.core.ImageFormat

@Composable
fun Information(
    navController: NavController,
    inforViewModel: InforViewModel = viewModel(),
    signUpViewModel: SignUpViewModel = viewModel(),
    taiKhoanViewModel: TaiKhoanViewModel = viewModel(),
    nguoiDungViewModel: NguoiDungViewModel = viewModel(),
    dangKyViewModel: DangKyViewModel = viewModel()

){
    val tenSĐT = signUpViewModel.phoneNumber
    val matKhau = signUpViewModel.passWord
    val loaiTk = loaitaikhoa
    var showDialog by remember { mutableStateOf(false) }
    var showScanner by remember { mutableStateOf(false) }
    var scanResult by remember{ mutableStateOf("Chưa có kết quả") }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${month + 1}/$year"
            inforViewModel.onBirthdayChanged(selectedDate) // Sử dụng hàm để cập nhật
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
//    LaunchedEffect(tenSĐT) {
//        taiKhoanViewModel.fetchIdByTenTK(tenSĐT)
//
//    }
//    val idTaiKhoan = taiKhoanViewModel.id_taikhoan

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AppBarView(
            title = "Thông tin cá nhân",
            color = R.color.white  ,
            backgroundColor = R.color.darkblue ,
            alignment = Alignment.Center,
            onDeleteNavClicked = {
//                taiKhoanViewModel.deleteTaiKhoa(tenSĐT)
                navController.popBackStack()
            },
            isVisible = true
        )

        Spacer(modifier = Modifier.height(24.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable { showScanner = true }
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
            if (showScanner) {
                QRScanner(
                    onScanSuccess = { cccd, name, birthDate, gender, address ->
                        inforViewModel.apply {
                            onCCCDChanged(cccd)
                            onNameChanged(name)
                            onBirthdayChanged(birthDate)
                            onGenderChanged(gender)
                            onQuequanChanged(address)
                        }
                    },
                    onClose = { showScanner = false }
                )
            }
        }
        // ho ten
        OutlinedTextField(
            value = inforViewModel.name ,
            onValueChange = {inforViewModel.onNameChanged(it)},
            label = { Text(text = "Họ và tên")},
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorResource(id = R.color.darkblue),    // Màu của đường viền khi trường được chọn
                unfocusedBorderColor = Color.Gray,                            // Màu của đường viền khi trường không được chọn
                focusedLabelColor = colorResource(id = R.color.darkblue),     // Màu của nhãn khi trường được chọn
                unfocusedLabelColor = Color.Gray,                             // Màu của nhãn khi trường không được chọn
                cursorColor = Color.Gray                                      // Màu của con trỏ
            ),
            isError = inforViewModel.nameError != null
        )
        //kiem tra loi
        inforViewModel.nameError?.let{
            Text(
                text = it,
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
        //
        Spacer(modifier = Modifier.height(8.dp))
        //ngay sinh & gioi tinh
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = inforViewModel.birthday,
                onValueChange = {},
                label = { Text(text = "Ngày sinh")},
                trailingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_keyboard_arrow_down_24) ,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color.Gray)
                    )
                },
                readOnly = true,
                enabled = false,
                modifier = Modifier
                    .weight(1f)
                    .clickable { datePickerDialog.show() }
                    .padding(horizontal = 16.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = colorResource(id = R.color.darkblue),    // Màu của đường viền khi trường được chọn
                    unfocusedBorderColor = Color.Gray,                            // Màu của đường viền khi trường không được chọn
                    focusedLabelColor = colorResource(id = R.color.darkblue),     // Màu của nhãn khi trường được chọn
                    unfocusedLabelColor = Color.Gray,                             // Màu của nhãn khi trường không được chọn
                    cursorColor = Color.Gray                                      // Màu của con trỏ
                ),
                isError = inforViewModel.birthdayError != null
            )


            Row(  verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = inforViewModel.gender == "Nam",
                    onClick = { inforViewModel.onGenderChanged("Nam") },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = colorResource(id = R.color.darkblue),
                        colorResource(id = R.color.darkblue))
                )
                Text(
                    text = "Nam",
                    color = colorResource(id = R.color.darkblue),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 4.dp
                    )
                )

                Spacer(modifier = Modifier.width(16.dp))

                RadioButton(
                    selected = inforViewModel.gender == "Nữ",
                    onClick = { inforViewModel.onGenderChanged("Nữ") },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = colorResource(id = R.color.darkblue),
                        colorResource(id = R.color.darkblue)),
                )
                Text(
                    text = "Nữ",
                    color = colorResource(id = R.color.darkblue),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 4.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
            }


        }
        // kiem tra loi
        inforViewModel.birthdayError?.let{
            Text(
                text = it,
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
        //kiem tra loi
        inforViewModel.genderError?.let {
            Text(
                text = it,
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
        //
        //
        Spacer(modifier = Modifier.height(8.dp))
        // so cccd
        OutlinedTextField(
            value = inforViewModel.CCCD ,
            onValueChange = {inforViewModel.onCCCDChanged(it)},
            label = { Text(text = "Số CCCD")},
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorResource(id = R.color.darkblue),    // Màu của đường viền khi trường được chọn
                unfocusedBorderColor = Color.Gray,                            // Màu của đường viền khi trường không được chọn
                focusedLabelColor = colorResource(id = R.color.darkblue),     // Màu của nhãn khi trường được chọn
                unfocusedLabelColor = Color.Gray,                             // Màu của nhãn khi trường không được chọn
                cursorColor = Color.Gray                                      // Màu của con trỏ
            ),
            isError = inforViewModel.CCCDError != null
        )
        inforViewModel.CCCDError?.let {
            Text(
                text = it,
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        // que quan
        OutlinedTextField(
            value = inforViewModel.quequan ,
            onValueChange = {inforViewModel.onQuequanChanged(it)},
            label = { Text(text = "Quê quán")},
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorResource(id = R.color.darkblue),    // Màu của đường viền khi trường được chọn
                unfocusedBorderColor = Color.Gray,                            // Màu của đường viền khi trường không được chọn
                focusedLabelColor = colorResource(id = R.color.darkblue),     // Màu của nhãn khi trường được chọn
                unfocusedLabelColor = Color.Gray,                             // Màu của nhãn khi trường không được chọn
                cursorColor = Color.Gray                                      // Màu của con trỏ
            ),
            isError = inforViewModel.quequanError != null

        )
        // kiem tra loi
        inforViewModel.quequanError?.let{
            Text(
                text = it,
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
        //
        // nut hoan thanh
        Spacer(modifier = Modifier.height(80.dp))
        Button(
            onClick = {
                try {
                    // Gọi tuần tự
//                    taiKhoanViewModel.addTaiKhoan(tenSĐT, matKhau, loaiTk)
//
//                    // Sau khi thêm tài khoản thành công, gọi API để lấy id_taikhoan theo tenSĐT
//                    taiKhoanViewModel.fetchIdByTenTK(tenSĐT)
//
//                    // Bạn có thể theo dõi idTaiKhoan trong UI

                    showDialog = true
                } catch (e: Exception) {
                    Log.e("Button", "Error: ${e.message}")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.darkblue)
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Hoàn thành",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

        }
        //
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = {
                    Text(text = "Bạn xác nhận đăng ký tài khoản ")

                },

                confirmButton = {
                    Button(onClick = {
                        CoroutineScope(Dispatchers.IO).launch{
//                            nguoiDungViewModel.addNguoiDung(
//                                tenSĐT,
//                                inforViewModel.name,
//                                tenSĐT,
//                                inforViewModel.birthday,
//                                inforViewModel.CCCD,
//                                inforViewModel.quequan,
//                                inforViewModel.gender,
//                                0
//                            )
                            dangKyViewModel.dangKy(
                                tentk = tenSĐT,
                                matkhau = matKhau,
                                loaitk = loaiTk,
                                hoten = inforViewModel.name,
                                sdt = tenSĐT,
                                ngaysinh = inforViewModel.birthday,
                                cccd = inforViewModel.CCCD,
                                quequan = inforViewModel.quequan,
                                gioitinh = inforViewModel.gender,
                                onSuccess = { message ->
                                    // Xử lý khi thành công
                                    Log.d("API", message)
                                    showDialog = false
                                    navController.navigate("LoginSignUpScreen") // Điều hướng về màn hình đăng nhập
                                },
                                onError = { error ->
                                    // Xử lý khi có lỗi
                                    Log.e("API", error)
                                    showDialog = false
                                }
                            )
                        }

                        showDialog = false
                        navController.navigate("LoginSignUpScreen") // Điều hướng sau khi tắt dialog

                    },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = colorResource(id = R.color.darkblue)
                        )
                    ) {
                        Text(text = "Xác nhận", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton = {
                    Button(onClick = {

                        showDialog = false },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = colorResource(id = R.color.darkblue)
                        )
                    ) {
                        Text(text = "Hủy",  color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            )
        }
    }
}
//


@Composable
fun QRScanner(
    onScanSuccess: (String, String, String, String, String) -> Unit,
    onClose: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }
    var torchEnabled by remember { mutableStateOf(false) }
    var camera by remember { mutableStateOf<Camera?>(null) }

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasCameraPermission = granted
        }
    )

    LaunchedEffect(Unit) {
        launcher.launch(Manifest.permission.CAMERA)
    }

    if (hasCameraPermission) {
        Box(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                factory = { context ->
                    PreviewView(context).apply {
                        implementationMode = PreviewView.ImplementationMode.PERFORMANCE
                        scaleType = PreviewView.ScaleType.FILL_CENTER
                    }.also { previewView ->
                        setupCamera(
                            context = context,
                            lifecycleOwner = lifecycleOwner,
                            cameraExecutor = cameraExecutor,
                            previewView = previewView,
                            onCameraSet = { cam ->
                                camera = cam
                            },
                            onBarcodeDetected = { barcodeValue ->
                                try {
                                    val processedValue = processQRContent(barcodeValue)
                                    if (processedValue != null) {
                                        val (cccd, name, birthDate, gender, address) = processedValue
                                        onScanSuccess(cccd, name, birthDate, gender, address)
                                        onClose()
                                    } else {
                                        Toast.makeText(context, "Không thể đọc thông tin từ QR", Toast.LENGTH_SHORT).show()
                                    }
                                } catch (e: Exception) {
                                    Toast.makeText(context, "Lỗi xử lý QR: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                            }
                        )
                    }
                },
                modifier = Modifier.fillMaxSize()
            )

            // Nút bật/tắt đèn flash
            IconButton(
                onClick = {
                    torchEnabled = !torchEnabled
                    camera?.cameraControl?.enableTorch(torchEnabled)
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .size(48.dp)
                    .background(Color.Black.copy(alpha = 0.5f), CircleShape)
            ) {
                Icon(
                    painter = painterResource(
                        id = if (torchEnabled)
                            R.drawable.baseline_flash_on_24
                        else
                            R.drawable.baseline_flash_off_24
                    ),
                    contentDescription = "Toggle flash",
                    tint = Color.White
                )
            }

            // Nút đóng
            IconButton(
                onClick = onClose,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
                    .size(48.dp)
                    .background(Color.Black.copy(alpha = 0.5f), CircleShape)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_close_24),
                    contentDescription = "Close",
                    tint = Color.White
                )
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            cameraExecutor.shutdown()
        }
    }
}

private fun processQRContent(rawContent: String): QRData? {
    return try {
        when {
            rawContent.contains("|") -> {
                val parts = rawContent.split("|")
                if (parts.size >= 6) {
                    QRData(
                        cccd = parts[0].trim(),
                        name = parts[2].trim(),
                        birthDate = formatBirthDate(parts[3].trim()),
                        gender = parseGender(parts[4].trim()),
                        address = parts[5].trim()
                    )
                } else null
            }
            rawContent.contains("$") -> {
                val parts = rawContent.split("$")
                if (parts.size >= 6) {
                    QRData(
                        cccd = parts[0].trim(),
                        name = parts[2].trim(),
                        birthDate = formatBirthDate(parts[3].trim()),
                        gender = parseGender(parts[4].trim()),
                        address = parts[5].trim()
                    )
                } else null
            }
            else -> null
        }
    } catch (e: Exception) {
        Log.e("QRProcessor", "Error processing QR content: ${e.message}")
        null
    }
}

private fun formatBirthDate(raw: String): String {
    return try {
        "${raw.substring(0, 2)}/${raw.substring(2, 4)}/${raw.substring(4)}"
    } catch (e: Exception) {
        raw
    }
}

private fun parseGender(raw: String): String {
    return when (raw.trim().lowercase()) {
        "nam", "male", "m" -> "Nam"
        "nữ", "nu", "female", "f" -> "Nữ"
        else -> raw
    }
}

@OptIn(ExperimentalGetImage::class)
private fun setupCamera(
    context: android.content.Context,
    lifecycleOwner: androidx.lifecycle.LifecycleOwner,
    cameraExecutor: ExecutorService,
    previewView: PreviewView,
    onCameraSet: (Camera) -> Unit,
    onBarcodeDetected: (String) -> Unit
) {
    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

    cameraProviderFuture.addListener({
        val cameraProvider = cameraProviderFuture.get()

        val preview = Preview.Builder()
            .setTargetRotation(previewView.display.rotation)
            .build()
        preview.setSurfaceProvider(previewView.surfaceProvider)

        val imageAnalysis = ImageAnalysis.Builder()
            .setTargetRotation(previewView.display.rotation)
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()

        imageAnalysis.setAnalyzer(cameraExecutor) { imageProxy ->
            val mediaImage = imageProxy.image
            if (mediaImage != null) {
                val image = InputImage.fromMediaImage(
                    mediaImage,
                    imageProxy.imageInfo.rotationDegrees
                )

                val scanner = BarcodeScanning.getClient()
                scanner.process(image)
                    .addOnSuccessListener { barcodes ->
                        for (barcode in barcodes) {
                            barcode.rawValue?.let { value ->
                                onBarcodeDetected(value)
                            }
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.e("QRScanner", "Barcode scanning failed: ${e.message}")
                    }
                    .addOnCompleteListener {
                        imageProxy.close()
                    }
            } else {
                imageProxy.close()
            }
        }

        try {
            cameraProvider.unbindAll()
            val camera = cameraProvider.bindToLifecycle(
                lifecycleOwner,
                CameraSelector.DEFAULT_BACK_CAMERA,
                preview,
                imageAnalysis
            )
            onCameraSet(camera)
        } catch (e: Exception) {
            Log.e("QRScanner", "Use case binding failed", e)
        }
    }, ContextCompat.getMainExecutor(context))
}

data class QRData(
    val cccd: String,
    val name: String,
    val birthDate: String,
    val gender: String,
    val address: String
)