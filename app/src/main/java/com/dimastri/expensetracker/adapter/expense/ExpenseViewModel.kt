package com.dimastri.expensetracker.adapter.expense

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dimastri.expensetracker.model.Expense

class ExpenseViewModel: ViewModel() {
  val listExpense = ListExpense()
  var lvListExpense = MutableLiveData<List<Expense>>()

  fun getListExpense(): LiveData<List<Expense>> {
    lvListExpense.value = listExpense.getList()
    return lvListExpense
  }

  fun addExpense(expense: Expense) {
    listExpense.addExpense(expense)
    lvListExpense.value = listExpense.getList()
  }
}