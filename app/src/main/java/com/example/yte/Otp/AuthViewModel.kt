//package com.example.yte.Otp
//
//import android.app.Activity
//import androidx.lifecycle.ViewModel
//import javax.inject.Inject
//import dagger.hilt.android.lifecycle.HiltViewModel
//
//
//@HiltViewModel
//class AuthViewModel @Inject constructor(
//    private val repo: AuthRepository
//) : ViewModel() {
//
//    fun createUserWithPhone(
//        mobile: String,
//        activity: Activity
//    ) = repo.createUserWithPhone(mobile, activity)
//
//    fun signInWithCredential(
//        code: String
//    ) = repo.signWithCredential(code)
//}