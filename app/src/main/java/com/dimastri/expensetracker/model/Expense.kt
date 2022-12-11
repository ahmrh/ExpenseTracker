package com.dimastri.expensetracker.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Expense(
  var title: String,
  var nominal: Long,
  var description: String?,
  var category: String,
  var date: Date
) : Parcelable