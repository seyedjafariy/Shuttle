plugins {
    id("com.android.application")
    kotlin("android")
}
initDeps(project)
android {
    compileSdk = 32

    defaultConfig {
        minSdk = 23
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        applicationId = "com.seyedjafariy.shuttle"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    packagingOptions {
        exclude("META-INF/*")
    }

    buildFeatures {
        compose =  true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.1.1"
    }
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

}

dependencies {
    implementation(project(":shared"))

    implementation(Deps.AndroidX.Compose.activity)
    implementation(Deps.AndroidX.Compose.ui)
    implementation(Deps.AndroidX.Compose.foundation)
    implementation(Deps.AndroidX.Compose.material)
    implementation(Deps.AndroidX.Compose.iconsExt)
    implementation(Deps.AndroidX.Compose.tooling)
    debugImplementation(Deps.AndroidX.Compose.debugManifest)
    implementation(Deps.Coil.composeCoil)

    implementation(Deps.ArkIvanov.MVIKotlin.mvikotlin)
    implementation(Deps.ArkIvanov.MVIKotlin.mvikotlinMain)
    implementation(Deps.ArkIvanov.MVIKotlin.mvikotlinLogging)
    implementation(Deps.ArkIvanov.MVIKotlin.mvikotlinTimeTravel)

    implementation(Deps.ArkIvanov.Decompose.decompose)
    implementation(Deps.ArkIvanov.Decompose.extensionsCompose)

    implementation(Deps.AndroidX.AppCompat.appCompat)
    implementation(Deps.AndroidX.Activity.activityCompose)

    implementation(Deps.Squareup.SQLDelight.androidDriver)
    implementation(Deps.Squareup.okhttp)
    implementation(Deps.Squareup.loggingInterceptor)

    implementation(Deps.Koin.android)
    implementation(Deps.Koin.compose)

    implementation(Deps.Ktor.okHttp)
    implementation(Deps.Ktor.common)

    implementation(Deps.JetBrains.Kotlin.datetime)

    androidTestImplementation(Deps.AndroidX.Compose.composeTestRule)
}
