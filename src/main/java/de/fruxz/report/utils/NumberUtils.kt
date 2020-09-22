package de.fruxz.report.utils

/**
 * This class helps to easily manage and handler
 * numbers and its formatters
 * @author Fruxz
 * @since v1.0
 */
class NumberUtils {

    /**
     * This function returns, if [v] can be
     * converted to an integer
     * @see Int
     * @return Is convertable to int
     * @author Fruxz
     * @since v1.0
     */
    fun isInt(v: String): Boolean {
        return try {
            v.toInt()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

}