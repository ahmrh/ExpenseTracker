package com.dimastri.expensetracker.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dimastri.expensetracker.R
import com.dimastri.expensetracker.activity.AddCategoryActivity
import com.dimastri.expensetracker.adapter.SharedViewModel
import com.dimastri.expensetracker.adapter.ListCategoryAdapter

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

    // Launcher for getting result from AddCategoryActivity Intent
    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
      if (result.resultCode == Activity.RESULT_OK) {
        val data: Intent? = result.data
        val category = data?.getStringExtra("categoryName")
        if (category != null) {
          addCategory(category)
        }
      }
    }

    btnAdd.setOnClickListener {
      val intent = Intent(view.context, AddCategoryActivity::class.java)
      resultLauncher.launch(intent)
    }

    sharedViedModel.listCategory.observe(viewLifecycleOwner, Observer {
      listCategoryAdapter.notifyDataSetChanged()
    })

    return view
  }


  fun addCategory(name: String) {
    val created = sharedViedModel.createNewCategory(name, R.drawable.ic_baseline_category_24, null)
    if (created) {
      Toast.makeText(context, "Category $name created", Toast.LENGTH_SHORT).show()
    } else {
      Toast.makeText(context, "Category $name already exist", Toast.LENGTH_SHORT).show()
    }
  }
}