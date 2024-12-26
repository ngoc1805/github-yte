//package com.example.yte.Otp
//
//import android.app.Activity
//import kotlinx.coroutines.flow.Flow
//import dagger.Binds
//import dagger.Module
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//
//
//
//interface AuthRepository {
//
//   fun createUserWithPhone(
//       phone: String,
//       activity:Activity
//   ) : Flow<ResultState<String>>
//
//   fun signWithCredential(
//       otp: String
//
//   ) : Flow<ResultState<String>>
//}