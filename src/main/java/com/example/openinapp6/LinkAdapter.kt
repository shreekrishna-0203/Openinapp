package com.example.openinapp6

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class LinkAdapter(private val links: List<Link>, private val onCopyClick: (String) -> Unit) :
    RecyclerView.Adapter<LinkAdapter.LinkViewHolder>() {

    class LinkViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivLinkIcon: ImageView = view.findViewById(R.id.ivLinkIcon)
        val tvLinkTitle: TextView = view.findViewById(R.id.tvLinkTitle)
        val tvLinkDate: TextView = view.findViewById(R.id.tvLinkDate)
        val tvClicks: TextView = view.findViewById(R.id.tvClicks)
        val tvLinkUrl: TextView = view.findViewById(R.id.tvLinkUrl)
        val ivCopy: ImageView = view.findViewById(R.id.ivCopy)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinkViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_link, parent, false)
        return LinkViewHolder(view)
    }

    override fun onBindViewHolder(holder: LinkViewHolder, position: Int) {
        val link = links[position]

        Glide.with(holder.ivLinkIcon.context)
            .load(link.iconUrl)
            .into(holder.ivLinkIcon)

        holder.tvLinkTitle.text = link.title
        holder.tvLinkDate.text = link.date
        holder.tvClicks.text = link.clicks.toString()
        holder.tvLinkUrl.text = link.url

        holder.ivCopy.setOnClickListener {
            onCopyClick(link.url)
        }
    }

    override fun getItemCount() = links.size
}