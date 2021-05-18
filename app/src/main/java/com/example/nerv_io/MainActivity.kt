package com.example.nerv_io

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth



class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseAuth = FirebaseAuth.getInstance()
        val user = firebaseAuth.currentUser

        Handler().postDelayed({
            if (user != null){
                val menuIntent = Intent(this, MenuActivity::class.java)
                startActivity(menuIntent)
            }else{
                val logInIntent = Intent(this,SignInActivity::class.java)
                startActivity(logInIntent)
            }
        },2000)
    }
}

//        var button : Button = findViewById<Button>(R.id.predict)
//        button.setOnClickListener(View.OnClickListener {
//            var ages : EditText = findViewById(R.id.age)
//            var gender : EditText = findViewById(R.id.sex)
//            var chestpain : EditText = findViewById(R.id.cp)
//            var resting : EditText = findViewById(R.id.trestbps)
//            var cholesterol : EditText = findViewById(R.id.chol)
//            var fasting : EditText = findViewById(R.id.fbs)
//            var resting_elect : EditText = findViewById(R.id.restecg)
//            var heart_rate : EditText = findViewById(R.id.thalach)
//            var exercise : EditText = findViewById(R.id.exang)
//            var depression : EditText = findViewById(R.id.oldpeak)
//            var slope_peak : EditText = findViewById(R.id.slope)
//            var major_vessel : EditText = findViewById(R.id.ca)
//            var thall : EditText = findViewById(R.id.thal)
//
//            var age : Float = ages.text.toString().toFloat()
//            var genders : Float = gender.text.toString().toFloat()
//            var chestpains : Float = chestpain.text.toString().toFloat()
//            var restings : Float = resting.text.toString().toFloat()
//            var cholesterols : Float = cholesterol.text.toString().toFloat()
//            var fastings : Float = fasting.text.toString().toFloat()
//            var resting_electron : Float = resting_elect.text.toString().toFloat()
//            var heart_rates : Float = heart_rate.text.toString().toFloat()
//            var exercises : Float = exercise.text.toString().toFloat()
//            var depressions : Float = depression.text.toString().toFloat()
//            var slope_peaks : Float = slope_peak.text.toString().toFloat()
//            var major_vessels : Float = major_vessel.text.toString().toFloat()
//            var thals : Float = thall.text.toString().toFloat()
//
//            var byteBuffer : ByteBuffer = ByteBuffer.allocateDirect(13*4)
//            byteBuffer.putFloat(age)
//            byteBuffer.putFloat(genders)
//            byteBuffer.putFloat(chestpains)
//            byteBuffer.putFloat(restings)
//            byteBuffer.putFloat(cholesterols)
//            byteBuffer.putFloat(fastings)
//            byteBuffer.putFloat(resting_electron)
//            byteBuffer.putFloat(heart_rates)
//            byteBuffer.putFloat(exercises)
//            byteBuffer.putFloat(depressions)
//            byteBuffer.putFloat(slope_peaks)
//            byteBuffer.putFloat(major_vessels)
//            byteBuffer.putFloat(thals)
//
//            val model = ModelAkurasiOverfittingKecil.newInstance(this)
//
//            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 13), DataType.FLOAT32)
//            inputFeature0.loadBuffer(byteBuffer)
//
//            val outputs = model.process(inputFeature0)
//            val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray
//
//            var tv : TextView = findViewById(R.id.textView)
//            tv.setText("Heart Disease - " + outputFeature0[0].toString() + "\nNot Heart Disease - " + outputFeature0[1].toString())
//
//            model.close()
//        })
//    }
