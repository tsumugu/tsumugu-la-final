package xyz.tsumugu2626.app.la23.final2

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout

class TimelineEventItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val circleView: View

    init {
        LayoutInflater.from(context).inflate(R.layout.none, this, true)
        circleView = findViewById(R.id.none_layout)
    }

    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
        val x = circleView.x + circleView.width / 2
        val y = circleView.y + circleView.height / 2
        val radius = circleView.width / 2.toFloat()

        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.RED
        canvas.drawCircle(x, y, radius, paint)
    }
}