package com.example.capstoneproject.ui.health

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.fragment.app.viewModels
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.FragmentHealthBinding
import com.example.capstoneproject.utils.Helper.Companion.DATA
import com.example.capstoneproject.utils.ViewModelFactory
import java.util.ArrayList


class HealthFragment : Fragment() {

    private var _binding: FragmentHealthBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HealthViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                AlertDialog.Builder(requireContext()).apply {
                    setMessage(getString(com.example.capstoneproject.R.string.exit))
                    setPositiveButton("Yes") { _, _ ->
                        activity?.moveTaskToBack(true)
                        activity?.finish()
                    }
                    setNegativeButton("No", null)
                }.show()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHealthBinding.inflate(inflater, container, false)
        viewModel.getUser().observe(viewLifecycleOwner) {
            binding.materialTextView.text = resources.getString(R.string.homepage, it.nickname)
        }
        Handler(Looper.getMainLooper()).postDelayed({
        viewModel.symptoms().observe(viewLifecycleOwner) {
        }}, 500)

        binding.health1.setOnClickListener{
            viewModel.setData(resources.getString(R.string.health1))
            binding.health1.setTextColor(requireContext().getColor(R.color.white))
            binding.health1.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_konsul_select)
        }

        binding.health2.setOnClickListener{
            viewModel.setData(resources.getString(R.string.health2))
            binding.health2.setTextColor(requireContext().getColor(R.color.white))
            binding.health2.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_konsul_select)
        }

        binding.health3.setOnClickListener{
            viewModel.setData(resources.getString(R.string.health3))
            binding.health3.setTextColor(requireContext().getColor(R.color.white))
            binding.health3.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_konsul_select)
        }

        binding.health4.setOnClickListener{
            viewModel.setData(resources.getString(R.string.health4))
            binding.health4.setTextColor(requireContext().getColor(R.color.white))
            binding.health4.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_konsul_select)
        }

        binding.health5.setOnClickListener{
            viewModel.setData(resources.getString(R.string.health5))
            binding.health5.setTextColor(requireContext().getColor(R.color.white))
            binding.health5.background = ContextCompat.getDrawable(requireContext(),R.drawable.bg_konsul_select)
        }

        binding.health6.setOnClickListener{
            viewModel.setData(resources.getString(R.string.health6))
            binding.health6.setTextColor(requireContext().getColor(R.color.white))
            binding.health6.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_konsul_select)
        }

        binding.btnNext.setOnClickListener {
            val mBundle = Bundle()
            viewModel.healthList.observe(viewLifecycleOwner) {
                val list = it.map {
                    it
                }
                val arraylist = ArrayList(list)
                mBundle.putStringArrayList(DATA, arraylist)
                findNavController().navigate(
                    R.id.action_navigation_health_to_navigation_healthsec,
                    mBundle
                )
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}