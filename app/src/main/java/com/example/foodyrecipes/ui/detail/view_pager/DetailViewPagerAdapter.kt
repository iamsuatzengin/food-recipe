package com.example.foodyrecipes.ui.detail.view_pager

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.foodyrecipes.ui.detail.ingredients.IngredientsFragment
import com.example.foodyrecipes.ui.detail.overview.OverviewFragment

class DetailViewPagerAdapter(
    fragment: Fragment,
    private val food: Bundle
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> {
                val overviewFragment = OverviewFragment()
                overviewFragment.arguments = food
                overviewFragment
            }
            1 -> {
                val ingredientsFragment = IngredientsFragment()
                ingredientsFragment.arguments = food
                ingredientsFragment
            }
            else -> Fragment()
        }
    }

}