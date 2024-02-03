package ru.omgtu.ivt213.mishenko.maksim.attendance.utils

import tech.ydb.table.SessionRetryContext
import tech.ydb.table.query.DataQueryResult
import tech.ydb.table.transaction.TxControl

fun SessionRetryContext.executeResultQuery(query: String): DataQueryResult {
    val txControl = TxControl.serializableRw().setCommitTx(true)
    return supplyResult { session ->
        session.executeDataQuery(query, txControl)
    }.join().value
}