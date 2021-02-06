package fr.iut.pm.techwatch.ui.utils

import android.content.Context
import android.text.format.DateFormat
import android.view.View
import java.text.SimpleDateFormat

object Converters {
    @JvmStatic
    fun dateToString(context: Context, value: String?) =
        value?.let { DateFormat.getDateFormat(context).format(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(it)) }

    @JvmStatic
    fun attrToVisibility(value: Any?) = if(value == null) View.INVISIBLE else View.VISIBLE
}