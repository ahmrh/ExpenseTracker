package com.dimastri.expensetracker.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ExpenseCategory(
  var name: String,
): Parcelable