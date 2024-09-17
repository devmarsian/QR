package com.testask.kiosktabletapp.presentation.fragments

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.testask.kiosktabletapp.databinding.FragmentQRBinding
import com.testask.kiosktabletapp.domain.model.DataResult
import com.testask.kiosktabletapp.presentation.viewmodels.UserViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class QRFragment : Fragment() {

    private var _binding: FragmentQRBinding? = null
    private val binding get() = _binding!!
    private val userViewModel: UserViewModel by activityViewModel()
    private lateinit var userDataObserver: Observer<DataResult>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQRBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        userViewModel.userDataState.removeObserver(userDataObserver)
        userViewModel.handleKioskMode(requireActivity(), false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener {
            userViewModel.clearNavigationState()
            findNavController().popBackStack()
        }

        userDataObserver = Observer { result ->
            when (result) {
                is DataResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.userDataLayout.visibility = View.GONE
                    binding.errorMessage.visibility = View.GONE
                }
                is DataResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.userDataLayout.visibility = View.VISIBLE
                    binding.errorMessage.visibility = View.GONE
                    binding.user = result.data.data.user
                    binding.payload = result.data.data.qr.payload
                    userViewModel.updateQRCode(result.data.data.qr.key, result.data.data.qr.payload)
                    if (result.data.data.is_blocked) {
                        userViewModel.handleKioskMode(requireActivity(), true)
                    } else {
                        userViewModel.handleKioskMode(requireActivity(), false)
                    }
                }
                is DataResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.userDataLayout.visibility = View.GONE
                    binding.errorMessage.visibility = View.VISIBLE
                    binding.errorMessage.text = result.message
                }
            }
        }

        userViewModel.userDataState.observe(viewLifecycleOwner, userDataObserver)

        userViewModel.qrBitmap.observe(viewLifecycleOwner) { bitmap ->
            binding.qrCodeImageView.setImageBitmap(bitmap)
        }
    }
}

