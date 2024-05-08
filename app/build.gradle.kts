plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.boycott_food"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.boycott_food"
        minSdk = 25
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
    // ZXing dependencies

    implementation ("com.google.zxing:core:3.4.1")
    implementation ("com.journeyapps:zxing-android-embedded:4.2.0") // Cette dépendance est pour l'intégration Android
    implementation ("com.google.zxing:core:3.4.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    //implementation("com.google.firebase:firebase-analytics:22.0.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    // D'autres dépendances de votre application...
    implementation ("com.squareup.okhttp3:okhttp:4.9.0")
    implementation ("com.squareup.picasso:picasso:2.71828")
    //implementation(platform("com.google.firebase:firebase-bom:32.8.1"))

    //implementation ("com.google.firebase:firebase-database:20.3.1")


    // Import the BoM for the Firebase platform
    //implementation(platform("com.google.firebase:firebase-bom:32.8.0"))

    // Add the dependency for the Realtime Database library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    //implementation("com.google.firebase:firebase-database")


}