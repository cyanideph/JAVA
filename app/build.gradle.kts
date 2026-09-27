import java.net.HttpURLConnection
import java.net.URI

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.cyanideph.java"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.cyanideph.java"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "0.1.0"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
}

dependencies {
    implementation(platform("androidx.compose:compose-bom:2026.08.00"))
    implementation("androidx.activity:activity-compose:1.12.0")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.navigation:navigation-compose:2.9.5")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.10.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.10.0")
    debugImplementation("androidx.compose.ui:ui-tooling")
}


val legacyAssetsManifest = rootProject.file("legacy-assets.txt")
val legacyAssetsSourceRef = "4645532460a865a6196ab04091a46f0d33d381e7"
val syncLegacyAssets by tasks.registering {
    outputs.dir(layout.projectDirectory.dir("app/src/main/assets/legacy"))
    doLast {
        val destination = layout.projectDirectory.dir("app/src/main/assets/legacy").asFile
        destination.mkdirs()
        legacyAssetsManifest.readLines().map(String::trim)
            .filter { it.isNotEmpty() && !it.startsWith("#") }
            .forEach { relative ->
                val out = destination.resolve(relative)
                out.parentFile.mkdirs()
                if (out.exists() && out.length() > 0) return@forEach
                val connection = URI("https://raw.githubusercontent.com/cyanideph/javauzzap/$legacyAssetsSourceRef/$relative").toURL().openConnection() as HttpURLConnection
                connection.connectTimeout = 20000
                connection.readTimeout = 60000
                connection.inputStream.use { input -> out.outputStream().use { output -> input.copyTo(output) } }
                connection.disconnect()
            }
    }
}
tasks.named("preBuild") { dependsOn(syncLegacyAssets) }
