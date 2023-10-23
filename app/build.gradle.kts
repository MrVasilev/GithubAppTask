@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.androidGradlePlugin)
    alias(libs.plugins.kotlinAndroidPlugin)
    kotlin("kapt")
    alias(libs.plugins.hiltPlugin)
}

android {
    namespace = "com.emerchantpay.githubapptask"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.emerchantpay.githubapptask"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildFeatures {
        dataBinding = true
        buildConfig = true
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

    testOptions {
        unitTests.isReturnDefaultValues = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.core)
    implementation(libs.appcompat)
    implementation(libs.lifecycle)
    implementation(libs.viewModel)
    implementation(libs.constraintLayout)
    implementation(libs.activityKtx)

    implementation(libs.coroutines)
    implementation(libs.coroutinesAndroid)

    implementation(libs.securityCrypto)

    implementation(libs.roomRuntime)
    implementation(libs.roomKotlin)
    annotationProcessor(libs.roomCompiler)
    kapt(libs.roomCompiler)

    implementation(libs.retrofit)
    implementation(libs.retrofitMoshiConverter)
    implementation(libs.okhttp)
    implementation(libs.okhttpLogInterceptor)
    implementation(libs.moshi)
    kapt(libs.moshiKotlinCodegen)

    implementation(libs.hilt)
    kapt(libs.hiltAndroidCompiler)

    testImplementation(libs.junit)
    testImplementation(libs.mockitoInline)
    testImplementation(libs.mockitoKotlin)
    testImplementation(libs.coroutinesTest)
    testImplementation(libs.turbine)

    androidTestImplementation(libs.andoridJunitTest)
    androidTestImplementation(libs.espresso)
}

kapt {
    correctErrorTypes = true
}