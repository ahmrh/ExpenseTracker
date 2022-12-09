package com.dimastri.expensetracker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.dimastri.expensetracker.R
import com.dimastri.expensetracker.model.Expense
import com.dimastri.expensetracker.tools.CustomFormatter

class ListExpenseAdapter(var listExpense: LiveData<List<Expense>>): RecyclerView.Adapter<ListExpenseAdapter.ListExpenseViewHolder>() {

    inner class ListExpenseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView = itemView.findViewById(R.id.textTitle)
        var tvAmount: TextView = itemView.findViewById(R.id.textAmount)
        var tvDate: TextView = itemView.findViewById(R.id.textDate)
        var tvCategory: TextView = itemView.findViewById(R.id.textCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListExpenseViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.expense_card, parent, false)
        return ListExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListExpenseViewHolder, position: Int) {

            val expense = listExpense.value?.get(position)
            holder.tvTitle.text = expense?.title
            holder.tvAmount.text = CustomFormatter().formatCurrency(expense?.nominal!!)
            holder.tvDate.text = expense.date.let { CustomFormatter().formatDate(it) }
            holder.tvCategory.text = expense.category
    }

    override fun getItemCount(): Int {
        return listExpense.value?.size!!
    }

    fun setFilter(model: LiveData<List<Expense>>) {
//        filterModel = model
        listExpense = model
        notifyDataSetChanged()
    }
}