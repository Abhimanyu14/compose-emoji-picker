plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
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
        kotlinCompilerExtensionVersion = "1.4.3"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.compose.ui:ui:1.6.0-alpha03")
    implementation("androidx.compose.material3:material3:1.2.0-alpha05")
    implementation("androidx.emoji2:emoji2:1.4.0")
    implementation("com.github.Abhimanyu14:emoji-core:1.0.4")
}


publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "com.github.Abhimanyu14"
            artifactId = "compose-emoji-picker"
            version = "1.0.0-alpha09"
            afterEvaluate {
                from(components["release"])
            }
        }
    }
}
