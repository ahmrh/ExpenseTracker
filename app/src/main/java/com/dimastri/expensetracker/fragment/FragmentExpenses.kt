package com.dimastri.expensetracker.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dimastri.expensetracker.R
import com.dimastri.expensetracker.activity.AddExpenseActivity
import com.dimastri.expensetracker.adapter.ListExpenseAdapter
import com.dimastri.expensetracker.adapter.SharedViewModel
import com.dimastri.expensetracker.model.Category
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

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
      if (result.resultCode == Activity.RESULT_OK) {
        val data: Intent? = result.data

        val title = data?.getStringExtra("title")
        val category = data?.getStringExtra("category")
        val amount = data?.getLongExtra("nominal", -1)
        val date = data?.getLongExtra("date", -1)
        val selectedDate = Date(date!!)

        if (category != null && amount != null && title != null) {
          addExpense(title, amount, category, selectedDate)
        }
      }
    }

    btnAdd.setOnClickListener {
      val intent = Intent(view.context, AddExpenseActivity::class.java)
      val listCategory = sharedViewModel.listCategory.value?.map { it.name }?.toTypedArray()
      intent.putExtra("listCategory", listCategory)
      resultLauncher.launch(intent)
    }

    sharedViewModel.listExpense.observe(viewLifecycleOwner, {
      listExpenseAdapter.notifyDataSetChanged()
    })

    return view
  }

  fun addExpense(title: String, nominal: Long, category: String, date: Date) {
    sharedViewModel.createNewExpenses(title, nominal, null, category, date)
    Toast.makeText(context, "Expense added", Toast.LENGTH_SHORT).show()
  }
}