package com.example.capstoneproject.ui.health

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstoneproject.R
import com.example.capstoneproject.data.local.SymptomsEntity
import com.example.capstoneproject.databinding.FragmentHealthSecBinding
import com.example.capstoneproject.utils.Helper.Companion.DATA
import com.example.capstoneproject.utils.Result
import com.example.capstoneproject.utils.ViewModelFactory
import com.google.gson.annotations.SerializedName

class HealthFragmentSec : Fragment() {

    private var _binding: FragmentHealthSecBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HealthViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }
    private var symptoms = arrayListOf<String>()

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

    data class DataWrapper(
        @SerializedName("symptoms") val data: List<String>
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHealthSecBinding.inflate(inflater, container, false)
        val data = arguments?.getStringArrayList(DATA)

        viewModel.searchSymptoms(data?.get(0).toString()).observe(viewLifecycleOwner) {
            recylerView(it, data)
        }

        binding.btnNext.setOnClickListener {
            data?.removeFirst()
            if(data?.isEmpty() == true) {
                viewModel.predict(symptoms).observe(viewLifecycleOwner) { state ->
                    when (state) {
                        is Result.Loading -> {
                        }
                        is Result.Success -> {
                            val mBundle = Bundle()
                            val arrayList = ArrayList<String>()
                            arrayList.add(state.data?.translatedDisease.toString())
                            arrayList.add(state.data?.imageUrl.toString())
                            arrayList.add(state.data?.treatment.toString())
                            arrayList.add(state.data?.description.toString())
                            mBundle.putStringArrayList(DATA, arrayList)
                            findNavController().navigate(
                                R.id.action_navigation_healthsec_to_navigation_healthresult,
                                mBundle
                            )
                        }
                        is Result.Error -> {
                            val error = state.error
                            Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
                        }
                        else -> {}
                    }
                }
            } else {
                binding.title.text = resources.getString(R.string.health_title, data?.get(0).toString())
                viewModel.searchSymptoms(data?.get(0).toString()).observe(viewLifecycleOwner) {
                    recylerView(it, data)
                }
            }
        }

        return binding.root
    }

    private fun recylerView(listSymptoms: List<SymptomsEntity>, data: ArrayList<String>?) {
        val healthAdapter = HealthAdapter(listSymptoms, requireContext())

        binding.listhealth.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = healthAdapter
        }
        binding.title.text = resources.getString(R.string.health_title, data?.get(0).toString())

        healthAdapter.setOnItemClickCallback(object : HealthAdapter.OnItemClickCallback {
            override fun onItemClicked(data: SymptomsEntity) {
                val list = ArrayList<String>()
                list.add(data.translatedSymptom)
                list.add(data.description)
                list.add(data.imageUrl)
                viewModel.getSymptomsDesc(list)
                val mBundle = Bundle()
                mBundle.putStringArrayList(DATA, list)
                findNavController().navigate(
                    R.id.action_navigation_healthsec_to_navigation_healththird,
                    mBundle
                )
            }

            override fun onItemClickListener(data: SymptomsEntity) {
                symptoms.add(data.symptom)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}