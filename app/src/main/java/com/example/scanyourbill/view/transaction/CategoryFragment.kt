package com.example.scanyourbill.view.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.scanyourbill.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class CategoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager: ViewPager2 = view.findViewById(R.id.view_pager)
        val tabs: TabLayout = view.findViewById(R.id.tabs)

        // Initialize ViewPager2 adapter
        val adapter = CategorySectionsPagerAdapter(requireActivity())
        viewPager.adapter = adapter

        // Attach TabLayout with ViewPager2
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(CategorySectionsPagerAdapter.TAB_TITLES[position])
        }.attach()
    }
}