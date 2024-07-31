package com.t4zb.e_commerce.ui.view.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.t4zb.e_commerce.R
import com.t4zb.e_commerce.data.config.AppConfig
import com.t4zb.e_commerce.data.model.Basket
import com.t4zb.e_commerce.data.model.Product
import com.t4zb.e_commerce.data.model.ProductRoom
import com.t4zb.e_commerce.databinding.FragmentCheckoutBinding
import com.t4zb.e_commerce.databinding.FragmentHomeBinding
import com.t4zb.e_commerce.ui.adapter.BasketAdapter
import com.t4zb.e_commerce.ui.listener.DeleteBasketItemListener
import com.t4zb.e_commerce.ui.viewmodel.CheckoutViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckoutFragment : Fragment(), DeleteBasketItemListener {

    private lateinit var mContext: Context
    private lateinit var mBinding: FragmentCheckoutBinding
    private val checkoutViewModel: CheckoutViewModel by viewModels()
    private lateinit var basketAdapter: BasketAdapter
    private var globalTotalPrice: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireActivity()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_checkout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentCheckoutBinding.bind(view)

        val basket = AppConfig.currentSelectedBasket
        basket?.let { initializeUI(it) }
    }

    private fun initializeUI(basket: Basket) {

        basket.userId?.let { checkoutViewModel.getUserAddress(it) }
        var totalPrice: Double = 0.0
        val productRoomList = basket.basketProductList
        if (productRoomList != null) {
            for (basketProduct in productRoomList) {
                totalPrice += (basketProduct.productPrice ?: 0.0)
            }
            globalTotalPrice = totalPrice

            setupRecyclerView(productRoomList)
            mBinding.totalPriceAmount.text = totalPrice.toString()
        }

        checkoutViewModel.user.observe(viewLifecycleOwner) {
            mBinding.tvShippingAddress.text = it.user_address_id ?: ""

        }

        mBinding.addToCartButton.setOnClickListener {
            if (globalTotalPrice > 0) {
                val amount: Double = 123.45
                val action =
                    CheckoutFragmentDirections.actionCheckoutFragmentToPaymentFragment(
                        amount.toFloat(),
                        basket.basketId
                    )
                findNavController().navigate(action)

            } else {
                Toast.makeText(
                    mContext,
                    "Please add any product on your basket",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setupRecyclerView(products: List<ProductRoom>) {
        basketAdapter = BasketAdapter(products, this)
        basketAdapter.setDecreaseQuantityVisibility(false)
        mBinding.rvOrderList.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = basketAdapter
        }
    }

    override fun onItemClicked(productRoom: ProductRoom) {
        // no need to implement
    }

}