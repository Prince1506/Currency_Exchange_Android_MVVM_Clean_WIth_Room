import Libraries.lifecycleRuntime

plugins {
  // Application Specific Plugins
  id(BuildPlugins.androidApplication)
  id(BuildPlugins.kotlinAndroid)
  id(BuildPlugins.kotlinKapt)
  id(BuildPlugins.kotlinAndroidExtensions)

  // Internal Script plugins
  id(ScriptPlugins.variants)
  id(ScriptPlugins.quality)
  id(ScriptPlugins.compilation)
}

android {
  compileSdkVersion(AndroidSdk.compile)

  signingConfigs {

    create(AndroidClient.release) {
      keyAlias = AndroidClient.release
      keyPassword = AndroidClient.keyPassword
      storeFile = file(AndroidClient.keystore_path)
      storePassword = AndroidClient.keyPassword
    }
  }
  defaultConfig {
    minSdkVersion(AndroidSdk.min)
    targetSdkVersion(AndroidSdk.target)

    applicationId = AndroidClient.appId
    versionCode = AndroidClient.versionCode
    versionName = AndroidClient.versionName
    testInstrumentationRunner = AndroidClient.testRunner
    multiDexEnabled = true
  }

  sourceSets {
    map { it.java.srcDir("src/${it.name}/kotlin") }

    //TODO: Remove this when migrating the DI framework
    getByName("main") { java.srcDir("$buildDir/generated/source/kapt/main") }
  }
}

dependencies {
    //Compile time dependencies
  kapt(Libraries.lifecycleCompiler)
  implementation(Libraries.lifecycleRuntime)
  kapt(Libraries.daggerCompiler)
  compileOnly(Libraries.javaxAnnotation)
  compileOnly(Libraries.javaxInject)

  // Application dependencies
  implementation(Libraries.kotlinStdLib)
  implementation(Libraries.kotlinCoroutines)
  implementation(Libraries.kotlinCoroutinesAndroid)
  implementation(Libraries.appCompat)
  implementation(Libraries.ktxCore)
  implementation(Libraries.constraintLayout)
  implementation(Libraries.viewModel)
  implementation(Libraries.liveData)
  implementation(Libraries.lifecycleExtensions)
  implementation(Libraries.cardView)
  implementation(Libraries.recyclerView)
  implementation(Libraries.material)
  implementation(Libraries.androidAnnotations)
  implementation(Libraries.glide)
  implementation(Libraries.dagger)
  implementation(Libraries.retrofit)
  implementation(Libraries.okHttpLoggingInterceptor)
  implementation(Libraries.swipeRefresh)

  // Unit/Android tests dependencies
  testImplementation(TestLibraries.junit4)
  testImplementation(TestLibraries.mockk)
  testImplementation(TestLibraries.kluent)
  testImplementation(TestLibraries.robolectric)

  // Acceptance tests dependencies
  androidTestImplementation(TestLibraries.testRunner)
  androidTestImplementation(TestLibraries.espressoCore)
  androidTestImplementation(TestLibraries.testExtJunit)
  androidTestImplementation(TestLibraries.testRules)
  androidTestImplementation(TestLibraries.espressoIntents)

  // GSON
  implementation("com.google.code.gson:gson:2.8.5")

  // ROOM PERSISTENCE
  implementation(Libraries.roomRuntime)
  kapt(Libraries.roomCompiler)

  // Development dependencies
  debugImplementation(DevLibraries.leakCanary)

  //Multi Dex
  implementation ("androidx.multidex:multidex:2.0.0")
  androidTestImplementation("androidx.multidex:multidex-instrumentation:2.0.0") {
    exclude(group ="com.android.support", module="multidex")
  }

}