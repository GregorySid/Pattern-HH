package com.example.jobsearch.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.jobsearch.R
import com.example.jobsearch.databinding.SearchHolderBinding
import com.example.jobsearch.ui.dModel.DataOffer

class SearchAdapter(val listner: (DataOffer) -> Unit):
    ListAdapter<DataOffer, SearchAdapter.ViewHolder>(Comp()) {
    var items = listOf<DataOffer>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(view: View, val listner: (DataOffer) -> Unit) : RecyclerView.ViewHolder(view){
        private val binding = SearchHolderBinding.bind(view)

        fun bind(item: DataOffer) = with(binding) {
            tvInfo.text = item.title
            tvRise.text = item.button?.text
            itemView.setOnClickListener {
                listner(item)
            }
            when(item.id){
                "near_vacancies" -> {
                    ivStar.setImageResource(R.drawable.ic_near_vacancies)
                    ivStar.setBackgroundResource(R.color.near_vacancie)
                }
                "temporary_job" -> ivStar.setImageResource(R.drawable.ic_temporary_job)
                "level_up_resume" -> ivStar.setImageResource(R.drawable.ic_starstate)
                null -> ivCard.visibility = View.INVISIBLE
            }
        }
    }

    class Comp : DiffUtil.ItemCallback<DataOffer>() {
        override fun areItemsTheSame(oldItem: DataOffer, newItem: DataOffer): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DataOffer, newItem: DataOffer): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_holder, parent, false)
        return ViewHolder(view, listner)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}