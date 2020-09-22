package de.fruxz.report.utils

class NumberUtils {

    fun isInt(v: String): Boolean {
        return try {
            v.toInt()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

}