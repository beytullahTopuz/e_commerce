package com.t4zb.e_commerce.ui.view.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.t4zb.e_commerce.R
import com.t4zb.e_commerce.databinding.FragmentHomeBinding
import com.t4zb.e_commerce.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var mContext: Context
    private lateinit var mBinding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireActivity()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentHomeBinding.bind(view)

        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavigationView = mBinding.bottomNavigation

        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_market -> {
                    navController.navigate(R.id.navigation_market)
                    true
                }

                R.id.orderFragment -> {
                    navController.navigate(R.id.orderFragment)
                    true
                }

                R.id.navigation_basket -> {
                    navController.popBackStack(R.id.navigation_basket, true)
                    navController.navigate(R.id.navigation_basket)
                    true
                }

                R.id.navigation_profile -> {
                    navController.navigate(R.id.navigation_profile)
                    true
                }

                else -> false
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.productDetailFragment,
                R.id.checkoutFragment,
                R.id.paymentFragment -> hideBottomNav()

                else -> showBottomNav()
            }
        }
    }

    private fun hideBottomNav() {
        mBinding.bottomNavigation.visibility = View.GONE
    }

    private fun showBottomNav() {
        mBinding.bottomNavigation.visibility = View.VISIBLE
    }
}
