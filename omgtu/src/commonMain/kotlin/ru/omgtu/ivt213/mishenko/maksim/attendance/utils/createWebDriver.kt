package ru.omgtu.ivt213.mishenko.maksim.attendance.utils

import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions

fun createWebDriver(chromeDriverPath: String, width: Int, height: Int): ChromeDriver {
    System.setProperty("webdriver.chrome.driver", chromeDriverPath)
//        val width = 1920 / 2 + 400
//        val height = 1080 - 300
    return ChromeDriver(ChromeOptions().apply {
        addArguments("--remote-allow-origins=*")
        addArguments("--window-size=$width,$height")
    })
}