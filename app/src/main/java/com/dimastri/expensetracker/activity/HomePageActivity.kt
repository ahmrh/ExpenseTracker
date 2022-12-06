package com.dimastri.expensetracker.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import com.dimastri.expensetracker.fragment.FragmentCategory
import com.dimastri.expensetracker.fragment.FragmentExpenses
import com.dimastri.expensetracker.fragment.FragmentHome
import com.dimastri.expensetracker.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomePageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottonNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val homePage = FragmentHome()
        val expensesPage = FragmentExpenses()
        val categoryPage = FragmentCategory()

        setActiveFragment(homePage)

        bottonNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> setActiveFragment(homePage)
                R.id.expense -> setActiveFragment(expensesPage)
                R.id.category -> setActiveFragment(categoryPage)
            }
            true
        }
    }

    fun setActiveFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment, fragment)
            commit()
        }
    }
}
