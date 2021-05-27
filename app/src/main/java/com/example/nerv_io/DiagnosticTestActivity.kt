package com.example.nerv_io

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.nerv_io.databinding.ActivityDiagnosticTestBinding
import com.example.nerv_io.ml.ModelAkurasiOverfittingKecil
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer

class DiagnosticTestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDiagnosticTestBinding


    private val item = arrayOf(
        "Male",
        "Female"
    )
    private var gender = 1F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiagnosticTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        spinnerAdapter()
        val button: Button = binding.btnResult
        button.setOnClickListener(View.OnClickListener {
            val userName : EditText = binding.userName
            val userAge: EditText = binding.userAge
            val userChestPain: EditText = binding.userChestPain
            val userRestingBlood: EditText = binding.userRestBlood
            val userCholesterol: EditText = binding.userCholesterol
            val userFastingBlood: EditText = binding.userFastingBlood
            val userRestEletrco: EditText = binding.userRestElectro
            val userHeartRate: EditText = binding.userHeartRate
            val userIncludeAngina: EditText = binding.userInducedAngina
            val userOldpeak: EditText = binding.userInducedAngina
            val userKindSlope: EditText = binding.userKindSlope
            val userMajorVessel: EditText = binding.userMajorVessel
            val userThalValue: EditText = binding.userThalValue


            val name = userName.text.toString()
            val age = userAge.text.toString().toFloat()
            val chestPain = userChestPain.text.toString().toFloat()
            val restingBlood = userRestingBlood.text.toString().toFloat()
            val cholesterol = userCholesterol.text.toString().toFloat()
            val fastingBlood = userFastingBlood.text.toString().toFloat()
            val restElectro = userRestEletrco.text.toString().toFloat()
            val heartRate = userHeartRate.text.toString().toFloat()
            val includeAngine = userIncludeAngina.text.toString().toFloat()
            val oldPeak = userOldpeak.text.toString().toFloat()
            val kindSlope = userKindSlope.text.toString().toFloat()
            val majorVessel = userMajorVessel.text.toString().toFloat()
            val thalValue = userThalValue.text.toString().toFloat()


            val byteBuffer: ByteBuffer = ByteBuffer.allocateDirect(13 * 4)
            byteBuffer.putFloat(age)
            byteBuffer.putFloat(gender)
            byteBuffer.putFloat(chestPain)
            byteBuffer.putFloat(restingBlood)
            byteBuffer.putFloat(cholesterol)
            byteBuffer.putFloat(fastingBlood)
            byteBuffer.putFloat(restElectro)
            byteBuffer.putFloat(heartRate)
            byteBuffer.putFloat(includeAngine)
            byteBuffer.putFloat(oldPeak)
            byteBuffer.putFloat(kindSlope)
            byteBuffer.putFloat(majorVessel)
            byteBuffer.putFloat(thalValue)


            val model = ModelAkurasiOverfittingKecil.newInstance(this)

            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 13), DataType.FLOAT32)
            inputFeature0.loadBuffer(byteBuffer)

            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray

//            tv.text = "Heart Disease - " + outputFeature0[0].toString() + "\nNot Heart Disease - " + outputFeature0[1].toString()

            val intent = Intent(this, ResultDiagnosticActivity::class.java)
            intent.putExtra(ResultDiagnosticActivity.NAME, name)
            intent.putExtra(ResultDiagnosticActivity.AGE, age.toString())
            intent.putExtra(ResultDiagnosticActivity.Disease, outputFeature0[0].toString())
            startActivity(intent)

            model.close()

        })

    }
    private fun spinnerAdapter() {
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, item)
        binding.spinnerGender.adapter = adapter
        binding.spinnerGender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                if (i == 0) {
                     gender = 1F
                } else {
                     gender = 0F
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {}
        }
        binding.spinnerGender.adapter = adapter
    }
}
