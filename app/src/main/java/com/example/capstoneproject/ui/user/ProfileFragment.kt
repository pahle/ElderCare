package com.example.capstoneproject.ui.user

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.capstoneproject.R
import com.example.capstoneproject.data.local.ArchiveEntity
import com.example.capstoneproject.databinding.FragmentProfileBinding
import com.example.capstoneproject.ui.archive.ArchiveActivity
import com.example.capstoneproject.utils.ViewModelFactory

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<AuthViewModel> {
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
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        viewModel.getPrefUser().observe(viewLifecycleOwner) {
            binding.profileDate.text = it.date
            binding.profileGender.text = it.gender
            binding.profileName.text = it.name
            binding.profileNumber.text = it.number
            binding.profileNickname.text = it.nickname
        }

        binding.btnLogOut.setOnClickListener {
            AlertDialog.Builder(requireContext()).apply {
                setTitle(getString(R.string.logout))
                setMessage("Yakin ingin keluar dari akun?")
                setPositiveButton("Yes") { _, _ ->
                    viewModel.delete()
                    val intent = Intent(requireContext(), RegistActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                setNegativeButton("No", null)
            }.show()
        }

        binding.btnArchieve.setOnClickListener {
            val intent = Intent(requireContext(), ArchiveActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}