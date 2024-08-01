package com.t4zb.e_commerce.ui.view.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.t4zb.e_commerce.R
import com.t4zb.e_commerce.data.config.AppConfig
import com.t4zb.e_commerce.data.config.AppConfig.userId
import com.t4zb.e_commerce.data.model.ProfileOption
import com.t4zb.e_commerce.databinding.FragmentProfileBinding
import com.t4zb.e_commerce.ui.adapter.ProfileOptionsAdapter
import com.t4zb.e_commerce.ui.listener.ProfileOptionItemClickListener
import com.t4zb.e_commerce.ui.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(), ProfileOptionItemClickListener {
    private lateinit var mContext: Context
    private lateinit var mBinding: FragmentProfileBinding
    private val profileViewModel: ProfileViewModel by viewModels()
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
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentProfileBinding.bind(view)

        if (AppConfig.userId != null) {
            AppConfig.userId.let {
                profileViewModel.fetchUser(it!!)
            }
        }

        profileViewModel.user.observe(viewLifecycleOwner, Observer { user ->
            user?.let {
                mBinding.tvUserName.text = "${user.user_name} ${user.user_surname}"

                if (!user.user_picture.isNullOrBlank()) {
                    Glide.with(this)
                        .load(user.user_picture)
                        .placeholder(R.drawable.profile_picture_dumy)
                        .error(R.drawable.profile_picture_dumy)
                        .into(mBinding.ivProfileImage)
                }

                val profileOptions = listOf(
                    ProfileOption(R.drawable.ic_email, user.user_email ?: ""),
                    ProfileOption(R.drawable.ic_phone, user.user_number ?: ""),
                    ProfileOption(R.drawable.ic_order, "My Orders"),
                    ProfileOption(R.drawable.ic_address, user.user_address_id ?: ""),
                    ProfileOption(R.drawable.ic_privacy, "Privacy Policy"),
                    ProfileOption(R.drawable.ic_logout, "Log out")
                )

                mBinding.rvProfileOptions.layoutManager = LinearLayoutManager(mContext)
                mBinding.rvProfileOptions.adapter = ProfileOptionsAdapter(profileOptions, this)

                mBinding.rvProfileOptions.adapter
            }
        })

        mBinding.btnEditImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, pickImageRequestCode)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImageRequestCode && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                userId?.let { profileViewModel.updateUserProfileImage(it, uri) }
            }
        }
    }

    override fun onItemClicked(index: Int) {
        if (index == 2) { // order page
            findNavController().navigate(R.id.action_navigation_profile_to_orderFragment)
        }
    }
}