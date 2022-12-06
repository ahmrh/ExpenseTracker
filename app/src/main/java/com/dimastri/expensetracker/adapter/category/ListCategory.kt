package com.dimastri.expensetracker.adapter.category

import com.dimastri.expensetracker.R
import com.dimastri.expensetracker.model.Category
import com.dimastri.expensetracker.model.Expense

class ListCategory {
  private val listCategory = mutableListOf<Category>()

  init {
    listCategory.add(Category("Food", R.drawable.ic_baseline_category_24, ArrayList<Expense>()))
//    listCategory.add(Category("Transportation", R.drawable.ic_baseline_category_24, ArrayList<Expense>()))
//    listCategory.add(Category("Shopping", R.drawable.ic_baseline_category_24, ArrayList<Expense>()))
//    listCategory.add(Category("Entertainment", R.drawable.ic_baseline_category_24, ArrayList<Expense>()))
//    listCategory.add(Category("Health", R.drawable.ic_baseline_category_24, ArrayList<Expense>()))
//    listCategory.add(Category("Education", R.drawable.ic_baseline_category_24, ArrayList<Expense>()))
  }

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