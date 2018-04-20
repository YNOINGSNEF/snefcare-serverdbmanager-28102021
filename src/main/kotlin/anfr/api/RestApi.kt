package anfr.api

import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File

object RestApi {
    private val gouvDataApi: GouvDataApi = Retrofit.Builder()
            .baseUrl("https://www.data.gouv.fr/api/1/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(GouvDataApi::class.java)

    fun getAnfrResources(): GouvDataResponse {
        val response = gouvDataApi.getDatasetInfo("551d4ff3c751df55da0cd89f").execute()

        if (response.isSuccessful) {
            return response.body()
        } else {
            throw RuntimeException(response.message())
        }
    }

    fun downloadResourceFile(resource: DatasetResourceResponse, dest: File) {
        val response = gouvDataApi.downloadResourceFile(resource.url).execute()

        if (response.isSuccessful) {
            writeToFile(response.body(), dest)
        } else {
            throw RuntimeException(response.message())
        }
    }

    private fun writeToFile(body: ResponseBody, dest: File) {
        body.byteStream().use { input ->
            dest.outputStream().use { input.copyTo(it) }
        }
    }
}