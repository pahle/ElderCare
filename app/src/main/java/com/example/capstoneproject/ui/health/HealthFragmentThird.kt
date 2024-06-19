package com.example.capstoneproject.ui.health

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.capstoneproject.databinding.FragmentHealthThirdBinding
import com.example.capstoneproject.utils.Helper.Companion.DATA

class HealthFragmentThird : Fragment() {

    private var _binding: FragmentHealthThirdBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentHealthThirdBinding.inflate(inflater, container, false)
        val data = arguments?.getStringArrayList(DATA)
            binding.title.text = data?.get(0).toString()
            binding.desc.text = data?.get(1).toString()
            Glide.with(binding.root)
                .load(data?.get(2).toString())
                .into(binding.imgPreview)

        binding.btnBack.setOnClickListener {
            fragmentManager?.popBackStack()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}