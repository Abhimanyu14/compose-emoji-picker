plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
    id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.15.0-Beta.1"
}

kotlin {
    explicitApi()
}

android {
    namespace = "com.makeappssimple.abhimanyu.composeemojipicker"
    compileSdk = 34

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

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.compose.ui:ui:1.6.4")
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("androidx.emoji2:emoji2:1.4.0")
    implementation("com.github.Abhimanyu14:emoji-core:1.0.12")
}


publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "com.github.Abhimanyu14"
            artifactId = "compose-emoji-picker"
            version = "1.0.0-alpha14"
            afterEvaluate {
                from(components["release"])
            }
        }
    }
}
