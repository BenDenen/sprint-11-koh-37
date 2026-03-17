package ru.practicum.sprint_11_koh_33

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.practicum.sprint_11_koh_37.R
import java.text.DateFormat

class NewsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: List<NewsItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when(viewType) {
            0 -> NewsScienceItemViewHolder(parent)
            1 -> NewsSportItemViewHolder(parent)
            else -> throw IllegalArgumentException("Unknow view type $viewType")
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        when(item) {
            is NewsItem.Science -> (holder as NewsScienceItemViewHolder).bind(item)
            is NewsItem.Sport -> (holder as NewsSportItemViewHolder).bind(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(items[position]) {
            is NewsItem.Science -> 0
            is NewsItem.Sport -> 1
        }
    }
}

class NewsSportItemViewHolder(
    parentView: ViewGroup
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parentView.context)
        .inflate(R.layout.sport_news_item, parentView, false)
) {

    private val title: TextView = itemView.findViewById(R.id.title)
    private val created: TextView = itemView.findViewById(R.id.created)
    private val sportItem: TextView = itemView.findViewById(R.id.sport_teams)

    fun bind(item: NewsItem.Sport) {
        title.text = item.title
        created.text =
            DateFormat.getDateTimeInstance(
                DateFormat.MEDIUM,
                DateFormat.MEDIUM
            ).format(item.created)

        sportItem.text = item.specificPropertyForSport

    }
}

class NewsScienceItemViewHolder(
    parentView: ViewGroup
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parentView.context)
        .inflate(R.layout.science_news_item, parentView, false)
) {

    private val title: TextView = itemView.findViewById(R.id.title)
    private val created: TextView = itemView.findViewById(R.id.created)
    private val scienceItem: ImageView = itemView.findViewById(R.id.science_img)

    fun bind(item: NewsItem.Science) {
        title.text = item.title
        created.text =
            DateFormat.getDateTimeInstance(
                DateFormat.MEDIUM,
                DateFormat.MEDIUM
            ).format(item.created)

        Glide.with(scienceItem).load((item as? NewsItem.Science)?.specificPropertyForScience).into(scienceItem)

    }
}