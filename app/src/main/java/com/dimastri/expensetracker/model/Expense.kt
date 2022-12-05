package com.dimastri.expensetracker.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Expense(
  var nominal: Int,
  var description: String,
  var category: Category,
  var date: Date
):Parcelable