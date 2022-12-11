package com.dimastri.expensetracker.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Toast
import android.widget.Toolbar
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
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import  io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*

class FragmentExpenses() :
  Fragment(R.layout.fragment_expenses)/*, SearchView.OnQueryTextListener */ {

  lateinit var rvExpense: RecyclerView
  lateinit var listExpenseAdapter: ListExpenseAdapter
  lateinit var subscription: CompositeDisposable
  lateinit var searchView: SearchView
  lateinit var btnAdd: FloatingActionButton

  private val sharedViewModel: SharedViewModel by activityViewModels()

  @SuppressLint("NotifyDataSetChanged")
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View? {
    val view = inflater.inflate(R.layout.fragment_expenses, container, false)

    // find view
    btnAdd = view.findViewById(R.id.buttonAddExpense)
    searchView = view.findViewById(R.id.searchExpense)
    rvExpense = view.findViewById(R.id.list_expenses)

    // setting up recycler view
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

    // listener for add expense button
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

    // Observables
    subscription = CompositeDisposable()

    // create observables for search view, emitting text change
    val observableSearch = Observable.create(ObservableOnSubscribe<String> { emitter ->
      searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
          if (!emitter.isDisposed) {
            if (query != null) {
              emitter.onNext(query)
            }
          }
          return false
        }

        override fun onQueryTextChange(query: String?): Boolean {
          if (!emitter.isDisposed) {
            if (query != null) {
              emitter.onNext(query)
            }
          }
          return false
        }
      })
    }).distinctUntilChanged().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    // add subscriber to search view observables to filter the list
    val subscriberSearch = observableSearch.subscribe { text ->
      val item = sharedViewModel.searchExpense(text)
      listExpenseAdapter.setFilter(item)
    }

    // add to composite disposable
    subscription.add(subscriberSearch)

    return view
  }

  // add expense to list
  private fun addExpense(title: String, nominal: Long, category: String, date: Date) {
    sharedViewModel.createNewExpenses(title, nominal, null, category, date)
    Toast.makeText(context, "Expense added", Toast.LENGTH_SHORT).show()
  }

//  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//    inflater.inflate(R.menu.search_menu, menu)
//
//    val searchItem = menu.findItem(R.id.item_search)
//    val searchView = searchItem?.actionView as SearchView
//
//    searchView.isSubmitButtonEnabled = true
//    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//      override fun onQueryTextSubmit(query: String?): Boolean {
//
//        if (query != null) {
//          Toast.makeText(context, "onQueryTextSubmit: $query", Toast.LENGTH_LONG).show()
//          val item = sharedViewModel.searchExpense(query)
//          listExpenseAdapter.setFilter(item)
//        }
//        return true
//      }
//
//      override fun onQueryTextChange(query: String?): Boolean {
//        if (query != null) {
//          Toast.makeText(context, "onQueryTextChange: $query", Toast.LENGTH_LONG).show()
//          val item = sharedViewModel.searchExpense(query)
//          listExpenseAdapter.setFilter(item)
//        }
//        return true
//      }
//    })
//
//    return super.onCreateOptionsMenu(menu, inflater)
//  }

  override fun onDestroy() {
    subscription.dispose()
    super.onDestroy()
  }
}