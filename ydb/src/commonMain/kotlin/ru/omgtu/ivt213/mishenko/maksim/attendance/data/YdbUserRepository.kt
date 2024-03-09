package ru.omgtu.ivt213.mishenko.maksim.attendance.data

import ru.omgtu.ivt213.mishenko.maksim.attendance.model.User
import ru.omgtu.ivt213.mishenko.maksim.attendance.utils.executeQuery
import tech.ydb.table.SessionRetryContext
import tech.ydb.table.result.ResultSetReader

class YdbUserRepository(private val sessionRetryContext: SessionRetryContext): UserRepository {
    override suspend fun getUsers(): List<User> {
        val result = mutableListOf<User>()
        sessionRetryContext.executeQuery("select * from student").getResultSet(0).apply {
            while (next()) {
                result.add(getUser())
            }
        }
        return result
    }

    private fun ResultSetReader.getUser() = User(
        login = getColumn("name").text,
        password = getColumn("password")?.text,
        isCanMakeMark = getColumn("isCanMakeMark")?.bool ?: false
    )
}