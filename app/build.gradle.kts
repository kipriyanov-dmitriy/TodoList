plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.example.todo"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.todo"
        minSdk = 28
        targetSdk = 36
        versionCode = 11
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            val keystorePath = System.getenv("RELEASE_KEYSTORE_PATH")
            val envStorePassword = System.getenv("SIGNING_STORE_PASSWORD")
            val envKeyAlias = System.getenv("SIGNING_KEY_ALIAS")
            val envKeyPassword = System.getenv("SIGNING_KEY_PASSWORD")
            
            println("=== SIGNING CONFIG DEBUG ===")
            println("RELEASE_KEYSTORE_PATH: $keystorePath")
            println("SIGNING_STORE_PASSWORD: ${if (envStorePassword != null) "[SET]" else "[NOT SET]"}")
            println("SIGNING_KEY_ALIAS: $envKeyAlias")
            println("SIGNING_KEY_PASSWORD: ${if (envKeyPassword != null) "[SET]" else "[NOT SET]"}")
            
            if (keystorePath != null && envStorePassword != null && envKeyAlias != null && envKeyPassword != null) {
                val keystoreFile = file(keystorePath)
                println("Using CI signing config with keystore: $keystorePath")
                println("Keystore file exists: ${keystoreFile.exists()}")
                println("Keystore file absolute path: ${keystoreFile.absolutePath}")
                storeFile = keystoreFile
                storePassword = envStorePassword
                keyAlias = envKeyAlias
                keyPassword = envKeyPassword
            } else {
                println("Using local signing config")
                val localKeystore = file("my-release-key.jks")
                println("Local keystore exists: ${localKeystore.exists()}")
                println("Local keystore absolute path: ${localKeystore.absolutePath}")
                storeFile = localKeystore
                storePassword = "local_password"
                keyAlias = "local_alias"
                keyPassword = "local_password"
            }
            println("=== END SIGNING CONFIG DEBUG ===")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
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
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    //DB
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    implementation(libs.room.paging)
    ksp(libs.room.compiler)

    //DI
    implementation(libs.koin.core)
    implementation(libs.koin.compose)
    implementation(libs.koin.compose.viewmodel)
    implementation(libs.koin.android)

    //tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}