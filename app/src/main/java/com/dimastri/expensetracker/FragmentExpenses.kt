package com.dimastri.expensetracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dimastri.expensetracker.adapter.ListExpenseAdapter
import com.dimastri.expensetracker.adapter.SharedViewModel
import com.dimastri.expensetracker.model.Expense
import java.util.*

class FragmentExpenses () : Fragment(R.layout.fragment_expenses) {

  private val sharedViewModel: SharedViewModel by activityViewModels()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val view = inflater.inflate(R.layout.fragment_expenses, container, false)
    val btnAdd = view.findViewById<View>(R.id.buttonAddExpense)

    val rvExpense: RecyclerView = view.findViewById(R.id.list_expenses)
    val listExpenseAdapter = ListExpenseAdapter(sharedViewModel.listExpense)
    rvExpense.adapter = listExpenseAdapter
    rvExpense.layoutManager = LinearLayoutManager(view.context)

    btnAdd.setOnClickListener {
      sharedViewModel.createNewExpenses("Tambahan", 10000, "Tambahan", "Test", Date())
    }

    sharedViewModel.listExpense.observe(viewLifecycleOwner, {
      listExpenseAdapter.notifyDataSetChanged()
    })

    return view
  }
}