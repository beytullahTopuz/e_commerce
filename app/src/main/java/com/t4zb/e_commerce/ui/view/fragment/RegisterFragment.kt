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
import com.t4zb.e_commerce.databinding.FragmentRegisterBinding
import com.t4zb.e_commerce.ui.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var mContext: Context
    private lateinit var mBinding: FragmentRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mContext = requireActivity()
        return inflater.inflate(R.layout.fragment_register, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding = FragmentRegisterBinding.bind(view)

        viewModel.isValid.observe(viewLifecycleOwner, Observer { isValid ->
            mBinding.btnSignUp.isEnabled = isValid
        })

        viewModel.registrationResult.observe(viewLifecycleOwner, Observer { userResultType ->
            if (userResultType.status) {
                Toast.makeText(requireContext(), "User registered successfully", Toast.LENGTH_SHORT)
                    .show()
                userResultType.userId?.let {
                    AppConfig.userId = it
                }
                findNavController().navigate(R.id.action_registerFragment_to_complateRegisterFragment)
            } else {
                Toast.makeText(requireContext(), "Registration failed", Toast.LENGTH_SHORT).show()
            }
        })

        mBinding.btnGoogleSignUp.setOnClickListener {
            //will be develop
          //  signIn()
        }

        mBinding.btnSignUp.setOnClickListener {
            viewModel.registerUser()
        }

        mBinding.etName.doAfterTextChanged { text ->
            viewModel.setUserName(text.toString())
        }

        mBinding.etSurname.doAfterTextChanged { text ->
            viewModel.setUserSurname(text.toString())
        }

        mBinding.etEmail.doAfterTextChanged { text ->
            viewModel.setEmail(text.toString())
        }

        mBinding.etPassword.doAfterTextChanged { text ->
            viewModel.setPassword(text.toString())
        }
    }

}

