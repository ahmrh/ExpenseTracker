package com.dimastri.expensetracker.adapter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dimastri.expensetracker.model.Category

class CategoryViewModel: ViewModel() {
  private val listCategory = ListCategory()
  private val lvListCategory = MutableLiveData<List<Category>>()

  fun getListCategory(): LiveData<List<Category>> {
    lvListCategory.value = listCategory.getList()
    return lvListCategory
  }
}