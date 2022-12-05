package com.dimastri.expensetracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dimastri.expensetracker.adapter.CategoryViewModel
import com.dimastri.expensetracker.adapter.ListCategoryAdapter

class FragmentCategory () : Fragment(R.layout.fragment_category) {
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val view = inflater.inflate(R.layout.fragment_category, container, false)

    val categoryViewModel = ViewModelProvider(this)[CategoryViewModel::class.java]
    val rvCategory: RecyclerView = view.findViewById<RecyclerView>(R.id.list_category)
    val listCategoryAdapter = ListCategoryAdapter(categoryViewModel.getListCategory())
    rvCategory.adapter = listCategoryAdapter
    rvCategory.layoutManager = LinearLayoutManager(view.context)

    return view
  }
}