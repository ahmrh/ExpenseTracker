package com.dimastri.expensetracker.adapter.expense

import com.dimastri.expensetracker.model.Expense
import java.util.*

class ListExpense {
  private val listExpense = mutableListOf<Expense>()

  fun getList(): List<Expense> = listExpense

  fun addExpense(expense: Expense) {
    listExpense.add(expense)
  }

  fun getExpenseByTitle(name: String): Expense? {
    for (expense in listExpense) {
      if (expense.title == name) {
        return expense
      }
    }
    return null
  }
}