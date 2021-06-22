package fr.snef.dbmanager.anfr.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Streaming
import retrofit2.http.Url

interface GouvDataApi {
    @GET("datasets/{datasetId}")
    fun getDatasetInfo(@Path("datasetId") datasetId: String): Call<GouvDataResponse>

    @GET
    @Streaming
    fun downloadResourceFile(@Url fileUrl: String) : Call<ResponseBody>
}