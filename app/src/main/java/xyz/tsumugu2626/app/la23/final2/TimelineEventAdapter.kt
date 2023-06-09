package xyz.tsumugu2626.app.la23.final2

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Space
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class TimelineEventAdapter(private val itemList: List<TimelineEvent>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_ONE = 1
    private val TYPE_TWO = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_ONE -> {
                Log.d("viewType", "1 item")
                val view = LayoutInflater.from(parent.context).inflate(R.layout.timeline_item_with_texts, parent, false)
                TypeOneViewHolder(view)
            }
            TYPE_TWO -> {
                Log.d("viewType", "2 spacer")
                val view = LayoutInflater.from(parent.context).inflate(R.layout.timeline_item, parent, false)
                TypeTwoViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itemList[position]
        when (holder) {
            is TypeOneViewHolder -> {
                holder.timeWithTextStartTextView.text =  item.startHm
                holder.timeWithTextEndTextView.text = item.endHm
                holder.memberTextView.text = item.users?.size.toString()
                //holder.descriptionTextView.text = item.url
                holder.descriptionTextView.text = item.startedAt.toString()
                holder.timeboxTextView.text =  item.startHm + "-" + item.endHm

                val params = holder.timeWithTextSpacer.layoutParams
                params.height = item.heightPx
                holder.timeWithTextSpacer.layoutParams = params
            }
            is TypeTwoViewHolder -> {
                holder.timeStartTextView.text = item.startHm
                holder.timeEndTextView.text = item.endHm

                val params = holder.timeSpacer.layoutParams
                params.height = item.heightPx
                holder.timeSpacer.layoutParams = params
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getItemViewType(position: Int): Int {
        val item = itemList[position]
        return if (item.type == "timeline_event") {
            TYPE_ONE
        } else {
            TYPE_TWO
        }
    }

    inner class TypeOneViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val timeWithTextStartTextView: TextView = itemView.findViewById(R.id.timeline_item_with_text_text_start)
        val timeWithTextEndTextView: TextView = itemView.findViewById(R.id.timeline_item_with_text_text_end)
        val memberTextView: TextView = itemView.findViewById(R.id.timeline_item_with_text_text_member)
        val descriptionTextView: TextView = itemView.findViewById(R.id.timeline_item_with_text_text_description)
        val timeboxTextView: TextView = itemView.findViewById(R.id.timeline_item_with_text_text_time_box)
        val timeWithTextSpacer: Space = itemView.findViewById(R.id.timeline_item_with_text_spacer)
    }

    inner class TypeTwoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val timeStartTextView: TextView = itemView.findViewById(R.id.timeline_item_text_start)
        val timeEndTextView: TextView = itemView.findViewById(R.id.timeline_item_text_end)
        val timeSpacer: Space = itemView.findViewById(R.id.timeline_item_spacer)
    }
}

private val diffUtilItemCallback = object : DiffUtil.ItemCallback<TimelineEvent>() {
    override fun areContentsTheSame(oldItem: TimelineEvent, newItem: TimelineEvent): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: TimelineEvent, newItem: TimelineEvent): Boolean {
        return oldItem.id == newItem.id
    }
}