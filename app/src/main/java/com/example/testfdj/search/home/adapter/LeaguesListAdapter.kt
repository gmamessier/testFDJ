package com.example.testfdj.search.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testfdj.search.model.League

class LeaguesListAdapter(private var leagues: List<League>) :
    RecyclerView.Adapter<LeaguesListAdapter.LeaguesListViewHolder>() {

    inner class LeaguesListViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView) {
        init {
            textView.setOnClickListener {
                onItemClick?.invoke(leagues[adapterPosition].name)
            }
        }
    }
    var onItemClick: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaguesListViewHolder {
        val textView = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false) as TextView
        return LeaguesListViewHolder(textView)
    }

    override fun onBindViewHolder(holder: LeaguesListViewHolder, position: Int) {
        val league = leagues[position]
        holder.textView.text = if (!league.alternateName.isNullOrEmpty()) league.alternateName else league.name
    }

    override fun getItemCount() = leagues.size

    fun updateLeaguesList(newLeagues: List<League>) {
        leagues = newLeagues
        notifyDataSetChanged()
    }
}