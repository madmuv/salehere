package utils

import org.joda.time.Days
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat

fun LocalDate?.diffDays(): String {
    val dateFormatter: org.joda.time.format.DateTimeFormatter? =  DateTimeFormat.forPattern("MM/dd/yyy")

    val from = LocalDate.parse(this?.toString(dateFormatter), dateFormatter)
    val to = LocalDate.parse(LocalDate.now().toString(dateFormatter), dateFormatter)
    val period = Days.daysBetween(to, from).days
    return period.toString()
}