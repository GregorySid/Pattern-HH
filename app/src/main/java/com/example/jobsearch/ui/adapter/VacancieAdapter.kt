package com.example.jobsearch.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.jobsearch.R
import com.example.jobsearch.databinding.VacancyHolderBinding
import com.example.jobsearch.ui.dModel.DataVacancie
import java.text.SimpleDateFormat
import java.util.Date

class VacancieAdapter(val lis: (DataVacancie) -> Unit):
    ListAdapter<DataVacancie, VacancieAdapter.ViewHolder>(Comp()) {
    var items = listOf<DataVacancie>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(view: View, val lis: (DataVacancie) -> Unit) : RecyclerView.ViewHolder(view) {
        private val binding = VacancyHolderBinding.bind(view)

        fun bind(item: DataVacancie) = with(binding) {
            val timeDate: Date =
                SimpleDateFormat("yyyy-MM-dd").parse(item.publishedDate)
            val humanTime: String =
                SimpleDateFormat("d MMMM").format(timeDate)
            val number = item.lookingNumber
            tvHour.text = when (number) {
                null -> ""
                2, 3, 4 -> "Сейчас просматривает $number человека"
                else -> "Сейчас просматривает $number человек"
            }
            tvUi.text = item.title
            tvCity.text = item.address.town
            tvTitle.text = item.company
            tvExperience.text = item.experience.previewText
            tvDate.text = "Опубликовано $humanTime"
            ivHeart.setOnClickListener {
                lis(item)
            }
            when(item.isFavorite) {
                false -> ivHeart.setImageResource(R.drawable.ic_heart)
                true -> ivHeart.setImageResource(R.drawable.ic_heart_active)
            }
        }
    }

    class Comp : DiffUtil.ItemCallback<DataVacancie>() {
        override fun areItemsTheSame(oldItem: DataVacancie, newItem: DataVacancie): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DataVacancie, newItem: DataVacancie): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vacancy_holder, parent, false)
        return ViewHolder(view, lis)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}