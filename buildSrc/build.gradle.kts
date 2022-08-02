plugins {
    `kotlin-dsl`
}

initDeps(project)

repositories {
    mavenLocal()
    google()
    mavenCentral()
}

dependencies {
    implementation(Deps.JetBrains.Kotlin.gradlePlugin)
    implementation(Deps.JetBrains.Kotlin.serializationPlugin)
    implementation(Deps.Android.Tools.Build.gradlePlugin)
    implementation(Deps.Squareup.SQLDelight.gradlePlugin)
}

kotlin {
    // Add Deps to compilation, so it will become available in main project
    sourceSets.getByName("main").kotlin.srcDir("buildSrc/src/main/kotlin")
}