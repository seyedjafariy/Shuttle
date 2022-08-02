// We store Kotlin and Compose versions in gradle.properties to
// be able to override them on CI.
// You probably won't need this, so you can get rid of `project` in this file.
import org.gradle.api.Project

lateinit var properties: Map<String, *>

fun initDeps(project: Project) {
    properties = project.properties
}

object Deps {
    object JetBrains {
        object Kotlin {
            private val VERSION get() = properties["kotlin.version"]
            val gradlePlugin get() = "org.jetbrains.kotlin:kotlin-gradle-plugin:$VERSION"
            val testCommon get() = "org.jetbrains.kotlin:kotlin-test-common:$VERSION"
            val testJunit get() = "org.jetbrains.kotlin:kotlin-test-junit:$VERSION"
            val testJs get() = "org.jetbrains.kotlin:kotlin-test-js:$VERSION"
            val testAnnotationsCommon get() = "org.jetbrains.kotlin:kotlin-test-annotations-common:$VERSION"

            val coroutines get() = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0"
            val coroutinesTest get() = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0"

            val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2"
            val serializationPlugin = "org.jetbrains.kotlin:kotlin-serialization:$VERSION"

            val datetime = "org.jetbrains.kotlinx:kotlinx-datetime:0.3.2"
        }
    }

    object Android {
        object Tools {
            object Build {
                const val gradlePlugin = "com.android.tools.build:gradle:7.2.1"
            }
        }
    }

    object AndroidX {
        object AppCompat {
            const val appCompat = "androidx.appcompat:appcompat:1.4.1"
        }

        object Activity {
            const val activityCompose = "androidx.activity:activity-compose:1.4.0"
        }

        object Compose {
            private val VERSION get() = properties["compose.version"]

            val activity = "androidx.activity:activity-compose:1.5.1"
            val ui = "androidx.compose.ui:ui:${VERSION}"
            val foundation = "androidx.compose.foundation:foundation:$VERSION"
            val material = "androidx.compose.material:material:$VERSION"
            val iconsExt = "androidx.compose.material:material-icons-extended:$VERSION"
            val tooling = "androidx.compose.ui:ui-tooling:$VERSION"
        }
    }

    object Coil {
        val composeCoil = "io.coil-kt:coil-compose:2.1.0"
    }

    object ArkIvanov {
        object MVIKotlin {
            private const val VERSION = "3.0.0-beta01"
            const val rx = "com.arkivanov.mvikotlin:rx:$VERSION"
            const val mvikotlin = "com.arkivanov.mvikotlin:mvikotlin:$VERSION"
            const val mvikotlinMain = "com.arkivanov.mvikotlin:mvikotlin-main:$VERSION"
            const val mvikotlinLogging = "com.arkivanov.mvikotlin:mvikotlin-logging:$VERSION"
            const val mvikotlinTimeTravel = "com.arkivanov.mvikotlin:mvikotlin-timetravel:$VERSION"
            const val mvikotlinExtensionsReaktive =
                "com.arkivanov.mvikotlin:mvikotlin-extensions-reaktive:$VERSION"
            const val mvikotlinExtensionsCoroutines =
                "com.arkivanov.mvikotlin:mvikotlin-extensions-coroutines:$VERSION"
        }

        object Decompose {
            private const val VERSION = "0.6.0"
            const val decompose = "com.arkivanov.decompose:decompose:$VERSION"
            const val extensionsCompose =
                "com.arkivanov.decompose:extensions-compose-jetbrains:$VERSION"
        }

        object Essenty {
            private const val VERSION = "0.2.2"
            const val lifecycle = "com.arkivanov.essenty:lifecycle:$VERSION"
        }
    }

    object Squareup {
        object SQLDelight {
            private const val VERSION = "1.5.3"

            const val gradlePlugin = "com.squareup.sqldelight:gradle-plugin:$VERSION"
            const val androidDriver = "com.squareup.sqldelight:android-driver:$VERSION"
            const val sqliteDriver = "com.squareup.sqldelight:sqlite-driver:$VERSION"
            const val nativeDriver = "com.squareup.sqldelight:native-driver:$VERSION"
            const val sqljsDriver = "com.squareup.sqldelight:sqljs-driver:$VERSION"
            const val coroutines = "com.squareup.sqldelight:coroutines-extensions:$VERSION"
        }

        val okhttp = "com.squareup.okhttp3:okhttp:4.9.3"
        val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:4.9.3"
    }

    object Koin {
        private const val VERSION = "3.2.0-beta-1"
        const val koin = "io.insert-koin:koin-core:$VERSION"
        const val test = "io.insert-koin:koin-test:$VERSION"
        const val android = "io.insert-koin:koin-android:$VERSION"
        const val compose = "io.insert-koin:koin-androidx-compose:$VERSION"
    }

    object Ktor {
        private const val VERSION = "1.6.8"

        val common = "io.ktor:ktor-client-core:${VERSION}"
        val jvm = "io.ktor:ktor-client-core-jvm:${VERSION}"
        val websocket = "io.ktor:ktor-client-websockets:${VERSION}"

        val okHttp = "io.ktor:ktor-client-okhttp:${VERSION}"
        val ios = "io.ktor:ktor-client-ios:${VERSION}"
        val native = "io.ktor:ktor-client-core-native:${VERSION}"

        object Json {
            val common = "io.ktor:ktor-client-json:${VERSION}"
            val jvm = "io.ktor:ktor-client-json-jvm:${VERSION}"
            val ios = "io.ktor:ktor-client-json-native:${VERSION}"
            val js = "io.ktor:ktor-client-json-js:${VERSION}"
        }

        object Serialization {
            val common = "io.ktor:ktor-client-serialization:${VERSION}"
            val jvm = "io.ktor:ktor-client-serialization-jvm:${VERSION}"
            val native = "io.ktor:ktor-client-serialization-native:${VERSION}"
        }

        object Logger {
            val common = "io.ktor:ktor-client-logging:${VERSION}"
            val jvm = "io.ktor:ktor-client-logging-jvm:${VERSION}"
            val native = "io.ktor:ktor-client-logging-native:${VERSION}"
            val js = "io.ktor:ktor-client-logging-js:${VERSION}"
        }
    }

    object Touchlab {
        val kermit = "co.touchlab:kermit:1.1.3"
    }
}
