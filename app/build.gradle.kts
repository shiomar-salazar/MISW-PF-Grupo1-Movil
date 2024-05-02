plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.gms.google-services")
    id ("androidx.navigation.safeargs")
    id ("kotlin-android")
    id ("kotlin-parcelize")
    id("jacoco")
}
apply{
    from("../jacoco.gradle")
}

jacoco {
    toolVersion = "0.8.8"
}

android {
    namespace = "com.sportapp_grupo1"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.sportapp_grupo1"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        testOptions {
            execution = "ANDROIDX_TEST_ORCHESTRATOR"
            unitTests {
                isIncludeAndroidResources = true
                unitTests.isReturnDefaultValues = true
            }
        }

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            enableAndroidTestCoverage = true
            enableUnitTestCoverage = true
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
            enableAndroidTestCoverage = true
            enableUnitTestCoverage = true
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
        viewBinding = true
        dataBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.volley)
    implementation(libs.androidx.junit.ktx)
    implementation(libs.androidx.media3.common)
    implementation(libs.androidx.uiautomator)
    implementation(libs.androidx.rules)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    val navversion = "2.7.7"
    implementation("androidx.navigation:navigation-fragment-ktx:$navversion")
    implementation("androidx.navigation:navigation-ui-ktx:$navversion")
    implementation("androidx.navigation:navigation-dynamic-features-fragment:$navversion")
    androidTestImplementation("androidx.navigation:navigation-testing:$navversion")
    implementation("androidx.navigation:navigation-compose:$navversion")

    implementation (libs.androidx.lifecycle.livedata.ktx)
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    implementation(platform("com.google.firebase:firebase-bom:32.8.0"))
    androidTestImplementation ("androidx.test:runner:1.5.2")
    androidTestUtil ("androidx.test:orchestrator:1.4.2")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.1")
    implementation ("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("androidx.test.espresso:espresso-contrib:3.5.1")
    implementation (libs.play.services.location)
    androidTestImplementation(libs.androidx.rules)
}
