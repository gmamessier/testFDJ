package com.example.testfdj.search.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testfdj.GlideApp
import com.example.testfdj.R
import com.example.testfdj.search.model.Team
import com.google.android.material.imageview.ShapeableImageView

class TeamsListAdapter(private var teams: List<Team>) :
    RecyclerView.Adapter<TeamsListAdapter.TeamsListViewHolder>() {

    inner class TeamsListViewHolder(val logo: ShapeableImageView) : RecyclerView.ViewHolder(logo) {
        init {
            logo.setOnClickListener {
                onItemClick?.invoke(teams[adapterPosition].name)
            }
        }
    }
    var onItemClick: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsListViewHolder {
        val logo = LayoutInflater.from(parent.context).inflate(R.layout.teams_list_item, parent, false) as ShapeableImageView
        return TeamsListViewHolder(logo)
    }

    override fun onBindViewHolder(holder: TeamsListViewHolder, position: Int) {
        val team = teams[position]
        GlideApp.with(holder.logo.context)
            .load(team.logo + "/preview")
            .into(holder.logo)
    }

    override fun getItemCount() = teams.size
}