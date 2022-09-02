package com.pedif.nyhighschoolinfo.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pedif.nyhighschoolinfo.data.model.HighSchool
import com.pedif.nyhighschoolinfo.databinding.ItemSchoolBinding

/**
 * List adapter for school items
 */
class SchoolAdapter(private val clickListener: OnclickListener) :
    RecyclerView.Adapter<SchoolAdapter.SchoolViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<HighSchool>() {
        override fun areItemsTheSame(oldItem: HighSchool, newItem: HighSchool): Boolean {
            return oldItem.dbn == newItem.dbn
        }

        override fun areContentsTheSame(oldItem: HighSchool, newItem: HighSchool): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var items: List<HighSchool>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolViewHolder {
        return SchoolViewHolder(
            ItemSchoolBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SchoolViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener { clickListener.onclick(items[position]) }

    }

    inner class SchoolViewHolder(private val binding: ItemSchoolBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(school: HighSchool) {
           with(binding) {
               tvName.text = school.school_name
               tvCity.text = school.city
               tvOverview.text = school.overview_paragraph
           }
        }
    }

    class OnclickListener(val clickListener: (school: HighSchool) -> Unit) {
        fun onclick(school: HighSchool) = clickListener(school)
    }
}