package com.android.breakingbad.domain.remote

import com.android.breakingbad.domain.model.BreakingBadPayLoad
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET

interface BreakingBadApiQueryService {
    @GET("/api/characters")
    fun retrieveBreakingBadCharacters(): Single<Response<BreakingBadPayLoad>>
}