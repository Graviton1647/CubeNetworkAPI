package org.cube.api.timer

fun convertTime(time: String): Long {
    val bits = time.split(" ")
    val hours = bits.find { it.contains("h") }?.replace("h", "")?.toLong() ?: 0
    val minutes = bits.find { it.contains("m") }?.replace("m", "")?.toLong() ?: 0
    val seconds = bits.find { it.contains("s") }?.replace("s", "")?.toLong() ?: 0

    return 3600 * hours + 60 * minutes + seconds
}

fun secondsToString(seconds : Long) : String {
    val hours = seconds.toInt() / 3600
    val minutes = (seconds.toInt() % 3600) / 60
    val seconds = seconds.toInt() % 60

    var format = ""
    if(hours != 0) {
        format += "$hours Hour".pluralOrSingal(hours)
    }
    if(minutes != 0) {
        format +="$minutes Minute".pluralOrSingal(minutes)
    }
    if(seconds != 0) {
        format +="$seconds Second".pluralOrSingal(seconds)
    }
    return format
}

fun String.pluralOrSingal(value : Int) : String {
    return this + if(value > 1) "s " else " "
}