plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.phillipthai.customerintake"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.phillipthai.customerintake"
        minSdk = 26
        targetSdk = 34
        versionCode = 3
        versionName = "1.02"

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
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)


    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    testImplementation("org.robolectric:robolectric:4.9")

    // Add Mockito for unit testing
    testImplementation ("org.mockito:mockito-core:5.4.0")

    // Optional: Add Mockito for Android instrumentation tests
    androidTestImplementation ("org.mockito:mockito-android:5.4.0")

}