plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compilerKsp)
    alias(libs.plugins.compilerHilt)
}

android {
    namespace = "com.example.jobsearch"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.jobsearch"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.swipe)
    implementation(libs.squareup.retrofit)
    implementation(libs.squareup.okhttp)
    implementation(libs.squareup.logging)
    implementation(libs.squareup.picasso)
    implementation(libs.squareup.converter)

    implementation(libs.dagger.hilt)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.activity)
    ksp(libs.dagger.compiler)

    implementation(libs.androidx.room)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.activity)
    ksp(libs.androidx.room.ksp)

    api(libs.jetbrains.coroutines.core)
    api(libs.jetbrains.coroutines.android)
    implementation(libs.jetbrains.kotlin)
    implementation(libs.androidx.recyclerview)

    implementation(libs.androidx.fragment)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}