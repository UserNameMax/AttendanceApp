package ru.omgtu.ivt213.mishenko.maksim.attendance.utils

import tech.ydb.auth.iam.CloudAuthHelper
import tech.ydb.core.grpc.GrpcTransport
import tech.ydb.table.SessionRetryContext
import tech.ydb.table.TableClient

fun createSessionRetryContext(authorizedKeyJson: String, connectionString: String): SessionRetryContext {
    val transport: GrpcTransport =
        GrpcTransport.forConnectionString(connectionString)
            .withAuthProvider(CloudAuthHelper.getServiceAccountJsonAuthProvider(authorizedKeyJson))
            .build()
    val tabletClient = TableClient.newClient(transport).build()
    return SessionRetryContext.create(tabletClient).build()
}