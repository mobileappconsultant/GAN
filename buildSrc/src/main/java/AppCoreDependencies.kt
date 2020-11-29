import org.gradle.api.artifacts.dsl.DependencyHandler

object AppCoreDependencies {
    //std lib
    private val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"

    //android ui
    private val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    private val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    private val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerview_version}"
    private val cardview = "androidx.cardview:cardview:${Versions.card_view_version}"
    private val material = "com.google.android.material:material:${Versions.material_version}"
    private val navigation =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigation_ui_ktx_version}"
    private val navigation_ui =
        "androidx.navigation:navigation-ui-ktx:${Versions.navigation_ui_ktx_version}"
    private val paging_version = "androidx.paging:paging-runtime:2.1.2"
    private val rxjava_paging_version = "androidx.paging:paging-rxjava3:3.0.0-alpha09"

    //Support libraries
    private val dagger = "com.google.dagger:dagger:${Versions.dagger2_version}"
    private val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit_version}"
    private val retrofit_moshi_converter =
        "com.squareup.retrofit2:converter-moshi:${Versions.retrofit_version}"
    private val retrofit_gson_converter =
        "com.squareup.retrofit2:converter-gson:${Versions.retrofit_version}"
    private val retrofit_rxjava_adapter =
        "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit_version}"
    private val rxjava_retrofitAdapter3 =
        "com.squareup.retrofit2:adapter-rxjava3:${Versions.retrofit_version}"


    /**
     *     // RxJava
    implementation 'io.reactivex.rxjava2:rxjava:2.2.12'
    implementation 'io.reactivex.rxjava2:rxandroid:2.1.1'


    implementation "com.squareup.retrofit2:adapter-rxjava2:${retrofitVersion}"

    implementation "com.squareup.okhttp3:logging-interceptor:$okhttpVersion"
     */
    private val okhttp_logging =
        "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp_version}"
    private val moshi = "com.squareup.moshi:moshi:${Versions.moshi_version}"
    private val moshi_kotlin = "com.squareup.moshi:moshi-kotlin:${Versions.moshi_version}"
    private val coroutines_kotlin =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlin_coroutines_version}"
    private val coroutines_android =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlin_coroutines_version}"
    private val retrofit_coroutines_adapter =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.retrofit_coroutines_adapter_version}"
    private val glide = "com.github.bumptech.glide:glide:${Versions.glide_version}"
    private val room = "androidx.room:room-runtime:${Versions.room_database_version}"
    private val rxjavaRoom = "androidx.room:room-rxjava3:2.3.0-alpha03"

    private val roomKtx = "androidx.room:room-ktx:${Versions.room_database_version}"


    private val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    private val gson = "com.google.code.gson:gson:${Versions.gson_version}"


    private val rxjava3 = "io.reactivex.rxjava3:rxjava:${Versions.rxjava3_version}"
    private val rxandroid = "io.reactivex.rxjava3:rxandroid:${Versions.rxandroid_version}"
    private val rxbinding = "com.jakewharton.rxbinding4:rxbinding:${Versions.rxbinding_version}"
    private val pullToRefresh =
        "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.pullToRefreshVersion}"
    private val rxjava2ToRxjava3Bridge = "com.github.akarnokd:rxjava3-bridge:3.0.0"
    private val material_spinner = "com.jaredrummler:material-spinner:1.2.5"
    val appLibraries = arrayListOf<String>().apply {
        add(kotlinStdLib)
        add(retrofit_rxjava_adapter)
        add(timber)
        add(gson)
        add(material_spinner)
        add(rxjava_retrofitAdapter3)
        add(coreKtx)
        add(retrofit_gson_converter)
        add(pullToRefresh)
        add(constraintLayout)
        add(recyclerview)
        add(cardview)
        add(material)
        add(navigation)
        add(navigation_ui)
        add(dagger)
        add(retrofit)
        add(paging_version)
        add(rxjava_paging_version)
        add(retrofit_moshi_converter)
        add(okhttp_logging)
        add(moshi)
        add(moshi_kotlin)
        add(coroutines_kotlin)
        add(coroutines_android)
        add(retrofit_coroutines_adapter)
        add(glide)
        add(rxjava2ToRxjava3Bridge)
        add(room)
        add(roomKtx)
        add(rxjavaRoom)

        add(rxjava3)
        add(rxandroid)
        add(rxbinding)
    }


    fun DependencyHandler.implementation(list: List<String>) {
        list.forEach { dependency ->
            add("implementation", dependency)
            println(dependency)
        }

    }

}