package com.example.capstoneproject.ui.health

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.capstoneproject.R
import com.example.capstoneproject.data.local.SymptomsEntity
import com.example.capstoneproject.databinding.KonsulListBinding


class HealthAdapter(private val listSymptoms: List<SymptomsEntity>, private val context: Context) : RecyclerView.Adapter<HealthAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = KonsulListBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val name = listSymptoms[position].translatedSymptom
        holder.binding.healthTitle.text = name
        holder.binding.info.setOnClickListener {
            onItemClickCallback.onItemClicked(listSymptoms[holder.bindingAdapterPosition])
        }
        holder.binding.itemView.setOnClickListener {
            onItemClickCallback.onItemClickListener(listSymptoms[holder.bindingAdapterPosition])
            holder.binding.itemView.background = ContextCompat.getDrawable(context, R.drawable.bg_konsul_select)
            holder.binding.healthTitle.setTextColor((context.getColor(R.color.white)))
            holder.binding.info.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.outline_info_white))
        }
    }


    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun getItemCount(): Int = listSymptoms.size

    class ListViewHolder(var binding: KonsulListBinding) : ViewHolder(binding.root) {
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: SymptomsEntity)
        fun onItemClickListener(data: SymptomsEntity)
    }
}