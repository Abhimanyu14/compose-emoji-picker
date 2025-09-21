import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
    id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.18.1"
    id("org.jetbrains.kotlin.plugin.compose")
}

kotlin {
    explicitApi()

    @OptIn(org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation::class)
    abiValidation {
        // Use the set() function to ensure compatibility with older Gradle versions
        enabled.set(true)
    }

    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_1_8)
    }
}

android {
    namespace = "com.makeappssimple.abhimanyu.composeemojipicker"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation("androidx.compose.ui:ui:1.9.1")
    implementation("androidx.compose.material3:material3:1.3.2")
    implementation("androidx.emoji2:emoji2:1.6.0")
    implementation("com.github.Abhimanyu14:emoji-core:1.0.13")
}

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "com.github.Abhimanyu14"
            artifactId = "compose-emoji-picker"
            version = "1.0.0-alpha19"
            afterEvaluate {
                from(components["release"])
            }
        }
    }
}
