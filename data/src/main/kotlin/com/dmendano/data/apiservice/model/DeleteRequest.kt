package com.dmendano.data.apiservice.model

data class DeleteRequest(
    val candidateId: String,
    val row: Int,
    val column: Int
)
