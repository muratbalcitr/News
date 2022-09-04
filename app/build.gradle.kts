import Dependencies.Project.data

plugins {
    // Application Specific Plugins
    id(Plugins.androidApplication)
    kotlin(Plugins.android)
    id(Plugins.kotlinAndroidExtensions)
    kotlin(Plugins.kapt)
    id(Plugins.hiltPlugin)
    id(Plugins.crashlytics)
    id(Plugins.googleServices)
  }

android {
    compileSdk = Configs.compileSdkVersion

    defaultConfig {
        applicationId = Configs.applicationId
        minSdk = Configs.minSdkVersion
        targetSdk = Configs.targetSdkVersion
        versionCode = Configs.versionCode
        versionName = Configs.versionName

        testInstrumentationRunner = Configs.testInstrumentationRunner
    }
    signingConfigs {
        register(Flavors.BuildTypes.RELEASE) {
            storeFile = file(KeyStore.storeFilePath)
            storePassword = KeyStore.storePassword
            keyAlias = KeyStore.keyAlias
            keyPassword = KeyStore.keyPassword
        }
    }

    buildTypes {

        getByName(Flavors.BuildTypes.DEBUG) {
            isTestCoverageEnabled = true
            isMinifyEnabled = false
            multiDexEnabled = true
            isDebuggable = true
            signingConfig = signingConfigs.getByName(Flavors.BuildTypes.RELEASE)
        }

        getByName(Flavors.BuildTypes.RELEASE) {
            isMinifyEnabled = true
            multiDexEnabled = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName(Flavors.BuildTypes.RELEASE)
        }
    }

    flavorDimensions.add(Flavors.FlavorDimensions.ENVIRONMENT)
    productFlavors {

        // dev
        create(Flavors.ProductFlavors.DEV) {
            applicationId = Configs.applicationId
            dimension = Flavors.FlavorDimensions.ENVIRONMENT
            versionNameSuffix = ""

            // BuildConfigField
            stringField(Field.SERVICE_URL to "https://newsapi.org/v2/")
            stringField(Field.PUBLIC_KEY to "a9db625ef9904a14ab5b38836b3f7d67")
         }

        // store
        create(Flavors.ProductFlavors.STORE) {
            dimension = Flavors.FlavorDimensions.ENVIRONMENT
            applicationIdSuffix = ""
            versionNameSuffix = ""
            // BuildConfigField
            stringField(Field.SERVICE_URL to "https://newsapi.org/v2/")
            stringField(Field.PUBLIC_KEY to "a9db625ef9904a14ab5b38836b3f7d67")
         }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    dataBinding {
        isEnabled = true
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
   /* hilt {
        enableAggregatingTask = true
    }*/

    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/LICENSE")
        exclude("META-INF/LICENSE.txt")
        exclude("META-INF/license.txt")
        exclude("META-INF/NOTICE")
        exclude("META-INF/NOTICE.txt")
        exclude("META-INF/notice.txt")
        exclude("META-INF/ASL2.0")
        exclude("META-INF/*.kotlin_module")
        exclude("META-INF/gradle/incremental.annotation.processors")
    }
}

dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))

    // Data Module
    implementation(data())

    // Kotlin
    implementation(Dependencies.Kotlin.kotlinStdLib)
    implementation(Dependencies.Kotlin.kotlinCoroutinesCore)
    implementation(Dependencies.Kotlin.kotlinCoroutinesAndroid)

    // Android
    implementation(Dependencies.Android.androidCore)
    implementation(Dependencies.Android.androidCoreKtx)
    implementation(Dependencies.Android.appCompat)
    implementation(Dependencies.Android.legacySupport)
    implementation(Dependencies.Android.multidex)
    implementation(Dependencies.Android.materialDesign)
    implementation(Dependencies.Android.fragment)
    implementation(Dependencies.Android.constraintLayout)
    implementation(Dependencies.Android.recyclerView)
    implementation(Dependencies.Android.cardView)
    // Coroutines
    implementation(Dependencies.Coroutines.coroutinesTest)
    implementation(Dependencies.Coroutines.kotlinCoroutinesAdapter)

    // Navigation
    implementation(Dependencies.Navigation.runTimeNavigation)
    implementation(Dependencies.Navigation.navigationFragment)
    implementation(Dependencies.Navigation.navigationUi)

    // LifeCycle
    implementation(Dependencies.LifeCycle.runTimeLiveCycle)
    implementation(Dependencies.LifeCycle.lifeCycleCompiler)
    implementation(Dependencies.LifeCycle.liveData)
    implementation(Dependencies.LifeCycle.viewModel)

    //Firebase
     implementation("com.google.firebase:firebase-bom:30.4.0")

    // Daager-Hilt
    implementation(Dependencies.DI.hilt)
    kapt(Dependencies.DI.hiltCompiler)

    // For instrumentation tests
    androidTestImplementation(Dependencies.DI.hiltAndroidTesting)
    kaptAndroidTest(Dependencies.DI.hiltCompiler)

    // For local unit tests
    testImplementation(Dependencies.DI.hiltAndroidTesting)
    kaptTest(Dependencies.DI.hiltCompiler)

    // DAO
    implementation(Dependencies.Room.roomRuntime)
    implementation(Dependencies.Room.roomKtx)
    kapt(Dependencies.Room.roomCompiler)

    //Firebase
    implementation(Dependencies.Firebase.firebaseAnalytics)
    implementation(Dependencies.Firebase.firebaseCrashlyticsKtx)
    implementation(Dependencies.Firebase.firebaseCrashlytics)

    // Network
    implementation(Dependencies.Network.gson)
    implementation(Dependencies.Network.gsonAdapter)
    implementation(Dependencies.Network.retrofit)
     implementation(Dependencies.Network.okHttp)
    implementation(Dependencies.Network.loggingInterceptor)

    // Coil
    implementation(Dependencies.Coil.coil)

    //Tools
    implementation(Dependencies.Tools.timber)
    implementation(Dependencies.Tools.roundedImageView)

    // Testing
    testImplementation(Dependencies.Test.junit)
    testImplementation(Dependencies.Test.truthExt)
    testImplementation(Dependencies.Test.mockK)
    testImplementation(Dependencies.Test.coreTesting)
    androidTestImplementation(Dependencies.Test.androidJunit)
    androidTestImplementation(Dependencies.Test.espressoCore)
}
fun com.android.build.api.dsl.ProductFlavor.stringField(entry: Pair<String, String>) {
    buildConfigField("String", entry.first, "\"${entry.second}\"")
}

fun com.android.build.api.dsl.ProductFlavor.manifestHolder(entry: Map<String, Any>) {
    addManifestPlaceholders(entry)
}
/*
kapt {
    correctErrorTypes = true
}
*/