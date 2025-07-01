package com.example.android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.ActivityAdapter
import com.example.android.R

class TabFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView

    companion object {
        private const val ARG_TAB_TYPE = "tab_type"

        fun newInstance(tabType: String): TabFragment {
            val fragment = TabFragment()
            val args = Bundle()
            args.putString(ARG_TAB_TYPE, tabType)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val tabType = arguments?.getString(ARG_TAB_TYPE) ?: ""
        val adapter = ActivityAdapter(tabType, this::onActivityClicked)
        recyclerView.adapter = adapter

        // Here you would normally load your data from a ViewModel or other source
        val sampleData = listOf(
            ActivityAdapter.ActivityItem.DateSection("Вчера"),
            ActivityAdapter.ActivityItem.Activity("14 часов назад", "Серфинг", "14.32 км", "2 часа 46 минут"),
            ActivityAdapter.ActivityItem.DateSection("Май 2022 года"),
            ActivityAdapter.ActivityItem.Activity("29.05.2022", "Велосипед", "1 км", "1 час")
        )

        adapter.submitList(sampleData)
    }

    private fun onActivityClicked(activity: ActivityAdapter.ActivityItem.Activity) {
        val fragment = ActivityDetailFragment.newInstance(activity)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}