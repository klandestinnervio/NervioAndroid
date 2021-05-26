package com.example.nerv_io

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.nerv_io.databinding.ActivityDiagnosticTestBinding
import com.example.nerv_io.ml.ModelAkurasiOverfittingKecil
import kotlinx.android.synthetic.main.activity_diagnostic_test.*
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.util.jar.Attributes

class DiagnosticTestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDiagnosticTestBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiagnosticTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var button: Button = binding.btnResult
        button.setOnClickListener(View.OnClickListener {
            var userName : EditText = binding.userName
            var userAge: EditText = binding.userAge
            var userGender: EditText = binding.userGender
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
            var tv: TextView = binding.textView


            var name = userName.text.toString()
            var age = userAge.text.toString().toFloat()
            var gender = userGender.text.toString().toFloat()
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
            var tvResult = tv.text.toString()


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

            tv.setText("Heart Disease - " + outputFeature0[0].toString() + "\nNot Heart Disease - " + outputFeature0[1].toString())

            model.close()

        })

    }
}
