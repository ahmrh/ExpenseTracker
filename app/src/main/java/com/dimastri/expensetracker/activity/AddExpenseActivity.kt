package com.dimastri.expensetracker.activity

import android.content.Intent
import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dimastri.expensetracker.R
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.time.format.DateTimeFormatter
import java.util.*

class AddExpenseActivity: AppCompatActivity() {
  lateinit var textTitle: TextInputEditText
  lateinit var textNominal: TextInputEditText
  lateinit var textDate: TextInputEditText
  lateinit var inputCategory: TextInputLayout
  lateinit var buttonSave: Button
  lateinit var selectedDate: Date

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_add_expense)

    textTitle = findViewById(R.id.textTitle)
    textNominal = findViewById(R.id.textNominal)
    inputCategory = findViewById(R.id.menu)
    textDate = findViewById(R.id.textDate)
    buttonSave = findViewById(R.id.btnSimpan)

    runDatePicker()

    textTitle.setText("")
    textNominal.setText("")

    val items = intent.getStringArrayExtra("listCategory")
    if (items != null) {
      populateCategory(items)
    }

    buttonSave.setOnClickListener {
      val data = Intent()
      data.putExtra("title", textTitle.text.toString())
      data.putExtra("nominal", textNominal.text.toString())
      data.putExtra("category", inputCategory.editText?.text.toString())
      data.putExtra("date", selectedDate.time)
      setResult(RESULT_OK, data)
      finish()
    }
  }

  fun runDatePicker() {
    val datePicker = MaterialDatePicker.Builder.datePicker()
      .setTitleText("Select date")
      .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
      .build()

    datePicker.show(supportFragmentManager, "datePicker");

    datePicker.addOnPositiveButtonClickListener {
      val date = Date(it)
      var formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy")
      textDate.setText(date.toInstant().atZone(TimeZone.getDefault().toZoneId()).toLocalDate().format(formatter))
      selectedDate = date
    }
  }

  fun populateCategory(items: Array<String>) {
    (inputCategory.editText as MaterialAutoCompleteTextView).setSimpleItems(items)
  }
}