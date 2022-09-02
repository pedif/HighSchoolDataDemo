package com.pedif.nyhighschoolinfo.ui.highschool

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pedif.nyhighschoolinfo.data.model.SAT
import com.pedif.nyhighschoolinfo.databinding.ItemSatBinding

/**
 * List adapter for SAT items
 */
class SATAdapter() :
    RecyclerView.Adapter<SATAdapter.SchoolViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<SAT>() {
        override fun areItemsTheSame(oldItem: SAT, newItem: SAT): Boolean {
            return oldItem.dbn == newItem.dbn
        }

        override fun areContentsTheSame(oldItem: SAT, newItem: SAT): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var items: List<SAT>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolViewHolder {
        return SchoolViewHolder(
            ItemSatBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SchoolViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class SchoolViewHolder(private val binding: ItemSatBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(sat: SAT) {
            with(binding){
                tvParticipantCount.text = sat.num_of_sat_test_takers
                tvThinking.text = sat.sat_critical_reading_avg_score
                tvMath.text = sat.sat_math_avg_score
                tvWriting.text = sat.sat_writing_avg_score
            }
        }
    }

}