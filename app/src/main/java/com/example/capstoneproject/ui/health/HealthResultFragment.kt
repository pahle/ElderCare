package com.example.capstoneproject.ui.health

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.capstoneproject.R
import com.example.capstoneproject.data.local.ArchiveEntity
import com.example.capstoneproject.databinding.FragmentHealthResultBinding
import com.example.capstoneproject.utils.Helper.Companion.DATA
import com.example.capstoneproject.utils.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HealthResultFragment : Fragment() {

    private var _binding: FragmentHealthResultBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HealthViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                AlertDialog.Builder(requireContext()).apply {
                    setMessage(getString(R.string.exit))
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
        _binding = FragmentHealthResultBinding.inflate(inflater, container, false)
        val data = arguments?.getStringArrayList(DATA)

        binding.result.text = data?.get(0).toString()
        binding.treatment.text = data?.get(2).toString()

        binding.result.setOnClickListener {
            val mBundle = Bundle()
            val arrayList = ArrayList<String>()
            arrayList.add(data?.get(0).toString())
            arrayList.add(data?.get(3).toString())
            arrayList.add(data?.get(1).toString())
            mBundle.putStringArrayList(DATA, arrayList)
            findNavController().navigate(R.id.action_navigation_healthresult_to_navigation_healththird, mBundle)
        }
        binding.btnDone.setOnClickListener {
            viewModel.deleteAll()
            viewModel.getUser().observe(viewLifecycleOwner) {
                viewModel.setArchive(ArchiveEntity(0,it.name,data?.get(0).toString(), SimpleDateFormat("dd MMM yyyy - HH:mm", Locale.getDefault()).format(Date()), data?.get(2).toString()))
                Log.d("ASU", "keren")
                findNavController().navigate(R.id.action_navigation_healthresult_to_navigation_health)
            }
        }
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}