package com.example.nerv_io.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nerv_io.R
import com.example.nerv_io.adapter.HospitalAdapter
import com.example.nerv_io.adapter.utils.DiagnosticAdapter
import com.example.nerv_io.data.Diagnostic
import com.example.nerv_io.data.Hospital
import com.example.nerv_io.databinding.ActivityHistoryDiagnosticBinding
import com.example.nerv_io.databinding.ActivityHospitalRecomBinding
import com.google.firebase.firestore.FirebaseFirestore

class HistoryDiagnosticActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryDiagnosticBinding
    var db = FirebaseFirestore.getInstance()
    private var diagnostic: MutableList<Diagnostic> = mutableListOf()
    var adapter: DiagnosticAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryDiagnosticBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
    }

    private fun initData() {
        db.collection("diagnostic")
            .get().addOnCompleteListener {
                for (document in it.result!!) {
                    val feeds = document.toObject(Diagnostic::class.java)
                    diagnostic.add(feeds)
                }
                adapter = DiagnosticAdapter(this, diagnostic)
                binding.rvDiagnostic.also {
                    it.layoutManager = LinearLayoutManager(this)
                    it.setHasFixedSize(true)
                    it.adapter = adapter
                }
            }
    }
}