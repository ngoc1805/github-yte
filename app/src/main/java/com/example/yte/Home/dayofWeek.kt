package com.example.yte.Home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.contentColorFor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.*
import com.example.yte.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

data class Day(
    val name: String,   // "T2", "T3", ..., "CN"
    val date: String,   // "04/11", "05/11", ...
    val isSelected: Boolean = false // Để đánh dấu ngày đang được chọn
)

fun getWeekDays(startDate: Calendar) : List<Day>{
    val daysOfWeek = mutableListOf<Day>()
    val dateFormat = SimpleDateFormat("dd/MM", Locale.getDefault())

    // Đặt ngày bắt đầu là thứ 2 đầu tuần
    val calendar = startDate.clone() as Calendar
    calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)

    for (i in 0..6) {
        val dayName = if (i == 6) "CN" else "T${i + 2}" // "T2", "T3", ..., "CN"
        val date = dateFormat.format(calendar.time)
        val isSelected = calendar.get(Calendar.DAY_OF_YEAR) == Calendar.getInstance().get(Calendar.DAY_OF_YEAR)

        daysOfWeek.add(Day(name = dayName, date = date, isSelected = isSelected))
        calendar.add(Calendar.DAY_OF_MONTH, 1)
    }
    return daysOfWeek

}

fun getWeeks(numWeeks: Int):  List<List<Day>>{
    val weeks = mutableListOf<List<Day>>()
    val calendar = Calendar.getInstance()

    // Tạo tuần hiện tại và các tuần tiếp theo
    for (i in 0 until numWeeks) {
        weeks.add(getWeekDays(calendar))
        calendar.add(Calendar.WEEK_OF_YEAR, 1) // Chuyển sang tuần tiếp theo
    }
    return weeks

}

@Composable
fun DayItem(day: Day){
    Box(
        modifier = Modifier
            .padding(2.dp)
            .width(50.dp)
            .height(60.dp)
            .background(
                color = if (day.isSelected) colorResource(id = R.color.darkblue)
                else Color.LightGray,
                shape = RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = day.name, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = day.date, color = Color.White)

        }

    }
}

@Composable
fun WeekRow(week: List<Day>){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
        ) {
        week.forEach {day ->
            DayItem(day = day)

        }

    }

}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun WeekDaysRow(){
    val weeks = remember { mutableStateListOf<List<Day>>() }
    val calendar = Calendar.getInstance()

    LaunchedEffect(Unit) {
        repeat(1) {
            weeks.add(getWeekDays(calendar.clone() as Calendar))
            calendar.add(Calendar.WEEK_OF_YEAR, 1)
        }
    }

    val pagerState = rememberPagerState()

    HorizontalPager(
        count = weeks.size,
        state = pagerState,
        modifier = Modifier.fillMaxWidth()
    ) { page ->
        WeekRow(week = weeks[page])

        // Thêm tuần mới khi cuộn đến trang cuối
        if (page == weeks.size - 1) {
            calendar.add(Calendar.WEEK_OF_YEAR, 1)
            weeks.add(getWeekDays(calendar.clone() as Calendar))
        }
    }

//    LazyRow(
//
//    ) {
//        itemsIndexed(weeks) { index, week ->
//            // Hiển thị một tuần
//            WeekRow(week = week)
//            Spacer(modifier = Modifier.width(24.dp))
//
//            // Khi đến gần cuối danh sách, thêm tuần mới
//            if (index == weeks.size - 1) {
//                // Thêm một tuần mới khi lướt đến cuối
//                weeks.add(getWeekDays(calendar.clone() as Calendar))
//                calendar.add(Calendar.WEEK_OF_YEAR, 1)
//            }
//        }
//    }
}
@Preview(showBackground = true)
@Composable
fun WeekDaysRowPR(){
    WeekDaysRow()
}


