package com.example.yte.News

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.yte.AppBarView
import com.example.yte.Appointment.Day
import com.example.yte.R

class detailViewModel: ViewModel(){
    val selectNews = mutableStateOf<NewsClass?>(null)
}
@Composable
fun DisplayNews(
    newsClass: NewsClass,
    onClicked: () -> Unit = {} ,
    ){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
            .clickable {

                onClicked()
            },
        elevation = 2.dp,

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth().padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = newsClass.tieuDe,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = rememberAsyncImagePainter(model = newsClass.linkAnh) ,
                contentDescription = null,
                modifier = Modifier.size(100.dp)

            )

        }

    }
}
@Composable
fun DisplayNewsLazy(
    newsViewModel: newsViewModel = viewModel(),
    navController: NavController,
    detailViewModel: detailViewModel = viewModel()
){


    val newslist = newsViewModel.newsList
    LaunchedEffect(Unit) {
        newsViewModel.fetchNews()
    }
    LazyColumn( ) {
        items(newslist){news->
            DisplayNews(

                newsClass = news,
                onClicked = {
                    detailViewModel.selectNews.value = news
                    if(detailViewModel.selectNews.value !=null)
                    {
                        navController.navigate("newsDetail")
                    }
                    else{
                        Log.d("DisplayNewsLazy", "Không có dữ liệu để truyền.")
                    }
                }

            )
        }
    }
}
@Composable
fun newsDetail(
    navController: NavController,
    newsViewModel: newsViewModel = viewModel(),
    detailViewModel: detailViewModel = viewModel()
) {
    val news = detailViewModel.selectNews.value
    Column(modifier = Modifier.fillMaxSize()) {


        // AppBar
        AppBarView(
            title = "Nội dung chi tiết",
            color = R.color.white,
            backgroundColor = R.color.darkblue,
            alignment = Alignment.Center,
            onDeleteNavClicked = { navController.popBackStack() },
            isVisible = true
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                 // Thêm padding cho toàn bộ Column
        ) {

            Spacer(modifier = Modifier.height(4.dp)) // Khoảng cách giữa AppBar và phần nội dung

            // Kiểm tra nếu có tin tức
            news?.let { newsClass ->
                // Tiêu đề
                Text(
                    text = newsClass.tieuDe,
                    fontSize = 20.sp, // Tăng font size cho tiêu đề
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp) // Khoảng cách dưới tiêu đề
                )

                // Hình ảnh
                newsClass.linkAnh?.let { imageUrl ->
                    Image(
                        painter = rememberAsyncImagePainter(model = imageUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth() // Chiếm toàn bộ chiều rộng của màn hình
                            .aspectRatio(1f)
                            .height(20.dp)
                            // Khoảng cách dưới hình ảnh
                    )
                }

                // Nội dung
                Text(
                    text = newsClass.noiDung,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(8.dp) // Khoảng cách dưới nội dung
                )
            }
        }
    }
}




//@Preview(showBackground = true)
//@Composable
//fun Displayprev(){
//    val news = NewsClass(
//        id_tintuc = 1,
//        tieuDe = "Lời khuyên dinh dưỡng cho bệnh nhân mắc bệnh tiểu đường",
//        linkAnh = "https://i.ibb.co/Q86CTMZ/account.png",
//        noiDung = "Ngọc đẹp trai"
//    )
//    DisplayNews(newsClass = news)
//}