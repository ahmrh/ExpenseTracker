package com.dimastri.expensetracker.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dimastri.expensetracker.R
import com.dimastri.expensetracker.activity.AddExpenseActivity
import com.dimastri.expensetracker.adapter.ListExpenseAdapter
import com.dimastri.expensetracker.adapter.SharedViewModel
import java.util.*

class FragmentExpenses() : Fragment(R.layout.fragment_expenses), SearchView.OnQueryTextListener {

  lateinit var rvExpense: RecyclerView
  lateinit var listExpenseAdapter: ListExpenseAdapter
  private val sharedViewModel: SharedViewModel by activityViewModels()

  @SuppressLint("NotifyDataSetChanged")
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val view = inflater.inflate(R.layout.fragment_expenses, container, false)
    val btnAdd = view.findViewById<View>(R.id.buttonAddExpense)

    rvExpense = view.findViewById(R.id.list_expenses)
    listExpenseAdapter = ListExpenseAdapter(sharedViewModel.listExpense)
    rvExpense.adapter = listExpenseAdapter
    rvExpense.layoutManager = LinearLayoutManager(view.context)

    // Intent launcher for AddExpenseActivity
    val resultLauncher =
      registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
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

    // Add expense button
    btnAdd.setOnClickListener {
      val intent = Intent(view.context, AddExpenseActivity::class.java)
      val listCategory = sharedViewModel.listCategory.value?.map { it.name }?.toTypedArray()
      intent.putExtra("listCategory", listCategory)
      resultLauncher.launch(intent)
    }

    // observe data change and update the list
    sharedViewModel.listExpense.observe(viewLifecycleOwner) {
      listExpenseAdapter.notifyDataSetChanged()
    }

    return view
  }

  private fun addExpense(title: String, nominal: Long, category: String, date: Date) {
    sharedViewModel.createNewExpenses(title, nominal, null, category, date)
    Toast.makeText(context, "Expense added", Toast.LENGTH_SHORT).show()
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.search_menu, menu)

    val search = menu.findItem(R.id.item_search)
    val searchView = search?.actionView as SearchView

    Toast.makeText(context, "Search", Toast.LENGTH_SHORT).show()

//    searchView.isSubmitButtonEnabled = true
//    searchView.setOnQueryTextListener(this)
    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
      override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
          Toast.makeText(context, "onQueryTextSubmit: $query", Toast.LENGTH_LONG).show()
//          searchExpense(query)
        }
        return true
      }

      override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
          Toast.makeText(context, "onQueryTextChange: $newText", Toast.LENGTH_LONG).show()
//          searchExpense(newText)
        }
        return true
      }
    })

    return super.onCreateOptionsMenu(menu, inflater)
  }

//  override fun onQueryTextSubmit(query: String?): Boolean {
//    if (query != null) {
//      val item = sharedViewModel.searchExpense(query)
//      listExpenseAdapter.setFilter(item)
//    }
//    return true
//  }
//
//  override fun onQueryTextChange(query: String?): Boolean {
//    if (query != null) {
//      Toast.makeText(context, "Your Search : $query", Toast.LENGTH_SHORT).show()
//      val item = sharedViewModel.searchExpense(query)
//      listExpenseAdapter.setFilter(item)
//    }
//    return true
//  }
}