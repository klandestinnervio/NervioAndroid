package com.example.nerv_io

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nerv_io.databinding.ActivityDiagnosticTestBinding

class DiagnosticTestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDiagnosticTestBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiagnosticTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnResult.setOnClickListener{
            val intent = Intent(this, ResultDiagnosticActivity::class.java)
            startActivity(intent)
        }
    }
}