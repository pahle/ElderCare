package com.example.capstoneproject.ui.archive

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstoneproject.databinding.ActivityArchiveBinding
import com.example.capstoneproject.utils.ViewModelFactory

class ArchiveActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArchiveBinding
    private val viewModel by viewModels<ArchiveViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArchiveBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val archiveAdapter = ArchiveAdapter()
        binding.listarchive.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = archiveAdapter
        }

        viewModel.getArchive().observe(this@ArchiveActivity) {
            Toast.makeText(this@ArchiveActivity, it.size.toString(), Toast.LENGTH_SHORT).show()
            archiveAdapter.submitList(it)
        }

        binding.back.setOnClickListener {
            finish()
        }
    }
}