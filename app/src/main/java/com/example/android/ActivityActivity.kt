package com.example.android

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import com.example.android.fragments.ProfileFragment
import com.example.android.fragments.NewActivityFragment
import com.example.android.fragments.ActivityInProgressFragment
import com.example.android.fragments.TabFragment
import com.google.android.material.bottomnavigation.BottomNavigationView



class ActivityActivity : AppCompatActivity() {
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var fragmentContainer: FragmentContainerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activity)

        bottomNav = findViewById(R.id.bottom_nav)
        fragmentContainer = findViewById(R.id.fragment_container)
        val fabNewActivity = findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.fab_new_activity)

        fabNewActivity.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, NewActivityFragment.newInstance(), NewActivityFragment.TAG)
                .addToBackStack(null)
                .commit()
        }

        supportFragmentManager.addOnBackStackChangedListener {
            val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
            fabNewActivity.visibility = if (fragment is NewActivityFragment || fragment is ActivityInProgressFragment) View.GONE else View.VISIBLE
        }

        if (savedInstanceState == null) {
            // Первоначальная загрузка фрагмента активности
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, TabFragment(), "TabFragment")
                .commit()
        }

        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.activityFragment -> switchToFragment("TabFragment")
                R.id.profileFragment -> switchToFragment(ProfileFragment.TAG)
            }
            true
        }
    }

    private fun switchToFragment(tag: String) {
        val transaction = supportFragmentManager.beginTransaction()

        supportFragmentManager.fragments.forEach { transaction.hide(it) }

        val fragment = supportFragmentManager.findFragmentByTag(tag)

        if (fragment != null) {
            transaction.show(fragment)
        } else {
            val newFragment = when (tag) {
                "TabFragment" -> TabFragment()
                ProfileFragment.TAG -> ProfileFragment()
                else -> throw IllegalArgumentException("Unknown fragment tag")
            }
            transaction.add(R.id.fragment_container, newFragment, tag)
        }

        transaction.commit()
    }
}