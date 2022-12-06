package com.dimastri.expensetracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dimastri.expensetracker.adapter.expense.ExpenseViewModel
import com.dimastri.expensetracker.adapter.expense.ListExpense
import com.dimastri.expensetracker.adapter.expense.ListExpenseAdapter
import com.dimastri.expensetracker.model.Expense
import java.util.*

class FragmentExpenses () : Fragment(R.layout.fragment_expenses) {
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val view = inflater.inflate(R.layout.fragment_expenses, container, false)
    val btnAdd = view.findViewById<View>(R.id.buttonAddExpense)

    val expenseViewModel = ViewModelProvider(this)[ExpenseViewModel::class.java]
    val rvExpense: RecyclerView = view.findViewById(R.id.list_expenses)
    val listExpenseAdapter = ListExpenseAdapter(expenseViewModel.getListExpense())
    rvExpense.adapter = listExpenseAdapter
    rvExpense.layoutManager = LinearLayoutManager(view.context)

    btnAdd.setOnClickListener {
      val expense = Expense("Tambahan Expense", 100000, "test", "test", Date())
      expenseViewModel.addExpense(expense)
    }

    expenseViewModel.lvListExpense.observe(viewLifecycleOwner, {
      listExpenseAdapter.notifyDataSetChanged()
    })
    return view
  }
}