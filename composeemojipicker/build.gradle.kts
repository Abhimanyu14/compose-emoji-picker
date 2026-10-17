import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("com.android.library")
    id("maven-publish")
    id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.18.2"
    id("org.jetbrains.kotlin.plugin.compose")
}

kotlin {
    explicitApi()

    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

android {
    namespace = "com.makeappssimple.abhimanyu.composeemojipicker"
    compileSdk = 37

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

dependencies {
    implementation("androidx.compose.ui:ui:1.12.1")
    implementation("androidx.compose.material3:material3:1.4.0")
    implementation("androidx.compose.material:material-icons-core:1.7.8")
    implementation("androidx.emoji2:emoji2:1.7.0")
    implementation("com.github.Abhimanyu14:emoji-core:1.0.15")
}

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "com.github.Abhimanyu14"
            artifactId = "compose-emoji-picker"
            version = "1.0.0-alpha21"
            afterEvaluate {
                from(components["release"])
            }
        }
    }
}
