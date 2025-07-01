package com.example.android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.android.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import androidx.viewpager2.widget.ViewPager2

class TabFragment : Fragment() {
    private val tabTitles = listOf("Мои", "Пользователей")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        val viewPager = view.findViewById<ViewPager2>(R.id.view_pager)
        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = 2
            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> MyActivitiesFragment()
                    1 -> UsersActivitiesFragment()
                    else -> throw IllegalArgumentException()
                }
            }
        }
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

    companion object {
        fun newInstance(tabType: String): TabFragment {
            val fragment = TabFragment()
            val args = Bundle()
            args.putString("tab_type", tabType)
            fragment.arguments = args
            return fragment
        }
    }
}

class UsersActivitiesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Здесь можно реализовать отображение активностей других пользователей
        val view = inflater.inflate(android.R.layout.simple_list_item_1, container, false)
        return view
    }
}