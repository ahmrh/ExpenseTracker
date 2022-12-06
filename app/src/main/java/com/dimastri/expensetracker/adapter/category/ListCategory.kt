package com.dimastri.expensetracker.adapter.category

import com.dimastri.expensetracker.R
import com.dimastri.expensetracker.model.Category
import com.dimastri.expensetracker.model.Expense

class ListCategory {
  private val listCategory = mutableListOf<Category>()

  fun getList(): List<Category> = listCategory

  fun addCategory(category: Category) {
    listCategory.add(category)
  }

  fun getCategoryByName(name: String): Category? {
    for (category in listCategory) {
      if (category.name == name) {
        return category
      }
    }
    return null
  }

  fun insertExpense(expense: Expense) {
    val category = getCategoryByName(expense.category)
    category?.expense?.add(expense)
  }
}