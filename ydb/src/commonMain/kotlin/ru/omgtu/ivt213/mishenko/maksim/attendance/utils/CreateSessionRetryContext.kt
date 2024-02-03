package ru.omgtu.ivt213.mishenko.maksim.attendance.utils

import tech.ydb.auth.TokenAuthProvider
import tech.ydb.core.grpc.GrpcTransport
import tech.ydb.table.SessionRetryContext
import tech.ydb.table.TableClient

fun createSessionRetryContext(token: String, connectionString: String): SessionRetryContext {
    val transport =
        GrpcTransport.forConnectionString(connectionString)
            .withAuthProvider(TokenAuthProvider(token))
            .build()
    val tabletClient = TableClient.newClient(transport).build()
    return SessionRetryContext.create(tabletClient).build()
}