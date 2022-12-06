package com.dimastri.expensetracker.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.dimastri.expensetracker.R

class AddCategoryActivity: AppCompatActivity() {
  lateinit var imgCategory: ImageView
  lateinit var textCategoryName: EditText
  lateinit var buttonSave: Button

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_add_category)

    imgCategory = findViewById(R.id.imageCategory)
    textCategoryName = findViewById(R.id.textCategoryName)
    buttonSave = findViewById(R.id.buttonSimpan)

    textCategoryName.setText("");
    textCategoryName.setAutofillHints("Category Name");

    buttonSave.setOnClickListener {
      val data = Intent()
      data.putExtra("categoryName", textCategoryName.text.toString())
      setResult(RESULT_OK, data)
      finish()
    }
  }
}