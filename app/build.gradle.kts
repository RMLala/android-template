import java.util.Base64

plugins {
    id("com.android.application")
}

android {
    namespace = "mg.rmlala.template"
    compileSdk = 35

    defaultConfig {
        applicationId = "mg.rmlala.template"
        minSdk = 23
        targetSdk = 34
        versionCode = providers.gradleProperty("releaseVersionCode").orNull?.toIntOrNull() ?: 1
        versionName = providers.gradleProperty("appVersionName").orNull ?: "1.0"
    }

    flavorDimensions += "compatibility"
    productFlavors {
        create("api23") {
            dimension = "compatibility"
            minSdk = 23
            versionCode = defaultConfig.versionCode?.times(10)?.plus(23)
        }
        create("api29") {
            dimension = "compatibility"
            minSdk = 29
            versionCode = defaultConfig.versionCode?.times(10)?.plus(29)
        }
    }

    signingConfigs {
        create("releaseCI") {
            val b64 = System.getenv("KEYSTORE_BASE64")
            val aliasValue = System.getenv("KEY_ALIAS")
            val storePass = System.getenv("KEYSTORE_PASSWORD")
            val keyPass = System.getenv("KEY_PASSWORD")
            if (!b64.isNullOrBlank() && !aliasValue.isNullOrBlank() &&
                !storePass.isNullOrBlank() && !keyPass.isNullOrBlank()) {
                val store = layout.buildDirectory.file("secure/release.jks").get().asFile
                store.parentFile.mkdirs()
                store.writeBytes(Base64.getMimeDecoder().decode(b64))
                storeFile = store
                storePassword = storePass
                keyAlias = aliasValue
                keyPassword = keyPass
            }
        }
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
            applicationIdSuffix = ".debug"
        }
        getByName("release") {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("releaseCI")
        }
    }

    buildFeatures {
        viewBinding = false
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

tasks.register("verifyReleaseCredentials") {
    doLast {
        val missing = listOf("KEYSTORE_BASE64","KEY_ALIAS","KEYSTORE_PASSWORD","KEY_PASSWORD")
            .filter { System.getenv(it).isNullOrBlank() }
        check(missing.isEmpty()) { "Missing release signing secrets: ${missing.joinToString()}" }
    }
}

tasks.matching { it.name in listOf("packageApi23Release", "packageApi29Release") }.configureEach {
    dependsOn("verifyReleaseCredentials")
}
