package com.example.scanyourbill.view.transaction

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.scanyourbill.R

class CategorySectionsPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    companion object {
        @StringRes
        val TAB_TITLES = intArrayOf(
            R.string.expense,
            R.string.income
        )
    }

    override fun getItemCount(): Int {
        return TAB_TITLES.size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CategoryExpenseFragment()
            1 -> CategoryIncomeFragment()
            else -> throw IllegalArgumentException("Invalid position $position")
        }
    }
}
