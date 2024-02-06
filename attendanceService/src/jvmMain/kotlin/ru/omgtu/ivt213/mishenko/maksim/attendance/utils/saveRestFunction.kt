package ru.omgtu.ivt213.mishenko.maksim.attendance.utils

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*

fun Route.saveGet(path: String = "", body: suspend PipelineContext<Unit, ApplicationCall>.() -> Unit): Route {
    return get(path) {
        try {
            body()
        } catch (e: Throwable) {
            call.respond(HttpStatusCode.InternalServerError, e.stackTraceToString())
        }
    }
}

fun Route.savePost(path: String = "", body: suspend PipelineContext<Unit, ApplicationCall>.() -> Unit): Route {
    return post(path) {
        try {
            body()
        } catch (e: Throwable) {
            call.respond(HttpStatusCode.InternalServerError, e.stackTraceToString())
        }
    }
}