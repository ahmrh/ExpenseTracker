package com.dimastri.expensetracker.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.dimastri.expensetracker.R
import com.dimastri.expensetracker.adapter.SharedViewModel
import com.dimastri.expensetracker.tools.CustomFormatter

class FragmentHome () : Fragment(R.layout.fragment_home) {
  lateinit var textTotalSpending: TextView
  lateinit var textStats1: TextView
  lateinit var textStats2: TextView
  lateinit var textStats3: TextView

  private val sharedViewModel: SharedViewModel by activityViewModels()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val view = inflater.inflate(R.layout.fragment_home, container, false)

    textTotalSpending = view.findViewById(R.id.textTotalSpent)
    textStats1 = view.findViewById(R.id.textStats1)
    textStats2 = view.findViewById(R.id.textStats2)
    textStats3 = view.findViewById(R.id.textStats3)

    setStatistics()

    sharedViewModel.listExpense.observe(viewLifecycleOwner) {
      setStatistics()
    }
    return view
  }

  fun setStatistics() {
    val customFormatter = CustomFormatter()
    val totalSpending = customFormatter.formatCurrency(sharedViewModel.calculateTotalExpense())
    val totalSpendingThisMonth = customFormatter.formatCurrency(sharedViewModel.calculateTotalExpenseThisMonth())
    val totalSpendingThisWeek = customFormatter.formatCurrency(sharedViewModel.calculateTotalExpenseThisWeek())
    val itemCount = sharedViewModel.getNumExpenses()
    val numExpenses = "$itemCount items"

    textTotalSpending.text = totalSpending.toString()
    textStats1.text = totalSpendingThisMonth.toString()
    textStats2.text = totalSpendingThisWeek.toString()
    textStats3.text = numExpenses.toString()
  }
}