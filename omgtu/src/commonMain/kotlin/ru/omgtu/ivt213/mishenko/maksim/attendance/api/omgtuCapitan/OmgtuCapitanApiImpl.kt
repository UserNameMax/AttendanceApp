package ru.omgtu.ivt213.mishenko.maksim.attendance.api.omgtuCapitan

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.Select
import ru.omgtu.ivt213.mishenko.maksim.attendance.api.omgtuCapitan.model.AttendanceDto
import ru.omgtu.ivt213.mishenko.maksim.attendance.api.omgtuCapitan.model.LessonDto
import ru.omgtu.ivt213.mishenko.maksim.attendance.model.Lesson
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

class OmgtuCapitanApiImpl(
    private val driver: WebDriver,
    private val login: CharSequence,
    private val password: CharSequence,
    private val baseUrl: String
) : OmgtuCapitanApi {
    init {
        initSite()
    }

    private fun initSite() {
        driver.get(baseUrl)
        driver.manage()?.timeouts()?.implicitlyWait(1, TimeUnit.MINUTES)
        driver
            .findElement(By.cssSelector("body > div > div > header > div.header__wrapper > div.header__top.top-header.open > div > div > div.top-header__nav-right.horizontal-menu > ul > li.horizontal-menu__item.header-login-form-hover"))
            ?.click()
        driver
            .findElement(By.cssSelector("#abitloginform > div > table > tbody > tr:nth-child(1) > td:nth-child(2) > input"))
            ?.sendKeys(login)
        driver
            .findElement(By.cssSelector("#abitloginform > div > table > tbody > tr:nth-child(2) > td:nth-child(2) > input"))
            ?.sendKeys(password)
        driver
            .findElements(By.cssSelector("#abitkeyreg"))?.last()
            ?.click()
        driver.manage()?.timeouts()?.implicitlyWait(15, TimeUnit.SECONDS)
        driver.findElements(By.tagName("li"))?.firstOrNull { it.text == "Студенческий портал" }?.click()
        driver.manage()?.timeouts()?.implicitlyWait(1, TimeUnit.MINUTES)
        driver.findElements(By.tagName("a"))?.firstOrNull { it.text.contains("Учебный процесс") }?.click()
        driver.findElements(By.tagName("a"))?.firstOrNull { it.text.contains("Журнал посещаемости") }?.click()
    }

    override fun selectDate(date: LocalDate) {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val sDate: CharSequence = date.format(formatter)
        driver.findElement(By.cssSelector("#publishDate"))?.apply {
            sendKeys(Keys.chord(Keys.CONTROL, "a"))
            sendKeys(sDate)
        }
        driver.findElement(By.cssSelector("#markStatSave > button.btn.btn-primary.btn-sm"))?.click()
    }

    override fun getStudentList(): List<String> {
        return driver.findElement(By.tagName("tbody"))?.findElements(By.tagName("tr"))
            ?.mapNotNull { it.findElement(By.tagName("th")).text } ?: listOf()
    }

    override fun getAttendance(): List<AttendanceDto> {
        val students = getStudentList()
        return getLessons().map { lesson ->
            students.mapNotNull { student ->
                getAttendance(student, lesson).let {
                    if (it == "Явка") AttendanceDto(
                        student = student,
                        lessonDto = lesson,
                        typeNumber = 1
                    ) else null //TODO add type
                }
            }
        }.flatten()
    }

    fun getAttendance(name: String, lesson: LessonDto): String {
        val lessonIndex =
            (driver.findElement(By.tagName("thead"))?.findElement(By.tagName("tr"))?.findElements(By.tagName("th"))
                ?.indexOfFirst {
                    it.text.contains(lesson.name) && it.text.contains(lesson.teacher) && it.text.contains(lesson.time)
                } ?: 0) - 1
        val select = driver.findElement(By.tagName("tbody"))?.findElements(By.tagName("tr"))
            ?.firstOrNull { it.findElement(By.tagName("th")).text == name }?.findElements(By.tagName("td"))
            ?.filterIndexed { index, _ -> index % 2 == 0 }?.get(lessonIndex)?.findElement(By.tagName("select"))
            ?.run { Select(this) }
        return select?.firstSelectedOption?.text ?: ""
    }

    override fun getLessons(): List<LessonDto> =
        try {
            driver.findElement(By.tagName("thead"))?.findElement(By.tagName("tr"))?.findElements(By.tagName("th"))
                ?.mapNotNull { it.text }?.filter { it != "ФИО" }?.map { it.toLesson() } ?: listOf()
        } catch (e: Throwable) {
            listOf()
        }

    override fun setAttendance(attendance: AttendanceDto) {
        val lesson = attendance.lessonDto
        val lessonIndex =
            (driver.findElement(By.tagName("thead"))?.findElement(By.tagName("tr"))?.findElements(By.tagName("th"))
                ?.indexOfFirst {
                    it.text.contains(lesson.name)
                            && it.text.contains(lesson.time)
                            && it.text.contains(lesson.teacher)
                } ?: 0) - 1
        val select = driver.findElement(By.tagName("tbody"))?.findElements(By.tagName("tr"))
            ?.firstOrNull { it.findElement(By.tagName("th")).text == attendance.student }
            ?.findElements(By.tagName("td"))
            ?.filterIndexed { index, _ -> index % 2 == 0 }?.get(lessonIndex)?.findElement(By.tagName("select"))
            ?.run { Select(this) }
        select?.selectByIndex(attendance.typeNumber)
    }

    override fun save() {
        val element = driver?.findElement(By.cssSelector("#sendForm"))
        val actions = Actions(driver)
        val jse = driver as JavascriptExecutor
        jse.executeScript("scroll(0, 2000)")
        actions.moveToElement(element).click().perform()
    }

    private fun String.toLesson(): LessonDto {
        return split("\n").let { parts ->
            LessonDto(
                name = parts[0],
                groupType = parts[1].trim('(', ')'),
                lessonType = parts[2],
                teacher = parts[3].trim('(', ')'),
                time = parts[4]
            )
        }
    }
}

