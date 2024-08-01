package com.t4zb.e_commerce.ui.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager.widget.ViewPager
import com.t4zb.e_commerce.R
import com.t4zb.e_commerce.data.config.AppConfig
import com.t4zb.e_commerce.data.model.Product
import com.t4zb.e_commerce.databinding.FragmentMarketBinding
import com.t4zb.e_commerce.ui.adapter.ProductAdapter
import com.t4zb.e_commerce.ui.adapter.ProductViewPagerAdapter
import com.t4zb.e_commerce.ui.listener.ProductItemClickListener
import com.t4zb.e_commerce.ui.viewmodel.MarketViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MarketFragment : Fragment(), ProductItemClickListener {

    private lateinit var mContext: Context
    private lateinit var mBinding: FragmentMarketBinding
    private val marketViewModel: MarketViewModel by viewModels()
    private lateinit var productAdapter: ProductAdapter
    private lateinit var productViewPagerAdapter: ProductViewPagerAdapter

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
            productAdapter = ProductAdapter(products, this)
            mBinding.rvFlashSale.adapter = productAdapter
        })

        marketViewModel.popularProducts.observe(viewLifecycleOwner, Observer { products ->
            productViewPagerAdapter = ProductViewPagerAdapter(products, this)
            mBinding.viewPager.adapter = productViewPagerAdapter
            setupViewPagerIndicator(products.size)
        })

        mBinding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                updateIndicators(position)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

    }

    private fun setupViewPagerIndicator(size: Int) {
        val indicators = arrayOfNulls<ImageView>(size)
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(8, 0, 8, 0)

        for (i in indicators.indices) {
            indicators[i] = ImageView(mContext)
            indicators[i]?.setImageDrawable(
                ContextCompat.getDrawable(
                    mContext,
                    if (i == 0) R.drawable.selected_dot else R.drawable.default_dot
                )
            )
            indicators[i]?.layoutParams = params
            mBinding.dotsIndicator.addView(indicators[i])
        }
    }

    private fun updateIndicators(position: Int) {
        val childCount = mBinding.dotsIndicator.childCount
        for (i in 0 until childCount) {
            val imageView = mBinding.dotsIndicator.getChildAt(i) as ImageView
            imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    mContext,
                    if (i == position) R.drawable.selected_dot else R.drawable.default_dot
                )
            )
        }
    }

    override fun itemClicked(itemIndex: Int, product: Product) {
        AppConfig.currentSelectedProduct = product
        findNavController().navigate(R.id.action_navigation_market_to_productDetailFragment)
    }


}