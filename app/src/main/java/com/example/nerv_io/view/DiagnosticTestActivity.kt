package com.example.nerv_io.view

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.nerv_io.adapter.utils.Cons
import com.example.nerv_io.data.User
import com.example.nerv_io.databinding.ActivityDiagnosticTestBinding
import com.example.nerv_io.ml.ModelAkurasiOverfittingKecil
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.orhanobut.hawk.Hawk
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.util.*


class DiagnosticTestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDiagnosticTestBinding
    var db = FirebaseFirestore.getInstance()
    val profile by lazy { Hawk.get<User>(Cons.MyProfile) }


    private val item = arrayOf(
        "Male",
        "Female"
    )

    private val item1 = arrayOf(
        "True",
        "False"
    )
    private val item2 = arrayOf(
        "yes",
        "no"
    )
    private val item3 = arrayOf(
        "Asymptomatic",
        "atypical angina",
        "non-anginal pain",
        "typical angina"
    )
    private var gender = 1F

    private var restBlood = 1F

    private var exerciseEngina = 1F

    private var chestPain = 1F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiagnosticTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Hawk.init(this).build()

        spinnerAdapter()
        spinnerAdapterRestBlood()
        spinnerAdapterExerciseEngina()
        spinnerAdapterChestPain()
        binding.userName.setText(profile.name)

        val button: Button = binding.btnResult
        button.setOnClickListener(View.OnClickListener {
            val userName: EditText = binding.userName
            val userAge: EditText = binding.userAge
            val userRestingBlood: EditText = binding.userRestBlood
            val userCholesterol: EditText = binding.userCholesterol
            val userRestEletrco: EditText = binding.userRestElectro
            val userHeartRate: EditText = binding.userHeartRate
            val userOldPeak: EditText = binding.userOldpeak
            val userKindSlope: EditText = binding.userKindSlope
            val userMajorVessel: EditText = binding.userMajorVessel
            val userThalValue: EditText = binding.userThalValue


            if (userName.text.isEmpty()) {
                Toast.makeText(this, "Full Name is Required", Toast.LENGTH_SHORT).show()
            } else if (userAge.text.isEmpty()) {
                Toast.makeText(this, "Age is Required", Toast.LENGTH_SHORT).show()
            }else if (userRestingBlood.text.isEmpty()) {
                Toast.makeText(this, "Resting Blood Pressure is Required", Toast.LENGTH_SHORT).show()
            } else if (userCholesterol.text.isEmpty()) {
                Toast.makeText(this, "Cholesterol is Required", Toast.LENGTH_SHORT).show()
            } else if (userRestEletrco.text.isEmpty()) {
                Toast.makeText(this, "Resting Electrocardiography is Required", Toast.LENGTH_SHORT).show()
            } else if (userHeartRate.text.isEmpty()) {
                Toast.makeText(this, "Max heart Rate Achieved is Required", Toast.LENGTH_SHORT).show()
            } else if (userOldPeak.text.isEmpty()) {
                Toast.makeText(this, "Oldpeak is Required", Toast.LENGTH_SHORT).show()
            } else if (userKindSlope.text.isEmpty()) {
                Toast.makeText(this, "Slope of the peak is Required", Toast.LENGTH_SHORT).show()
            } else if (userMajorVessel.text.isEmpty()) {
                Toast.makeText(this, "Number of major vessels is Required", Toast.LENGTH_SHORT).show()
            } else if (userThalValue.text.isEmpty()) {
                Toast.makeText(this, "Thal Value is Required", Toast.LENGTH_SHORT).show()
            } else {

                val name = userName.text.toString()
                val age = userAge.text.toString().toFloat()
                val restingBlood = userRestingBlood.text.toString().toFloat()
                val cholesterol = userCholesterol.text.toString().toFloat()
                val restElectro = userRestEletrco.text.toString().toFloat()
                val heartRate = userHeartRate.text.toString().toFloat()
                val oldPeak = userOldPeak.text.toString().toFloat()
                val kindSlope = userKindSlope.text.toString().toFloat()
                val majorVessel = userMajorVessel.text.toString().toFloat()
                val thalValue = userThalValue.text.toString().toFloat()

                val byteBuffer: ByteBuffer = ByteBuffer.allocateDirect(13 * 4)
                byteBuffer.putFloat(age)
                byteBuffer.putFloat(gender)
                byteBuffer.putFloat(chestPain)
                byteBuffer.putFloat(restingBlood)
                byteBuffer.putFloat(cholesterol)
                byteBuffer.putFloat(restBlood)
                byteBuffer.putFloat(restElectro)
                byteBuffer.putFloat(heartRate)
                byteBuffer.putFloat(exerciseEngina)
                byteBuffer.putFloat(oldPeak)
                byteBuffer.putFloat(kindSlope)
                byteBuffer.putFloat(majorVessel)
                byteBuffer.putFloat(thalValue)


                val model = ModelAkurasiOverfittingKecil.newInstance(this)

                val inputFeature0 =
                    TensorBuffer.createFixedSize(intArrayOf(1, 13), DataType.FLOAT32)
                inputFeature0.loadBuffer(byteBuffer)

                val outputs = model.process(inputFeature0)
                val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray

//            tv.text = "Heart Disease - " + outputFeature0[0].toString() + "\nNot Heart Disease - " + outputFeature0[1].toString()

                val city = hashMapOf(
                    "Age" to age,
                    "ChestPainType" to chestPain,
                    "Cholesterol" to cholesterol,
                    "ExerciseIncluded" to exerciseEngina,
                    "FastingBlood" to restBlood,
                    "FullName" to name,
                    "Gender" to gender.toString(),
                    "MaxHeartRate" to heartRate,
                    "NumberOfMajorVessels" to majorVessel.toString(),
                    "Oldpeak" to oldPeak.toString(),
                    "RestingBlood" to restingBlood.toString(),
                    "RestingElectroCardiography" to restElectro.toString(),
                    "SlopeOfThePeakExercise" to kindSlope.toString(),
                    "ThalValue" to thalValue.toString(),
                    "UserID" to profile.userID,
                    "HeartDisease" to outputFeature0[0],
                    "NotHeartDisease" to outputFeature0[1],
                )


                db.collection("diagnostic").document(UUID.randomUUID().toString())
                    .set(city)
                    .addOnSuccessListener { Log.d("ss", "DocumentSnapshot successfully written!") }
                    .addOnFailureListener { e -> Log.w("ss", "Error writing document", e) }

                val intent = Intent(this, ResultDiagnosticActivity::class.java)
                intent.putExtra(ResultDiagnosticActivity.NAME, name)
                intent.putExtra(ResultDiagnosticActivity.AGE, age.toString())
                intent.putExtra(ResultDiagnosticActivity.Disease, outputFeature0[0].toString())
                startActivity(intent)

                model.close()
            }

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
    private fun spinnerAdapterRestBlood() {
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, item1)
        binding.spinnerRestblood.adapter = adapter
        binding.spinnerRestblood.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                if (i == 1) {
                    restBlood = 0F
                } else {
                    restBlood = 1F
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {}
        }
        binding.spinnerGender.adapter = adapter
    }
    private fun spinnerAdapterExerciseEngina() {
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, item2)
        binding.spinnerExerciseEngina.adapter = adapter
        binding.spinnerExerciseEngina.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                if (i == 1) {
                    exerciseEngina = 0F
                } else {
                    exerciseEngina = 1F
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {}
        }
        binding.spinnerExerciseEngina.adapter = adapter
    }
    private fun spinnerAdapterChestPain() {
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, item3)
        binding.spinnerChestpain.adapter = adapter
        binding.spinnerChestpain.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                if (i == 0) {
                    chestPain = 0F
                } else if (i == 1){
                    chestPain = 1F
                } else if (i == 2){
                    chestPain = 2F
                }else if (i == 3){
                    chestPain = 3F
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {}
        }
        binding.spinnerChestpain.adapter = adapter
    }

}
