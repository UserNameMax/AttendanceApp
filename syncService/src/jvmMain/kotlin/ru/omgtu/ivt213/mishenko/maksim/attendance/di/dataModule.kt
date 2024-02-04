package ru.omgtu.ivt213.mishenko.maksim.attendance.di

import io.ktor.client.*
import org.koin.dsl.module
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import ru.omgtu.ivt213.mishenko.maksim.attendance.api.omgtuCapitan.OmgtuCapitanApi
import ru.omgtu.ivt213.mishenko.maksim.attendance.api.omgtuCapitan.OmgtuCapitanApiImpl
import ru.omgtu.ivt213.mishenko.maksim.attendance.api.omgtuSchedule.OmgtuScheduleApi
import ru.omgtu.ivt213.mishenko.maksim.attendance.api.omgtuSchedule.OmgtuScheduleApiImpl
import ru.omgtu.ivt213.mishenko.maksim.attendance.utils.createSessionRetryContext
import ru.omgtu.ivt213.mishenko.maksim.attendance.utils.createWebDriver
import ru.omgtu.ivt213.mishenko.maksim.attendance.utils.httpClient
import tech.ydb.table.SessionRetryContext

val dataModule = module {
    single<HttpClient> { httpClient() }
    single<WebDriver> {
        createWebDriver(
            chromeDriverPath = "/home/max/Downloads/kotlin-wasm-examples/attendance/chromedriver",
            width = 1920 / 2 + 400,
            height = 1080 - 300
        )
    }
    single<SessionRetryContext> {
        createSessionRetryContext(
            authorizedKeyJson = System.getenv("YDB_KEY_JSON"),
            connectionString = System.getenv("YDB_CONNECTION_STRING")
        )
    }
    factory<OmgtuScheduleApi> { OmgtuScheduleApiImpl(baseUrl = "https://rasp.omgtu.ru/api", client = get()) }
    single<OmgtuCapitanApi> {
        OmgtuCapitanApiImpl(
            driver = get(),
            login = System.getenv("CAPITAN_LOGIN"),
            password = System.getenv("CAPITAN_PASSWORD"),
            baseUrl = "https://www.omgtu.ru/"
        )
    }

}