plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")

}

android {
    namespace = "com.example.yte"
    compileSdk = 34

    kotlinOptions {
        freeCompilerArgs += "-Xopt-in=com.google.accompanist.pager.ExperimentalPagerApi"
    }

    defaultConfig {
        applicationId = "com.example.yte"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

//    implementation ("com.google.dagger:hilt-android:2.44")
//    kapt ("com.google.dagger:hilt-compiler:2.44")
//
//    // Hilt ViewModel
//    implementation ("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
//    kapt ("androidx.hilt:hilt-compiler:1.0.0")
//
//    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
//    implementation("com.google.dagger:hilt-android:2.44")
//    kapt("com.google.dagger:hilt-compiler:2.44")


    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-messaging")
    implementation ("com.google.firebase:firebase-inappmessaging")
    implementation ("com.google.firebase:firebase-inappmessaging-display")
    implementation ("com.google.firebase:firebase-auth-ktx")

    implementation("com.google.ai.client.generativeai:generativeai:0.9.0")

    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")


    implementation ("com.google.mlkit:barcode-scanning:17.0.3")
    implementation("com.google.android.gms:play-services-code-scanner:16.1.0")

    // Thêm các phụ thuộc khác nếu cần

    implementation ("com.google.accompanist:accompanist-permissions:0.30.1")

    implementation ("com.google.accompanist:accompanist-pager:0.25.1")
    implementation ("com.google.android.material:material:1.8.0")
    implementation ("androidx.compose.material:material:1.4.3")
    implementation ("androidx.compose.ui:ui:1.4.3")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.4.3")
    implementation ("androidx.compose.runtime:runtime-livedata:1.4.3")

    implementation ("com.google.accompanist:accompanist-pager:0.25.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")
    implementation("com.google.mlkit:barcode-scanning:17.0.2")
    implementation("com.journeyapps:zxing-android-embedded:4.3.0")
    implementation("com.google.zxing:core:3.4.1")

    implementation("com.squareup.okhttp3:okhttp:4.6.0")
    implementation("commons-codec:commons-codec:1.14")
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation(fileTree(mapOf(
        "dir" to "D:\\ZaloPay_lib",
        "include" to listOf("*.aar", "*.jar"),
        "exclude" to listOf("")
    )))
    implementation(libs.play.services.maps)
    implementation(libs.firebase.auth)


    val nav_version = "2.7.5"
    val compose_version = "1.6.0-alpha08"
    val room = "2.6.0"

    implementation("androidx.room:room-runtime:$room")
    implementation("androidx.room:room-ktx:$room")
    kapt("androidx.room:room-compiler:$room")

    implementation("androidx.navigation:navigation-compose:$nav_version")
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.compose.ui:ui:$compose_version")
    implementation("androidx.compose.material:material:$compose_version")
    implementation("androidx.compose.ui:ui-tooling-preview:$compose_version")

    implementation( "org.jetbrains.kotlin:kotlin-stdlib:1.9.10")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.0")
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")


    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

    //Network calls
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    //Json to Kotlin object mapping
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //Image loading
    implementation("io.coil-kt:coil-compose:2.4.0")

    //Thêm thành phần, cung cấp thành phần giao diện
   implementation("androidx.compose.material3:material3:1.2.0-rc01")

//    implementation("androidx.navigation:navigation-compose:2.7.4")

//    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation ("com.google.accompanist:accompanist-pager:0.23.1")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}