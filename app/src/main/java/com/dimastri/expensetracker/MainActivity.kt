package com.dimastri.expensetracker

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dimastri.expensetracker.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

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