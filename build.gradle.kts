import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

allprojects {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
    }
}

afterEvaluate {
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            apiVersion = "1.5"
            languageVersion = "1.5"
        }
    }
}
