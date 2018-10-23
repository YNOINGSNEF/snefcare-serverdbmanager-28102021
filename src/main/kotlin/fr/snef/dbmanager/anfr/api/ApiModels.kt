package fr.snef.dbmanager.anfr.api

import java.text.SimpleDateFormat
import java.util.*

data class GouvDataResponse(
        val resources: List<DatasetResourceResponse>
) {
//    val lastModified: Date get() = SimpleDateFormat("yyyy-MM-dd").parse(last_modified.take(10))
//    val lastModified: Date get() = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(last_modified.take(23))
}

data class DatasetResourceResponse(
        val checksum: ChecksumResponse,
        val mime: String,
        private val last_modified: String,
        val url: String,
        val title: String
) {
    val lastModifiedDate: Date get() = SimpleDateFormat("yyyy-MM-dd").parse(last_modified.take(10))
    val lastModifiedDateTime: Date get() = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(last_modified.take(23))
}

data class ChecksumResponse(
        val type: String,
        val value: String
)