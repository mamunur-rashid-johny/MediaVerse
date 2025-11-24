import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}


//for extracting local properties variables
val properties = Properties()
val propertiesLocalFile = rootProject.file("local.properties")
if (propertiesLocalFile.exists()){
    properties.load(propertiesLocalFile.inputStream())
}


android {
    namespace = "com.johny.mediaverse"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.johny.mediaverse"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
        multiDexEnabled = true

        //Code to Fetch Keys or Other Credentials
        val movieDbApiKey = properties.getProperty("MOVIE_DB_API_KEY", "")
        val listenNoteApiKey = properties.getProperty("LISTEN_NOTE_API_KEY","")
        buildConfigField("String", "MOVIE_DB_API_KEY", "\"$movieDbApiKey\"")
        buildConfigField("String", "LISTEN_NOTE_API_KEY", "\"$listenNoteApiKey\"")
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
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs += "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
}

dependencies {
    //<editor-fold desc = "Android Core, ViewModel, Life-Cycle">
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)
    debugImplementation(libs.bundles.compose.debug)
    //</editor-fold>

    //<editor-fold desc = "Desuger for backward compatibility">
    coreLibraryDesugaring(libs.desugar.jdk.libs)
    //</editor-fold>

    //<editor-fold desc = "Koin">
    implementation(libs.bundles.koin)
    //</editor-fold>

    //<editor-fold desc = "ktor">
    implementation(libs.bundles.ktor)
    //</editor-fold>

    //<editor-fold desc = "Testing Related">
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    //</editor-fold>

    //<editor-fold desc = "Google Font API">
    implementation(libs.compose.ui.google.fonts)
    //</editor-fold>

    //<editor-fold desc = "Compose Navigation">
    implementation(libs.androidx.navigation.compose)
    //</editor-fold>

    //<editor-fold desc = "Splash API">
    implementation(libs.androidx.core.splashscreen)
    //</editor-fold>

    //<editor-fold desc = "Pagination">
    implementation(libs.bundles.pagination)
    //</editor-fold>

    //<editor-fold desc = "Image Loader">
    implementation(libs.skydoves.landscapist.coil)
    //</editor-fold>

    //<editor-fold desc = "Exo Player">
    implementation(libs.bundles.exo.player)
    //</editor-fold>

}