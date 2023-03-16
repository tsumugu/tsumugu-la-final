package xyz.tsumugu2626.app.la23.final2

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TimelineEventLayoutManager(context: Context) : LinearLayoutManager(context) {
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onMeasure(
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State,
        widthSpec: Int,
        heightSpec: Int
    ) {
        super.onMeasure(recycler, state, widthSpec, heightSpec)
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val lp = child?.layoutParams as RecyclerView.LayoutParams
            lp.height = 500 * i
            child.layoutParams = lp
            child.measure(
                View.MeasureSpec.makeMeasureSpec(widthSpec, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(lp.height, View.MeasureSpec.EXACTLY)
            )
        }
    }
}