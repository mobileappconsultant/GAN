package com.android.breakingbad

import com.android.breakingbad.common.RoomRepository
import com.android.breakingbad.domain.AppSchedulerProvider
import com.android.breakingbad.domain.SchedulerProvider
import com.android.breakingbad.domain.database.BreakingBadDao
import com.android.breakingbad.domain.mapper.BreakingBadResponseModelToRoomItemMapper
import com.android.breakingbad.domain.mapper.RoomResponseToHospitalItemMapper
import com.android.breakingbad.domain.model.BreakingBadDataItem
import com.android.breakingbad.domain.model.BreakingBadPayLoad
import com.android.breakingbad.domain.remote.BreakingBadApiQueryService
import com.android.breakingbad.domain.repository.BreakingItemsQueryRepository
import com.google.gson.*
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import javax.inject.Named
import javax.inject.Singleton

@Module
class TestBreakingBadApiModule(var application: BreakingBadTestApplication) {
    @Provides
    @Singleton
    fun providesMockWebServer(): MockWebServer {
        var mockWebServer: MockWebServer? = null
        val thread = Thread({
            mockWebServer = MockWebServer()
            mockWebServer?.dispatcher = MockServerDispatcher(application).RequestDispatcher()
        })

        thread.start()
        thread.join()

        return mockWebServer ?: throw NullPointerException()
    }

    @Provides
    @Singleton
    @Named("baseUrl")
    fun provideBaseUrl(mockWebServer: MockWebServer): String {
        var url = ""
        val t = Thread({
            url = mockWebServer.url("/").toString()
        })
        t.start()
        t.join()

        return url
    }

    @Provides
    @Singleton
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                level = HttpLoggingInterceptor.Level.BODY
            } else {
                level = HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Provides
    @Singleton
    fun providesTestApiClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @Named("BreakingBad")
    fun provideBreakingbadRetrofitClient(
        @Named("baseUrl") baseUrl: String,
        okHttpClient: OkHttpClient
    ): Retrofit {
        val gsonBuilder = GsonBuilder().setPrettyPrinting().serializeNulls()
            .registerTypeAdapter(BreakingBadPayLoad::class.java, BreakingBadCustomDeserializer())
            .create()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(
                GsonConverterFactory.create(
                    gsonBuilder
                )
            )
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }


    @Provides
    @Singleton
    fun providesBreakingBadRetrofit(@Named("BreakingBad") retrofit: Retrofit): BreakingBadApiQueryService {
        return retrofit.create(BreakingBadApiQueryService::class.java)
    }

    @Provides
    @Singleton
    fun provideBreakingBadReponseMapper() = BreakingBadResponseModelToRoomItemMapper()


    @Provides
    @Singleton
    fun provideRoomResponseMapper() = RoomResponseToHospitalItemMapper()

    @Provides
    @Singleton
    fun providesTaskRepository(
        breakingBadDao: BreakingBadDao,
        breakingBadResponseModelToRoomItemMapper: BreakingBadResponseModelToRoomItemMapper
    ) = RoomRepository(breakingBadDao, breakingBadResponseModelToRoomItemMapper)

    @Provides
    @Singleton
    fun provideAppSchedulers(): SchedulerProvider = AppSchedulerProvider()

    @Provides
    @Singleton
    fun providesBreakingItemsQueryRepository(
        breakingBadApiQueryService: BreakingBadApiQueryService,
        roomRepository: RoomRepository
    ) = BreakingItemsQueryRepository(breakingBadApiQueryService, roomRepository)

    class BreakingBadCustomDeserializer : JsonDeserializer<BreakingBadPayLoad> {
        var gson = Gson()
        var jObject = JsonObject()
        var jsonArray: JsonArray = JsonArray()
        var breakingBadPayLoad = BreakingBadPayLoad()
        override fun deserialize(
            jElement: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): BreakingBadPayLoad? {
            if (jElement is JsonObject) {
                jObject = jElement.getAsJsonObject()
            } else if (jElement is JsonArray) {
                jsonArray = jElement.getAsJsonArray()
                breakingBadPayLoad.list = jsonArray.map {
                    gson.fromJson(
                        it?.asJsonObject,
                        BreakingBadDataItem::class.java
                    )
                }.toMutableList()
            }
            return breakingBadPayLoad
        }
    }
}
