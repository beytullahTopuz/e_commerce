package com.t4zb.e_commerce.ui.view.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.t4zb.e_commerce.R
import com.t4zb.e_commerce.data.config.AppConfig
import com.t4zb.e_commerce.data.model.Product
import com.t4zb.e_commerce.databinding.FragmentProductDetailBinding
import com.t4zb.e_commerce.ui.adapter.ProductImagesAdapter

class ProductDetailFragment : Fragment() {

    private lateinit var mContext: Context
    private lateinit var mBinding: FragmentProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireActivity()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentProductDetailBinding.bind(view)

        AppConfig.currentSelectedProduct.let {
            if (it != null) {
                initializeUI(it)
            }
        }
    }

    private fun initializeUI(product: Product) {
        mBinding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        mBinding.productNameTextView.text = product.productName
        mBinding.productDescriptionTextView.text = product.productDescription
        mBinding.totalPriceAmount.text = product.productPrice.toString()
        mBinding.productStar.text = product.star.toString()

        if (product.productPictureList != null) {
            val adapter = ProductImagesAdapter(product.productPictureList)
            mBinding.productImagesRecyclerView.adapter = adapter
            mBinding.productImagesRecyclerView.layoutManager =
                LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        }

        if (!product.productSizeList.isNullOrEmpty()) {
            val sizeRadios = listOf(
                mBinding.sizeRadioButton1,
                mBinding.sizeRadioButton2,
                mBinding.sizeRadioButton3,
                mBinding.sizeRadioButton4,
                mBinding.sizeRadioButton5,
                mBinding.sizeRadioButton6
            )
            for ((index, sizeValue) in product.productSizeList.withIndex()) {
                if (index < sizeRadios.size) {
                    sizeRadios[index].text = sizeValue
                    sizeRadios[index].visibility = View.VISIBLE
                }
            }
        }



        mBinding.addToCartButton.setOnClickListener {
        }
    }

}