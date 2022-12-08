package com.dimastri.expensetracker.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(
  var name: String,
  var image: Int,
  var count: Long,
): Parcelable