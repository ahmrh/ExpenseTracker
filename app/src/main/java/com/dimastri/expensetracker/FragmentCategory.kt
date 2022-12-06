package com.dimastri.expensetracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dimastri.expensetracker.adapter.category.CategoryViewModel
import com.dimastri.expensetracker.adapter.category.ListCategoryAdapter
import com.dimastri.expensetracker.model.Category
import com.dimastri.expensetracker.model.Expense

class FragmentCategory () : Fragment(R.layout.fragment_category) {

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val view = inflater.inflate(R.layout.fragment_category, container, false)
    val btnAdd = view.findViewById<View>(R.id.btnAddCategory)

    val categoryViewModel = ViewModelProvider(this)[CategoryViewModel::class.java]
    val rvCategory: RecyclerView = view.findViewById<RecyclerView>(R.id.list_category)
    val listCategoryAdapter = ListCategoryAdapter(categoryViewModel.getListCategory())
    rvCategory.adapter = listCategoryAdapter
    rvCategory.layoutManager = LinearLayoutManager(view.context)

    btnAdd.setOnClickListener {
      val category = Category("Tambahan Kategori", R.drawable.ic_baseline_category_24, ArrayList<Expense>())
      categoryViewModel.addCategory(category)
    }

    categoryViewModel.lvListCategory.observe(viewLifecycleOwner, Observer {
      listCategoryAdapter.notifyDataSetChanged()
    })

    return view
  }
}