package com.t4zb.e_commerce.ui.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.t4zb.e_commerce.R
import com.t4zb.e_commerce.data.config.AppConfig
import com.t4zb.e_commerce.data.model.ProductRoom
import com.t4zb.e_commerce.databinding.FragmentBasketBinding
import com.t4zb.e_commerce.ui.adapter.BasketAdapter
import com.t4zb.e_commerce.ui.listener.DeleteBasketItemListener
import com.t4zb.e_commerce.ui.viewmodel.BasketViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BasketFragment : Fragment(), DeleteBasketItemListener {

    private lateinit var mContext: Context
    private lateinit var mBinding: FragmentBasketBinding
    private val basketViewModel: BasketViewModel by viewModels()
    private lateinit var basketAdapter: BasketAdapter
    private var globalTotalPrice: Double = 0.0

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

        mBinding.addToCartButton.setOnClickListener {
            if (globalTotalPrice > 0) {
                findNavController().navigate(R.id.action_navigation_basket_to_checkoutFragment)
            } else {
                Toast.makeText(
                    mContext,
                    "Please add any product on your basket",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setupRecyclerView() {
        basketAdapter = BasketAdapter(emptyList(), this)
        mBinding.basketRv.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = basketAdapter
        }
    }

    private fun observeViewModel() {
        basketViewModel.basket.observe(viewLifecycleOwner) { basket ->
            basket?.let {
                AppConfig.currentSelectedBasket = it
                it.basketProductList?.let { it1 ->
                    setTotalPrice(it1)
                }
                basketAdapter = BasketAdapter(it.basketProductList ?: emptyList(), this)
                mBinding.basketRv.adapter = basketAdapter
            }
        }

        basketViewModel.removeBasketResult.observe(viewLifecycleOwner) { result ->
            if (result) {
                AppConfig.userId?.let { basketViewModel.fetchBasketByUserId(it) }
            }
        }
    }

    private fun setTotalPrice(productList: List<ProductRoom>) {
        var totalPrice: Double = 0.0
        for (product in productList) {
            totalPrice += product.productPrice ?: 0.0
        }
        globalTotalPrice = totalPrice
        mBinding.totalPriceAmount.text = totalPrice.toString()
    }

    override fun onItemClicked(productRoom: ProductRoom) {
        if (AppConfig.userId != null) {
            basketViewModel.removeItemOrDeleteBasket(AppConfig.userId!!, productRoom)
        }

    }

}