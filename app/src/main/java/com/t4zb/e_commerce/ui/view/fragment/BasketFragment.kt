package com.t4zb.e_commerce.ui.view.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.t4zb.e_commerce.R
import com.t4zb.e_commerce.databinding.FragmentBasketBinding
import com.t4zb.e_commerce.databinding.FragmentProfileBinding


class BasketFragment : Fragment() {

    private lateinit var mContext: Context
    private lateinit var mBinding: FragmentBasketBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireActivity()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_basket, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentBasketBinding.bind(view)


    }
}