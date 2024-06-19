package com.example.capstoneproject.ui.archive

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.data.local.ArchiveEntity
import com.example.capstoneproject.databinding.ArchiveListBinding

class ArchiveAdapter: ListAdapter<ArchiveEntity, ArchiveAdapter.ListViewHolder>(DIFF_CALLBACK) {

    class ListViewHolder (var binding: ArchiveListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(archive: ArchiveEntity) {
            binding.title.text = archive.diagnose
            binding.detail.text = archive.detail
            binding.date.text = archive.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ArchiveListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<ArchiveEntity> =
            object : DiffUtil.ItemCallback<ArchiveEntity>() {
                override fun areItemsTheSame(oldUser: ArchiveEntity, newUser: ArchiveEntity): Boolean {
                    return oldUser.id == newUser.id
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldUser: ArchiveEntity, newUser: ArchiveEntity): Boolean {
                    return oldUser == newUser
                } }
    }
}