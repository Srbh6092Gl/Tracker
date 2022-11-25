package com.srbh.track.model

data class Note(
    var id: Long,
    var sender: String,
    val topic : String,
    var description: String
)
