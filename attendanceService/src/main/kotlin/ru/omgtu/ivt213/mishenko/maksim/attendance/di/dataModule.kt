package ru.omgtu.ivt213.mishenko.maksim.attendance.di

import io.ktor.client.*
import org.koin.dsl.module
import ru.omgtu.ivt213.mishenko.maksim.attendance.api.omgtuSchedule.OmgtuScheduleApi
import ru.omgtu.ivt213.mishenko.maksim.attendance.api.omgtuSchedule.OmgtuScheduleApiImpl
import ru.omgtu.ivt213.mishenko.maksim.attendance.utils.createSessionRetryContext
import ru.omgtu.ivt213.mishenko.maksim.attendance.utils.httpClient
import tech.ydb.table.SessionRetryContext

val dataModule = module {
    single<HttpClient> { httpClient() }
    single<OmgtuScheduleApi> { OmgtuScheduleApiImpl(baseUrl = "https://rasp.omgtu.ru/api", client = get()) }
    single<SessionRetryContext> {
        createSessionRetryContext(
            authorizedKeyJson = System.getenv("YDB_KEY_JSON"),
            connectionString = System.getenv("YDB_CONNECTION_STRING")
        )
    }
}