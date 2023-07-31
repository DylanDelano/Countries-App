package com.example.countriesapp.modal_data

import android.os.Parcelable
import androidx.core.view.ContentInfoCompat
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class countries_data(
    @SerializedName("area")
    val area:Double,
    @SerializedName("continents")
    val continents:List<String>?,
    @SerializedName("name")
    val name:Names,
    @SerializedName("population")
    val population:Int,
    @SerializedName("capital")
    val capital:List<String>? = emptyList(),
    @SerializedName("timezones")
    val timezones:List<String>,
    @SerializedName("region")
    val region:String,
    @SerializedName("subregion")
    val subregion:String?,
    @SerializedName("languages")
    val languages:HashMap<String,String>? = HashMap(),
    @SerializedName("independent")
    val independent:Boolean?,
    @SerializedName("currencies")
    val currencies:HashMap<String, CurrencyDetail>?= HashMap(),
    @SerializedName("flags")
    val flags: Flags,
    @SerializedName("coatofarms")
    val coatofarms:CoatOfArms,
    @SerializedName("idd")
    val idd:Idd?,
    @SerializedName("car")
    val car:Car,
):Parcelable{
    @Parcelize
    data class Flags(
        @SerializedName("png")
        val png:String?,
        @SerializedName("svg")
        val svg:String?
    ):Parcelable
    @Parcelize
    data class CoatOfArms(
        @SerializedName("png")
        val png:String?,
        @SerializedName("svg")
        val svg: String?,
    ):Parcelable
    @Parcelize
    data class Car(
        @SerializedName("side")
        val side:String,
        @SerializedName("signs")
        val signs:List<String>
    ):Parcelable
    @Parcelize
    data class Idd(
        @SerializedName("root")
        val root:String,
        @SerializedName("suffixes")
        val suffixes:List<String>
    ):Parcelable
    @Parcelize
    data class Names(
        @SerializedName("common")
        val common:String,
        @SerializedName("nativeName")
        val nativeName: HashMap<String,NativeNameDetail> = HashMap(),
        @SerializedName("official")
        val official:String
    ):Parcelable

    @Parcelize
    data class CurrencyDetail(
        @SerializedName("name")
        val name: String?,
        @SerializedName("symbol")
        val symbol:String?
    ): Parcelable
    @Parcelize
    data class NativeNameDetail(
        @SerializedName("common")
        val common: String,
        @SerializedName("official")
        val official: String
    ):Parcelable


}










