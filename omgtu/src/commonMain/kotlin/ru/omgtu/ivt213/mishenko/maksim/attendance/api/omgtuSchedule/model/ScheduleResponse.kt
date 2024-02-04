package ru.omgtu.ivt213.mishenko.maksim.attendance.api.omgtuSchedule.model

import kotlinx.serialization.Serializable
import ru.omgtu.ivt213.mishenko.maksim.attendance.api.omgtuSchedule.model.Groups
import ru.omgtu.ivt213.mishenko.maksim.attendance.api.omgtuSchedule.model.OfLecturers
import ru.omgtu.ivt213.mishenko.maksim.attendance.model.Lesson
import ru.omgtu.ivt213.mishenko.maksim.attendance.model.LessonType

@Serializable
data class ScheduleResponse(
    val auditorium: String,
    val auditoriumAmount: Int,
    val auditoriumGUID: String,
    val auditoriumOid: Int,
    val author: String,
    val beginLesson: String,
    val building: String,
    val buildingGid: Int,
    val buildingOid: Int,
    val contentOfLoadOid: Int,
    val contentOfLoadUID: String?,
    val contentTableOfLessonsName: String,
    val contentTableOfLessonsOid: Int,
    val createddate: String,
    val date: String,
    val dateOfNest: String,
    val dayOfWeek: Int,
    val dayOfWeekString: String,
    val detailInfo: String,
    val discipline: String,
    val disciplineOid: Int,
    val disciplineinplan: String?,
    val disciplinetypeload: Int,
    val duration: Int,
    val endLesson: String,
    val group: String?,
    val groupGUID: String?,
    val groupOid: Int?,
    val groupUID: String?,
    val group_facultyname: String?,
    val group_facultyoid: Int?,
    val hideincapacity: Int,
    val isBan: Boolean,
    val kindOfWork: String,
    val kindOfWorkComplexity: Int,
    val kindOfWorkOid: Int,
    val kindOfWorkUid: String?,
    val lecturer: String,
    val lecturerCustomUID: String?,
    val lecturerEmail: String?,
    val lecturerGUID: String,
    val lecturerOid: Int,
    val lecturerUID: String?,
    val lecturer_post_oid: Int,
    val lecturer_rank: String,
    val lecturer_title: String,
    val lessonNumberEnd: Int,
    val lessonNumberStart: Int,
    val lessonOid: Int,
    val listGroups: List<Groups>,
    val listOfLecturers: List<OfLecturers>,
    val modifieddate: String,
    val note: String?,
    val note_description: String,
    val parentSchedule_Status: Int,
    val parentschedule: String,
    val replaces: String?,
    val specialization_name: String?,
    val specialization_oid: Int,
    val stream: String?,
    val streamOid: Int?,
    val stream_facultyoid: Int?,
    val subGroup: String?,
    val subGroupOid: Int?,
    val subgroup_facultyoid: Int?,
    val subgroup_groupOid: Int?,
    val tableofLessonsName: String,
    val tableofLessonsOid: Int,
    val url1: String,
    val url1_description: String,
    val url2: String,
    val url2_description: String
) {
    fun toLesson(): Lesson =
        Lesson(id = -1, name = discipline, teacher = lecturer, type = getLessonType())

    fun getLessonType(): LessonType {
        return when (kindOfWork) {
            "Лекция" -> LessonType.LECTION
            "Лабораторные работы" -> LessonType.LABS
            "Практические занятия" -> LessonType.PRACTICAL
            else -> LessonType.UNKNOW
        }
    }
}