package xyz.tsumugu2626.app.la23.final2

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.util.*
import kotlin.math.roundToInt

object DP {
    @RequiresApi(Build.VERSION_CODES.O)
    fun calcDpFromTimestamp(startedAt: Date, endedAt: Date): Int {
        val BORDER = 70

        val dp = (((endedAt.toLocaleEpochSeconds()-startedAt.toLocaleEpochSeconds()) / 60 ) * 2).toInt()

        if (dp <= BORDER) {
            return BORDER
        }
        return dp
    }

    fun dpToPx(dp: Int, context: Context): Int {
        val metrics = context.resources.displayMetrics
        return (dp * metrics.density).roundToInt()
    }

    fun pxToDp(px: Int, context: Context): Int {
        val metrics = context.resources.displayMetrics
        return (px / metrics.density).roundToInt()
    }
}