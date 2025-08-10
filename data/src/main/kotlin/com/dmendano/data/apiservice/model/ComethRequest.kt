package com.dmendano.data.apiservice.model

data class ComethRequest(
    val candidateId: String,
    val direction: String,
    val row: Int,
    val column: Int,
)
