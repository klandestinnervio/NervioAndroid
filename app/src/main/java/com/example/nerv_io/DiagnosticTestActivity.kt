package com.example.nerv_io

import android.R
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.nerv_io.databinding.ActivityDiagnosticTestBinding
import com.example.nerv_io.ml.ModelAkurasiOverfittingKecil
import kotlinx.android.synthetic.main.activity_diagnostic_test.*
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.util.jar.Attributes

class DiagnosticTestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDiagnosticTestBinding


    private val Item = arrayOf(
        "Male",
        "Female"
    )
    private var gender = 1F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiagnosticTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        spinnerAdapter()
        var button: Button = binding.btnResult
        button.setOnClickListener(View.OnClickListener {
            var userName : EditText = binding.userName
            var userAge: EditText = binding.userAge
            var userChestPain: EditText = binding.userChestPain
            var userRestingBlood: EditText = binding.userRestBlood
            var userCholesterol: EditText = binding.userCholesterol
            var userFastingBlood: EditText = binding.userFastingBlood
            var userRestEletrco: EditText = binding.userRestElectro
            var userHeartRate: EditText = binding.userHeartRate
            var userIncludeAngina: EditText = binding.userInducedAngina
            var userOldpeak: EditText = binding.userInducedAngina
            var userKindSlope: EditText = binding.userKindSlope
            var userMajorVessel: EditText = binding.userMajorVessel
            var userThalValue: EditText = binding.userThalValue


            var name = userName.text.toString()
            var age = userAge.text.toString().toFloat()
            var chestPain = userChestPain.text.toString().toFloat()
            var restingBlood = userRestingBlood.text.toString().toFloat()
            var cholesterol = userCholesterol.text.toString().toFloat()
            var fastingBlood = userFastingBlood.text.toString().toFloat()
            var restElectro = userRestEletrco.text.toString().toFloat()
            var heartRate = userHeartRate.text.toString().toFloat()
            var includeAngine = userIncludeAngina.text.toString().toFloat()
            var oldPeak = userOldpeak.text.toString().toFloat()
            var kindSlope = userKindSlope.text.toString().toFloat()
            var majorVessel = userMajorVessel.text.toString().toFloat()
            var thalValue = userThalValue.text.toString().toFloat()


            var byteBuffer: ByteBuffer = ByteBuffer.allocateDirect(13 * 4)
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
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, Item)
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
