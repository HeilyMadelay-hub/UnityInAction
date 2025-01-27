plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "org.meicode.handson"
    compileSdk = 35

    defaultConfig {
        applicationId = "org.meicode.handson"
        minSdk = 31
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("androidx.gridlayout:gridlayout:1.0.0")
    implementation ("androidx.appcompat:appcompat:1.3.1")
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}