package com.testask.kiosktabletapp.presentation.fragments

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.testask.kiosktabletapp.R
import com.testask.kiosktabletapp.databinding.FragmentLoginBinding
import com.testask.kiosktabletapp.domain.model.LoginResult
import com.testask.kiosktabletapp.presentation.viewmodels.UserViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val userViewModel: UserViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel.loginState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is LoginResult.Loading -> {
                    binding.textView.text = "Loading..."
                }
                is LoginResult.Success -> {
                    val navOptions = NavOptions.Builder()
                        .setPopUpTo(R.id.loginFragment, false)
                        .build()
                    findNavController().navigate(R.id.action_loginFragment_to_QRFragment, null, navOptions)
                }
                is LoginResult.Error -> {
                    binding.textView.text = result.message
                }
            }
        }
        binding.loginButton.setOnClickListener {
            val login = binding.email.text.toString()
            val pass = binding.password.text.toString()
            userViewModel.loginUser(login, pass)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}