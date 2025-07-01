package com.example.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import com.example.android.fragments.ActivityFragment
import com.example.android.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class ActivityActivity : AppCompatActivity() {
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var fragmentContainer: FragmentContainerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activity)

        bottomNav = findViewById(R.id.bottom_nav)
        fragmentContainer = findViewById(R.id.fragment_container)

        if (savedInstanceState == null) {
            // Первоначальная загрузка фрагмента активности
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, ActivityFragment(), ActivityFragment.TAG)
                .commit()
        }

        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.activityFragment -> switchToFragment(ActivityFragment.TAG)
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
                ActivityFragment.TAG -> ActivityFragment()
                ProfileFragment.TAG -> ProfileFragment()
                else -> throw IllegalArgumentException("Unknown fragment tag")
            }
            transaction.add(R.id.fragment_container, newFragment, tag)
        }

        transaction.commit()
    }
}