package com.example.yte.APIs

import androidx.compose.foundation.Image
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
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.yte.AppBarView
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yte.Home.WeekDaysRow
import com.example.yte.R

@Composable
fun ClinicDetailScreen(
    clinicName: String,
    navController: NavController,
    doctorViewModel : DoctorViewModel = viewModel()
) {
    LaunchedEffect(clinicName) {
        doctorViewModel.fetchDoctors(clinicName)
    }
    val doctors = doctorViewModel.doctorList
    Column(
        modifier = Modifier
            .fillMaxSize(),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AppBarView(
            title = "Chọn bác sĩ",
            color = R.color.white ,
            backgroundColor = R.color.darkblue,
            alignment = Alignment.Center,
            onDeleteNavClicked = {navController.popBackStack()}
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Bác sĩ $clinicName",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        WeekDaysRow()
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Số lượng bác sĩ: ${doctors.size}")
        LazyColumn {
            items(doctors) { doctor ->
                DoctorCard(doctor = doctor)
            }
        }


    }
}

@Composable
fun DoctorCard(doctor: Doctor){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_person_24), 
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .padding(end = 0.dp),
                colorFilter = ColorFilter.tint(colorResource(id = R.color.darkblue))
                )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = doctor.hoten,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Box {
                    Text(
                        text = "Khoa: ${doctor.khoa}",
                        fontSize = 14.sp
                    )

                }
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.coin) ,
                        contentDescription =null,
                        modifier = Modifier.size(13.dp),
                        colorFilter = ColorFilter.tint(colorResource(id = R.color.darkblue))
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Giá khám: ${doctor.giakham} VND",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.darkblue)
                    )
                }
            }
            
        }

    }
}
@Composable
fun test(doctors: List<Doctor>){
    LazyColumn {
        items(doctors) { doctor ->
            DoctorCard(doctor = doctor)
    }}
}

//

//


@Preview(showBackground = true)
@Composable
fun DoctorCardPR(){
    val sampleDoctor = Doctor(
        bac_si_id = 1,
        hoten = "Bác sĩ Nguyễn Văn A",
        khoa = "Khoa nội",
        giakham = 200000
    )
    DoctorCard(doctor = sampleDoctor)
}