package com.example.nerv_io

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.example.nerv_io.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnTest.setOnClickListener {
            val intent = Intent(this, DiagnosticTestActivity::class.java)
            startActivity(intent)
        }
        binding.btnHistory.setOnClickListener {
            val intent = Intent(this, HistoryDiagnosticActivity::class.java)
            startActivity(intent)
        }
        binding.btnRecommen.setOnClickListener {
            val intent = Intent(this, HospitalRecomActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)

    }
}