package xyz.tsumugu2626.app.la23.final2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import xyz.tsumugu2626.app.la23.final2.databinding.TimelineItemBinding

class TimelineEventAdapter : ListAdapter<TimelineEvent, TimelineEventViewHolder>(diffUtilItemCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelineEventViewHolder {
        val view = TimelineItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TimelineEventViewHolder(view)
    }

    override fun onBindViewHolder(holder: TimelineEventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class TimelineEventViewHolder(
    private val binding: TimelineItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(timelineEvent: TimelineEvent) {
        binding.tvItemModel.text = timelineEvent.id
//        binding.titleTextView.text = task.title
//        binding.dateTextView.text =
//            SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.JAPANESE).format(task.createdAt)
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