package fr.iut.pm.techwatch.ui.utils

import android.content.Context
import android.text.format.DateFormat
import android.view.View
import java.util.*

object Converters {
    @JvmStatic
    fun dateToString(context: Context, value: Date?) =
        value?.let { DateFormat.getDateFormat(context).format(it) }

    @JvmStatic
    fun attrToVisibility(value: Any?) = if(value == null) View.INVISIBLE else View.VISIBLE
}