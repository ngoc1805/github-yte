//package com.example.yte.Otp
//
//import com.google.firebase.auth.FirebaseAuth
//import dagger.Binds
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.components.ViewModelComponent
//import dagger.hilt.components.SingletonComponent
//import javax.inject.Singleton
//
//
//@Module
//@InstallIn(SingletonComponent::class)
//object RepositoryModule {
//
//    @Provides
//    @Singleton
//    fun provideAuthRepository(
//        firebaseAuth: FirebaseAuth
//    ): AuthRepository {
//        return AuthRepositoryImpl(firebaseAuth)
//    }
//}
//
