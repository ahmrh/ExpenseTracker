package com.dimastri.expensetracker.adapter

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dimastri.expensetracker.R
import com.dimastri.expensetracker.model.Category
import com.dimastri.expensetracker.model.Expense
import java.util.*
import kotlin.collections.ArrayList

class SharedViewModel : ViewModel() {
  private var _listCategory = MutableLiveData<List<Category>>()
  private var _listExpense = MutableLiveData<List<Expense>>()
  private var _listFilteredExpense = MutableLiveData<List<Expense>>()

  val listCategory: LiveData<List<Category>> = _listCategory
  val listExpense: LiveData<List<Expense>> = _listExpense
  val listFilteredExpense: LiveData<List<Expense>> = _listFilteredExpense

  init {
    val listCategory = ArrayList<Category>()
    val listExpense = ArrayList<Expense>()

    listCategory.add(Category("Food", R.drawable.ic_baseline_category_24, 11000))
    listCategory.add(Category("Transport", R.drawable.ic_baseline_category_24, 50000))
    listCategory.add(Category("Entertainment", R.drawable.ic_baseline_category_24, 40000))

    listExpense.add(Expense("Beli Nasi Goreng", 11000, "Buying Food", "Food", Date()))
    listExpense.add(Expense("Beli Tiket Film", 40000, "Buying Food", "Food", Date()))
    listExpense.add(Expense("Beli Bensin", 50000, "Buying Train Ticket", "Transport", Date()))

    _listCategory.value = listCategory
    _listExpense.value = listExpense
  }

  fun createNewCategory(name: String, icon: Int?, count: Long?): Boolean {
    if (getCategoryByName(name) !== null) {
      return false
    }

    val category = Category(name, icon ?: R.drawable.ic_baseline_category_24, count ?: 0)
    _listCategory.value = _listCategory.value?.plus(category)
    return true
  }

  fun getCategoryByName(name: String): Category? {
    return _listCategory.value?.find { it.name == name }
  }

  fun createNewExpenses(title: String, nominal: Long, description: String?, category: String, date: Date) {
    val ca = getCategoryByName(category)

    if (ca != null) {
      ca.count += nominal
      _listCategory.value = _listCategory.value
    } else {
      createNewCategory(category, null, nominal)
    }

    val expense = Expense(title, nominal, description, category, date)

    _listExpense.value = _listExpense.value?.plus(expense)
    _listCategory.value = _listCategory.value?.sortedByDescending { it.count }
  }

  fun calculateTotalExpense(): Long {
    var total:Long = 0
    _listExpense.value?.forEach {
      total += it.nominal
    }
    return total
  }

  fun calculateTotalExpenseThisWeek(): Long {
    var total:Long = 0
    _listExpense.value?.forEach {
      if (it.date.time > Date().time - 604800000) {
        total += it.nominal
      }
    }
    return total
  }

  fun calculateTotalExpenseThisMonth(): Long {
    var total:Long = 0
    _listExpense.value?.forEach {
      if (it.date.time > Date().time - 2629746000) {
        total += it.nominal
      }
    }
    return total
  }

  fun getNumExpenses(): Int {
    return _listExpense.value?.size ?: 0
  }

  fun searchExpense(query: String): LiveData<List<Expense>> {
    if (query == "") return listExpense

    val listExpense = ArrayList<Expense>()

    val res = _listExpense.value?.filter {
      it.title.lowercase().contains(query, true) || it.category.lowercase().contains(query, true)
    } ?: listOf()

    listExpense.addAll(res)
    _listFilteredExpense.value = listExpense
    return listFilteredExpense
  }

}