package com.dimastri.expensetracker.adapter

import com.dimastri.expensetracker.R
import com.dimastri.expensetracker.model.Category
import com.dimastri.expensetracker.model.Expense

class ListCategory {
  private val listCategory = mutableListOf<Category>()

  init {
    listCategory.add(Category("Food", R.drawable.ic_baseline_category_24, ArrayList<Expense>()))
    listCategory.add(Category("Transportation", R.drawable.ic_baseline_category_24, ArrayList<Expense>()))
    listCategory.add(Category("Shopping", R.drawable.ic_baseline_category_24, ArrayList<Expense>()))
    listCategory.add(Category("Entertainment", R.drawable.ic_baseline_category_24, ArrayList<Expense>()))
    listCategory.add(Category("Health", R.drawable.ic_baseline_category_24, ArrayList<Expense>()))
    listCategory.add(Category("Education", R.drawable.ic_baseline_category_24, ArrayList<Expense>()))
  }

  fun getList(): List<Category> = listCategory
}