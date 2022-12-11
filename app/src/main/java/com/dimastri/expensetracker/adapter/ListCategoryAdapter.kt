package com.dimastri.expensetracker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.dimastri.expensetracker.R
import com.dimastri.expensetracker.model.Category
import com.dimastri.expensetracker.tools.CustomFormatter

class ListCategoryAdapter(var listCategory: LiveData<List<Category>>) :
  RecyclerView.Adapter<ListCategoryAdapter.ListCategoryViewHolder>() {
  inner class ListCategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var tvName: TextView = itemView.findViewById(R.id.textCategory)
    var ivImage: ImageView = itemView.findViewById(R.id.imgCategory)
    var tvAmount: TextView = itemView.findViewById(R.id.textCategoryAmount)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCategoryViewHolder {
    val view: View =
      LayoutInflater.from(parent.context).inflate(R.layout.category_card, parent, false)
    return ListCategoryViewHolder(view)
  }

  override fun onBindViewHolder(holder: ListCategoryViewHolder, position: Int) {
    val category = listCategory.value?.get(position)
    holder.tvName.text = category?.name
    holder.ivImage.setImageResource(category?.image!!)

    val amount = CustomFormatter().formatCurrency(category.count)
    holder.tvAmount.text = amount
  }

  override fun getItemCount(): Int {
    return listCategory.value?.size!!
  }
}