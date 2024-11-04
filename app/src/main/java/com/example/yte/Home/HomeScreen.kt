package com.example.yte.Home
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.yte.R
import kotlinx.coroutines.delay


@Composable
fun HomeScreen(navController: NavController){
    ImageSlider()
    Spacer(modifier = Modifier.height(32.dp))
    ButtonGrid(navController)
}


@Composable
fun ImageSlider(){
    val images = listOf(
        R.drawable.lacquan,
        R.drawable.phongchonghiv,
        R.drawable.sieuam,
        R.drawable.ruatay
    )
    var currentImageIndex by remember { mutableStateOf(0) }

    LaunchedEffect(currentImageIndex) {
        delay(3000L)
        currentImageIndex = (currentImageIndex + 1) % images.size  // Quay lại ảnh đầu tiên khi hết danh sách
    }
    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.2f)
        .padding(horizontal = 8.dp)
    ) {
        Image(
            painter = painterResource(id = images[currentImageIndex]),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun CustomButtons(
    icon: Painter,
    text: String,
    isSelected: Boolean = false,
    modifier: Modifier = Modifier,
    onDeleteNavClicked: () -> Unit = {},
    ) {
    Button(
            onClick = { onDeleteNavClicked() },
            modifier = modifier
                .clip(RoundedCornerShape(50.dp))
                .height(50.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = if (isSelected) Color(0xFF005BAC) else Color.White,
                contentColor = if (isSelected) Color.White else Color(0xFF005BAC)
            ),
            border =  if (!isSelected) BorderStroke(1.dp, Color(0xFF005BAC)) else null
    ) {
        Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                painter = icon,
                contentDescription = text,
                modifier = Modifier.size(24.dp),
                tint = if(isSelected) Color.White else Color(0xFF0856A8)
                )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                    text = text,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = if(isSelected) Color.White else Color(0xFF0856A8)
            )
        }
    }
}

@Composable
fun ButtonGrid(navController: NavController){
    Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CustomButtons(
                icon = painterResource(id = R.drawable.datlichkham),
                text = "Đặt lịch khám",
                isSelected = true,
                modifier = Modifier.weight(1f),
                onDeleteNavClicked = { navController.navigate("Booking")}
            )
        CustomButtons(
                icon = painterResource(id = R.drawable.hososuckhoe),
                text = "Hồ sơ sức khỏe",
                modifier = Modifier.weight(1f),
                onDeleteNavClicked = { navController.navigate("HealthRecords")}
            )
        }
    Spacer(modifier = Modifier.height(12.dp))
    Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CustomButtons(
                icon = painterResource(id = R.drawable.datlichtiemvaccine),
                text = "Đặt lịch tiêm chủng ",
                modifier = Modifier.weight(1f)
        )
//            CustomButtons(
//                icon = ImageVector.vectorResource(id = R.drawable.ic_visibility),
//                text = "Bản đồ bệnh viện ",
//                modifier = Modifier.weight(1f),)
        Spacer(modifier = Modifier.weight(1f))
    }
}

//@Preview(showBackground = true)
//@Composable
//fun HomeScreenPreview(){
//   ButtonGrid()
//}

