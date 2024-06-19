package com.example.capstoneproject.ui.user

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.capstoneproject.MainActivity
import com.example.capstoneproject.R
import com.example.capstoneproject.data.local.UserEntity
import com.example.capstoneproject.databinding.ActivityRegistTwoBinding
import com.example.capstoneproject.utils.Helper.Companion.DATA
import com.example.capstoneproject.utils.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Suppress("DEPRECATION")
class RegistActivityTwo  : AppCompatActivity() {
    private lateinit var binding: ActivityRegistTwoBinding
    private val calendar = Calendar.getInstance()
    private val viewModel by viewModels<AuthViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        var gender = binding.registGender.text.toString()
        val languages = resources.getStringArray(R.array.gender)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, languages)
        val data = intent.getStringArrayListExtra(DATA)
        binding.registGender.setAdapter(arrayAdapter)
        binding.registGender.setOnItemClickListener { parent, _, position, _ ->
            gender = parent.getItemAtPosition(position).toString()
        }

        binding.btnPicker.setOnClickListener {
            showDatePicker()
        }

        binding.btnBack.setOnClickListener {
            viewModel.setUser(UserEntity(data?.get(0).toString(), data?.get(1).toString(), gender, binding.registDate.text.toString(), data?.get(2).toString()))
            finish()
        }

        binding.btnNext.setOnClickListener {
            when {
                binding.registDate.text.toString().isEmpty() -> {
                    binding.registDate.error = resources.getString(R.string.error_empty)
                    Toast.makeText(this@RegistActivityTwo, resources.getString(R.string.error_empty), Toast.LENGTH_LONG).show()
                }
                gender.isEmpty() -> {
                    binding.registGender.error = resources.getString(R.string.error_empty)
                    Toast.makeText(this@RegistActivityTwo, resources.getString(R.string.error_empty), Toast.LENGTH_LONG).show()
                }
                else -> {
                    viewModel.setUser(UserEntity(data?.get(0).toString(), data?.get(1).toString(), gender, binding.registDate.text.toString(), data?.get(2).toString()))
                    val intent = Intent(this@RegistActivityTwo, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(
            this, { _, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonth)
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate.time)
                binding.registDate.setText(formattedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
        datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
        datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)

    }
}