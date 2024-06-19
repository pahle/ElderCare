package com.example.capstoneproject.ui.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.ActivityRegistBinding
import com.example.capstoneproject.utils.Helper.Companion.DATA
import kotlin.system.exitProcess

class RegistActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.btnNext.setOnClickListener {
            val name = binding.registName.text.toString()
            val number = binding.registNumber.text.toString()
            when {
                name.isEmpty() -> {
                    binding.registName.error = resources.getString(R.string.error_empty)
                    Toast.makeText(this@RegistActivity, resources.getString(R.string.error_empty), Toast.LENGTH_LONG).show()
                }
                number.isEmpty() -> {
                    binding.registNumber.error = resources.getString(R.string.error_empty)
                    Toast.makeText(this@RegistActivity, resources.getString(R.string.error_empty), Toast.LENGTH_LONG).show()
                }
                number.length.equals(11..13 ) -> {
                    Toast.makeText(this@RegistActivity, resources.getString(R.string.error_number), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val intent = Intent(this@RegistActivity, RegistActivityTwo::class.java)
                    intent.putStringArrayListExtra(DATA, arrayListOf(name, getFirstTwoWords(name), number))
                    startActivity(intent)
                }
            }
        }
    }

    fun getFirstTwoWords(sentence: String): String {
        val words = sentence.trim().split("\\s+".toRegex())
        return if (words.size >= 2) {
            "${words[0]} ${words[1]}"
        } else {
            words.joinToString(" ")
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        AlertDialog.Builder(this).apply {
            setMessage(getString(R.string.exit))
            setPositiveButton("Yes") { _, _ ->
                finish()
                exitProcess(0)
            }
            setNegativeButton("No", null)
        }.show()
    }
}