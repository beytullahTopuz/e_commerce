package com.t4zb.e_commerce.ui.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.t4zb.e_commerce.R
import com.t4zb.e_commerce.data.config.AppConfig
import com.t4zb.e_commerce.databinding.FragmentLoginBinding
import com.t4zb.e_commerce.ui.viewmodel.LoginViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var mContext: Context
    private lateinit var mBinding: FragmentLoginBinding
    private val viewModel: LoginViewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireActivity()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding = FragmentLoginBinding.bind(view)

        mBinding.tvSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        viewModel.isValid.observe(viewLifecycleOwner, Observer { isValid ->
            mBinding.btnSignIn.isEnabled = isValid
        })

        viewModel.loginResult.observe(viewLifecycleOwner, Observer { resultTpye ->
            if (resultTpye.status) {
                Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT).show()
                resultTpye.userId?.let {
                    AppConfig.userId = it
                }
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            } else {
                Toast.makeText(requireContext(), "Login failed", Toast.LENGTH_SHORT).show()
            }
        })

        mBinding.btnSignIn.setOnClickListener {
            viewModel.loginUser()
        }

        mBinding.etEmail.doAfterTextChanged { text ->
            viewModel.setEmail(text.toString())
        }

        mBinding.etPassword.doAfterTextChanged { text ->
            viewModel.setPassword(text.toString())
        }
    }
}