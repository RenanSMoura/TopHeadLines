import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import java.io.FileInputStream
import java.util.Properties


fun getPropertiesFile(path: String): Properties {
    val properties = Properties()
    properties.load(FileInputStream(file(path)))
    return properties
}

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.detekt)
    alias(libs.plugins.hilt.android)
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    namespace = "dev.moura.test.challenge.ctw"
    compileSdk = 34

    defaultConfig {
        applicationId = "dev.moura.test.challenge.ctw"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        manifestPlaceholders["hostName"] = "dev.moura.test.challenge.ctw"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {

        getByName("debug") {
            isMinifyEnabled = false

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            getPropertiesFile("./config/development.properties").forEach { key, value ->
                buildConfigField("String", key as String, value as String)
            }
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            getPropertiesFile("./config/production.properties").forEach { key, value ->
                buildConfigField("String", key as String, value as String)
            }
        }
    }
    flavorDimensions += "version"
    productFlavors {
        create("cnn") {
            dimension = "version"
            versionNameSuffix = "-cnn"
            buildConfigField("String", "SOURCE_TYPE", "\"${name}\"")
        }
        create("cbs-news") {
            dimension = "version"
            versionNameSuffix = "-cbs"
            buildConfigField("String", "SOURCE_TYPE", "\"${name}\"")
        }
    }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }

    packaging {
        resources {
            excludes.add("META-INF/*")
            excludes.add("META-INF/gradle/incremental.annotation.processors")
        }
    }


}

detekt {
    buildUponDefaultConfig = true
    allRules = false
    config.setFrom("$rootDir/config/detekt.yml")
    baseline = file("$rootDir/config/baseline.xml")
    autoCorrect = true
}

tasks.withType<Detekt>().configureEach {
    jvmTarget = "1.8"
}
tasks.withType<DetektCreateBaselineTask>().configureEach {
    jvmTarget = "1.8"
}

tasks.withType<Detekt>().configureEach {
    reports {
        html.required.set(true)
        html.outputLocation.set(file("$projectDir/build/reports/detekt/detekt.html"))
        sarif.required.set(false)
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.core.splashscreen)
    implementation(platform(libs.androidx.ui.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.material3)
    implementation(libs.hilt.android)
    implementation(libs.hilt.android.compiler)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.jakewharton.timber)
    implementation(libs.coil)
    implementation(libs.coil.compose)
    testImplementation(libs.junit)
    testImplementation(libs.androidx.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.ui.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    androidTestImplementation(libs.androidx.navigation.testing)
    androidTestImplementation(libs.mockk.android)

    detektPlugins(libs.detekt.formatting)

    kapt(libs.hilt.android.compiler)
    kapt(libs.hilt.compiler)
}
