package com.example.nerv_io.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Diagnostic (
    val Age: String ?="",
    val ChestPainType: String ?="",
    val Cholesterol: String ?="",
    val ExerciseIncluded: String ?="",
    val FastingBlood: String?="",
    val FullName: String?="",
    val Gender: String?="",
    val HeartDisease: String?="",
    val MaxHeartRate: String?="",
    val NotHeartDisease: String?="",
    val NumberOfMajorVessels: String?="",
    val Oldpeak: String?="",
    val RestingBlood: String?="",
    val RestingElectroCardiography: String?="",
    val SlopeOfThePeakExercise: String?="",
    val ThalValue: String?="",
    val UserID: String?="",
) : Parcelable
