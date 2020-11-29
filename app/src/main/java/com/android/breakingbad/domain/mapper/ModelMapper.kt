package com.android.breakingbad.domain.mapper

interface ModelMapper<in M, out E> {
    fun mapFromModel(model: M): E
}
