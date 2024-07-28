package com.t4zb.e_commerce.ui.view.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.t4zb.e_commerce.R
import com.t4zb.e_commerce.data.config.AppConfig
import com.t4zb.e_commerce.databinding.FragmentComplateRegisterBinding
import com.t4zb.e_commerce.ui.viewmodel.CompleteRegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComplateRegisterFragment : Fragment() {

    private lateinit var mContext: Context
    private lateinit var mBinding: FragmentComplateRegisterBinding
    private val viewModel: CompleteRegisterViewModel by viewModels()
    private val pickImageRequestCode = 1997

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireActivity()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_complate_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentComplateRegisterBinding.bind(view)

        val genderOptions = listOf("Male", "Female", "Other")
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            genderOptions
        )
        mBinding.actvGender.setAdapter(adapter)

        mBinding.actvGender.setOnClickListener {
            mBinding.actvGender.showDropDown()
        }

        mBinding.btnEditImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, pickImageRequestCode)
        }
        viewModel.user.observe(viewLifecycleOwner, Observer { user ->
            user?.let {
                if (!user.user_picture.isNullOrBlank()) {
                    Glide.with(this)
                        .load(user.user_picture)
                        .placeholder(R.drawable.profile_picture_dumy) // Placeholder image
                        .error(R.drawable.profile_picture_dumy) // Error image
                        .into(mBinding.ivProfileImage)
                }
            }
        })

        mBinding.btnCompleteProfile.setOnClickListener {
            val phoneNumber = mBinding.etPhoneNumber.text.toString()
            val address = mBinding.etAddress.text.toString()
            val gender = mBinding.actvGender.text.toString()

            if (viewModel.isValid(phoneNumber, address, gender)) {
                viewModel.completeProfile(phoneNumber, address, gender)
                // show progress bar
            } else {
                Toast.makeText(mContext, "Please fill all fields correctly", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.completionResult.observe(viewLifecycleOwner, Observer { isSuccess ->
            if (isSuccess) {
                Toast.makeText(mContext, "Profile completed successfully", Toast.LENGTH_SHORT)
                    .show()
                findNavController().navigate(R.id.action_complateRegisterFragment_to_homeFragment)
            } else {
                Toast.makeText(mContext, "Profile completion failed", Toast.LENGTH_SHORT).show()
            }
        })
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImageRequestCode && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                AppConfig.userId?.let { viewModel.updateUserProfileImage(it, uri) }
            }
        }
    }

}