package com.t4zb.e_commerce.ui.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.t4zb.e_commerce.R
import com.t4zb.e_commerce.databinding.FragmentMarketBinding
import com.t4zb.e_commerce.ui.adapter.ProductAdapter
import com.t4zb.e_commerce.ui.viewmodel.MarketViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MarketFragment : Fragment() {

    private lateinit var mContext: Context
    private lateinit var mBinding: FragmentMarketBinding
    private val marketViewModel: MarketViewModel by viewModels()
    private lateinit var productAdapter: ProductAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireActivity()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_market, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentMarketBinding.bind(view)

        mBinding.rvFlashSale.layoutManager = GridLayoutManager(mContext, 2)
        marketViewModel.products.observe(viewLifecycleOwner, Observer { products ->
            productAdapter = ProductAdapter(products)
            mBinding.rvFlashSale.adapter = productAdapter
        })

    }

}