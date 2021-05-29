package com.example.nerv_io.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.example.nerv_io.R
import com.example.nerv_io.databinding.ActivityMenuBinding
import com.google.firebase.auth.FirebaseAuth

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.imgSignout.setOnClickListener {
            auth.signOut()
            Intent(this@MenuActivity, SignInActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }
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

        binding.GuideBook.setOnClickListener{
            val intent = Intent(this, GuideBookActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)

    }


}