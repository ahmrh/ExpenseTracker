package com.dimastri.expensetracker.adapter.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dimastri.expensetracker.model.Category

class CategoryViewModel: ViewModel() {
  val listCategory = ListCategory()
  var lvListCategory = MutableLiveData<List<Category>>()

  fun getListCategory(): LiveData<List<Category>> {
    lvListCategory.value = listCategory.getList()
    return lvListCategory
  }

  fun addCategory(category: Category) {
    listCategory.addCategory(category)
    lvListCategory.value = listCategory.getList()
  }
}