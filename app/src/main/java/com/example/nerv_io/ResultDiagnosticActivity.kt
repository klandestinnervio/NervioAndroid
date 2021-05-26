package com.example.nerv_io

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nerv_io.databinding.ActivityResultDiagnosticBinding


class ResultDiagnosticActivity : AppCompatActivity() {
    private lateinit var binding : ActivityResultDiagnosticBinding

    companion object{
        const val NAME = "NAME"
        const val AGE = "AGE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultDiagnosticBinding.inflate(layoutInflater)
        setContentView(binding.root)




        binding.backDashboard.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

    }

}