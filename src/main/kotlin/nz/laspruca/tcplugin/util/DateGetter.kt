package nz.laspruca.tcplugin.util

import java.text.SimpleDateFormat
import java.util.*

private val format = SimpleDateFormat("E yyyy/MM/dd:HH:mm:ss")

/**
 * Gets the current data and time using 'E yyyy/MM/dd:HH:mm:ss' format
 */
fun getDate(): String = format.format(Date())

