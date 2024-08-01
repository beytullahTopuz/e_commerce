package com.t4zb.e_commerce.ui.view.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.t4zb.e_commerce.R
import com.t4zb.e_commerce.data.config.AppConfig
import com.t4zb.e_commerce.data.model.Order
import com.t4zb.e_commerce.databinding.FragmentOrderBinding
import com.t4zb.e_commerce.ui.adapter.OrderAdapter
import com.t4zb.e_commerce.ui.viewmodel.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OrderFragment : Fragment() {

    private lateinit var mContext: Context
    private lateinit var mBinding: FragmentOrderBinding
    private val orderViewModel: OrderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireActivity()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentOrderBinding.bind(view)

        val _userId = AppConfig.userId
        if (_userId != null) {
            orderViewModel.getOrderListByUserId(_userId)
        }

        orderViewModel.orderListResult.observe(viewLifecycleOwner) { result ->
            result.fold(onSuccess = { orderList ->
                setupRecyclerView(orderList)
            }, onFailure = { error ->
            })
        }
    }

    private fun setupRecyclerView(orderList: List<Order>) {
        val orderAdapter = OrderAdapter(orderList)
        mBinding.oderRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = orderAdapter
        }
    }
}