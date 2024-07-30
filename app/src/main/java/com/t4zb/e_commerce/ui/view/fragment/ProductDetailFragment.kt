package com.t4zb.e_commerce.ui.view.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.t4zb.e_commerce.R
import com.t4zb.e_commerce.data.config.AppConfig
import com.t4zb.e_commerce.data.model.Basket
import com.t4zb.e_commerce.data.model.Product
import com.t4zb.e_commerce.data.model.ProductRoom
import com.t4zb.e_commerce.databinding.FragmentProductDetailBinding
import com.t4zb.e_commerce.ui.adapter.ProductImagesAdapter
import com.t4zb.e_commerce.ui.viewmodel.ProductDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    private lateinit var mContext: Context
    private lateinit var mBinding: FragmentProductDetailBinding
    private val productDetailViewModel: ProductDetailViewModel by viewModels()

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
            AppConfig.currentSelectedProduct = null
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

        productDetailViewModel.basketOperationResult.observe(
            viewLifecycleOwner,
            Observer { success ->
                if (success) {
                    Toast.makeText(mContext, "Basket updated successfully", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(mContext, "Failed to update basket", Toast.LENGTH_SHORT).show()
                }
            })

        mBinding.addToCartButton.setOnClickListener {

            val selectedRadioButtonId = mBinding.sizeRadioGroup.checkedRadioButtonId
            val selectedSize =
                mBinding.root.findViewById<RadioButton>(selectedRadioButtonId)?.text.toString()

            if (selectedRadioButtonId != -1 && selectedSize.isNotEmpty()) {
                val product = AppConfig.currentSelectedProduct?.copy(productSize = selectedSize)

                if (product != null) {
                    val productRoom = convertProductToProductRoom(product)
                    val basket = Basket(
                        userId = AppConfig.userId ?: "",
                        createDate = Date(),
                        basketProductList = listOf(productRoom)
                    )

                    productDetailViewModel.insertOrUpdateBasket(basket)
                }
            } else {
                Toast.makeText(mContext, "Please choose any size", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun convertProductToProductRoom(product: Product): ProductRoom {
        return ProductRoom(
            productIdInt = 0,
            productId = product.productId,
            productName = product.productName,
            productDescription = product.productDescription,
            productPrice = product.productPrice,
            productCategory = product.productCategory,
            productStockCount = product.productStockCount,
            productPicture = product.productPictureList?.firstOrNull(),
            productOwner = product.productOwner,
            productColor = product.productColor,
            productSize = product.productSize,
            productLabel = product.productLabel,
            star = product.star
        )
    }

}