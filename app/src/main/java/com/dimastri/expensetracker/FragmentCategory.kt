package com.dimastri.expensetracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dimastri.expensetracker.adapter.SharedViewModel
import com.dimastri.expensetracker.adapter.ListCategoryAdapter
import com.dimastri.expensetracker.model.Category
import com.dimastri.expensetracker.model.Expense

class FragmentCategory () : Fragment(R.layout.fragment_category) {

  private val sharedViedModel: SharedViewModel by activityViewModels()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val view = inflater.inflate(R.layout.fragment_category, container, false)
    val btnAdd = view.findViewById<View>(R.id.btnAddCategory)

    val rvCategory: RecyclerView = view.findViewById<RecyclerView>(R.id.list_category)
    val listCategoryAdapter = ListCategoryAdapter(sharedViedModel.listCategory)
    rvCategory.adapter = listCategoryAdapter
    rvCategory.layoutManager = LinearLayoutManager(view.context)

    btnAdd.setOnClickListener {
      val created = sharedViedModel.createNewCategory("Tambahan", R.drawable.ic_baseline_category_24, null)
      if (created) {
        Toast.makeText(view.context, "Category created", Toast.LENGTH_SHORT).show()
      } else {
        Toast.makeText(view.context, "Category already exist", Toast.LENGTH_SHORT).show()
      }
    }

    sharedViedModel.listCategory.observe(viewLifecycleOwner, Observer {
      listCategoryAdapter.notifyDataSetChanged()
    })

    return view
  }
}