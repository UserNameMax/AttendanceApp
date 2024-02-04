package ru.omgtu.ivt213.mishenko.maksim.attendance.api.omgtuSchedule

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.appendPathSegments
import ru.omgtu.ivt213.mishenko.maksim.attendance.api.omgtuSchedule.model.ScheduleResponse
import ru.omgtu.ivt213.mishenko.maksim.attendance.api.omgtuSchedule.model.SearchResponse
import java.text.SimpleDateFormat
import java.util.Calendar

class OmgtuScheduleApiImpl(private val baseUrl: String, private val client: HttpClient) : OmgtuScheduleApi {
//    private val baseUrl = BuildConfig.omgtuScheduleUrl
//    private val client = HttpClient(CIO) {
//        install(ContentNegotiation) {
//            json()
//        }
//        install(Logging) {
//            logger = object : Logger {
//                override fun log(message: String) {
//                    Log.i("ktorClient", message)
//                }
//            }
//            level = LogLevel.HEADERS
//        }
//    }

    override suspend fun search(term: String, type: String): List<SearchResponse> =
        client.get("$baseUrl/search") {
            parameter("term", term)
            parameter("type", type)
        }.body()

    override suspend fun schedule(
        id: String,
        type: String,
        start: String,
        finish: String
    ): List<ScheduleResponse> =
        client.get("$baseUrl/schedule") {
            url {
                appendPathSegments(type, id)
            }
            parameter("start", start)
            parameter("finish", finish)
        }.body()
}