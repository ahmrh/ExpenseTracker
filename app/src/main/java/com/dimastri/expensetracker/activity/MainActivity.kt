package com.dimastri.expensetracker.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import com.dimastri.expensetracker.R

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.launch_screen)

    supportActionBar?.hide()

    val btnMulai = findViewById<Button>(R.id.btnMulai)

    btnMulai.setOnClickListener {
      val intent = Intent(this, HomePageActivity::class.java)
      startActivity(intent)
      finish()
    }
  }
}