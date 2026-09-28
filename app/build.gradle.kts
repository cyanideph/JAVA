import java.net.HttpURLConnection
import java.net.URI
import java.util.zip.ZipInputStream
import java.security.MessageDigest

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
    implementation(platform("androidx.compose:compose-bom:2025.10.01"))
    implementation("androidx.activity:activity-compose:1.11.0")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.navigation:navigation-compose:2.9.5")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.10.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.10.0")
    debugImplementation("androidx.compose.ui:ui-tooling")
}


val legacyAssetsManifest = rootProject.file("legacy-assets.txt")
val legacyAssetsIntegrity = rootProject.file("legacy-assets-integrity.txt")
val legacyAssetsSourceRef = "4645532460a865a6196ab04091a46f0d33d381e7"

fun gitBlobSha1(bytes: ByteArray): String {
    val header = "blob ${bytes.size}\u0000".toByteArray(Charsets.UTF_8)
    val digest = MessageDigest.getInstance("SHA-1")
    return digest.digest(header + bytes).joinToString("") { "%02x".format(it) }
}

val syncLegacyAssets by tasks.registering {
    outputs.dir(layout.projectDirectory.dir("src/main/assets/legacy"))
    doLast {
        val destination = layout.projectDirectory.dir("src/main/assets/legacy").asFile
        destination.mkdirs()

        val expected = legacyAssetsIntegrity.readLines()
            .map(String::trim)
            .filter { it.isNotEmpty() && !it.startsWith("#") }
            .associate {
                val parts = it.split("  ", limit = 2)
                require(parts.size == 2) { "Malformed legacy asset integrity entry: $it" }
                parts[1] to parts[0]
            }

        val required = legacyAssetsManifest.readLines()
            .map(String::trim)
            .filter { it.isNotEmpty() && !it.startsWith("#") }

        val missing = required.filter { relative ->
            val out = destination.resolve(relative)
            !out.exists() || gitBlobSha1(out.readBytes()) != expected[relative]
        }
        if (missing.isNotEmpty()) {
            val sourceDir = System.getenv("LEGACY_SOURCE_DIR")?.let(::File)
            require(sourceDir != null && sourceDir.isDirectory) {
                "LEGACY_SOURCE_DIR is not available; pinned legacy source checkout is required"
            }
            missing.forEach { relative ->
                val source = sourceDir!!.resolve(relative)
                require(source.isFile) { "Missing legacy source asset: $relative" }
                val out = destination.resolve(relative)
                out.parentFile.mkdirs()
                val expectedSha = expected[relative] ?: error("Missing integrity fingerprint for legacy asset: $relative")
                val actualSha = gitBlobSha1(source.readBytes())
                require(actualSha == expectedSha) {
                    "Legacy asset integrity failure for $relative: expected $expectedSha but found $actualSha"
                }
                source.copyTo(out, overwrite = true)
            }
        }

        required.forEach { relative ->
            val out = destination.resolve(relative)
            val expectedSha = expected[relative] ?: error("Missing integrity fingerprint for legacy asset: $relative")
            require(out.exists()) { "Missing legacy asset after sync: $relative" }
            require(gitBlobSha1(out.readBytes()) == expectedSha) {
                "Legacy asset integrity failure for $relative"
            }
        }
    }
}
tasks.named("preBuild") { dependsOn(syncLegacyAssets) }
