package com.dimastri.expensetracker.tools

import java.time.format.DateTimeFormatter
import java.util.*

class CustomFormatter {
  val DATE_PATTERN = "dd MMMM yyyy"
  val dateFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN)

  var COUNTRY = "ID"
  var LANGUAGE = "id"
  var LOCALE = Locale(LANGUAGE, COUNTRY)
  val currencyFormatter = java.text.NumberFormat.getCurrencyInstance(LOCALE)

  fun formatDate(date: Date): String {
    return date.toInstant().atZone(TimeZone.getDefault().toZoneId()).toLocalDate().format(dateFormatter)
  }

  fun formatCurrency(amount: Long): String {
    return currencyFormatter.format(amount)
  }
}