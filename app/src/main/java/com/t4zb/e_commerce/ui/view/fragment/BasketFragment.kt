package com.t4zb.e_commerce.ui.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.t4zb.e_commerce.R
import com.t4zb.e_commerce.data.config.AppConfig
import com.t4zb.e_commerce.databinding.FragmentBasketBinding
import com.t4zb.e_commerce.ui.adapter.BasketAdapter
import com.t4zb.e_commerce.ui.viewmodel.BasketViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BasketFragment : Fragment() {

    private lateinit var mContext: Context
    private lateinit var mBinding: FragmentBasketBinding
    private val basketViewModel: BasketViewModel by viewModels()
    private lateinit var basketAdapter: BasketAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireActivity()
    }

    override fun onResume() {
        super.onResume()
        AppConfig.userId?.let { basketViewModel.fetchBasketByUserId(it) }
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

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        basketAdapter = BasketAdapter(emptyList())
        mBinding.basketRv.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = basketAdapter
        }
    }

    private fun observeViewModel() {
        basketViewModel.basket.observe(viewLifecycleOwner) { basket ->
            basket?.let {
                basketAdapter = BasketAdapter(it.basketProductList ?: emptyList())
                mBinding.basketRv.adapter = basketAdapter
            }
        }
    }

}