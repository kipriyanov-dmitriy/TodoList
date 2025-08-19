package com.example.todo.core

import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

object TodoDateUtils {
    private const val DEFAULT_DATE_PATTERN = "dd.MM.yyyy"
    private const val DEFAULT_DATETIME_PATTERN = "dd.MM.yyyy HH:mm"

    private fun dateFormatter(): DateTimeFormatter =
        DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN, Locale.getDefault())

    private fun dateTimeFormatter(): DateTimeFormatter =
        DateTimeFormatter.ofPattern(DEFAULT_DATETIME_PATTERN, Locale.getDefault())

    /** Текущая дата */
    fun today(): LocalDate = LocalDate.now()

    /** Текущее время с датой */
    fun now(): LocalDateTime = LocalDateTime.now()

    /** Форматирование LocalDate */
    fun formatDate(date: LocalDate, formatter: DateTimeFormatter = dateFormatter()): String =
        date.format(formatter)

    /** Форматирование LocalDateTime */
    fun formatDateTime(
        dateTime: LocalDateTime,
        formatter: DateTimeFormatter = dateTimeFormatter()
    ): String = dateTime.format(formatter)

    /** Парсинг строки в LocalDate */
    fun parseDate(dateString: String, formatter: DateTimeFormatter = dateFormatter()): LocalDate =
        LocalDate.parse(dateString, formatter)

    /** Парсинг строки в LocalDateTime */
    fun parseDateTime(dateTimeString: String, formatter: DateTimeFormatter = dateTimeFormatter()): LocalDateTime =
        LocalDateTime.parse(dateTimeString, formatter)

    /** Добавить дни */
    fun addDays(date: LocalDate, days: Long): LocalDate = date.plusDays(days)

    /** Разница в днях между двумя датами */
    fun daysBetween(start: LocalDate, end: LocalDate): Long =
        Duration.between(start.atStartOfDay(), end.atStartOfDay()).toDays()
}