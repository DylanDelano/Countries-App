package com.example.countriesapp.ui

import android.view.LayoutInflater
import android.view.LayoutInflater.*
import android.view.ViewGroup
import android.widget.Filter

import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.countriesapp.R
import com.example.countriesapp.constants.Constants.CONTENT_VIEW
import com.example.countriesapp.constants.Constants.SECTION_VIEW
import com.example.countriesapp.databinding.CountryAbbreBinding
import com.example.countriesapp.databinding.CountryRowBinding
import com.example.countriesapp.domain.CountriesModel
import com.example.countriesapp.modal_data.countries_data

import java.util.*
import kotlin.collections.ArrayList
import android.view.LayoutInflater.Filter as LayoutInflaterFilter

class CountryAdapter(private val dataSet: ArrayList<CountriesModel>): RecyclerView.Adapter<ViewHolder>() {
    val intialCountryList = ArrayList<CountriesModel>().apply {
        addAll(dataSet)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == SECTION_VIEW) {
            SectionHeaderViewHolder(CountryAbbreBinding.inflate(from(parent.context)))
        } else {
            ViewHolder(CountryRowBinding.inflate(from(parent.context)))
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (dataSet[position].isSection) {
            SECTION_VIEW
        } else
            CONTENT_VIEW
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val current = dataSet[position]
        if (SECTION_VIEW == getItemViewType(position)) {
            val sectionHeaderViewHolder = holder as SectionHeaderViewHolder
            sectionHeaderViewHolder.bind(current)
            return
        } else {
            val viewHolder = holder as ViewHolder
            viewHolder.bind(current)
        }
    }

    override fun getItemCount(): Int = dataSet.size


    class ViewHolder(private val binding: CountryRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(countriesModel: CountriesModel) {
            binding.apply {
                Glide.with(itemView)
                    .load(countriesModel.countryData.flags.png)
                    .into(flagImage)
                countryName.text = countriesModel.countryData.name.official ?: ""
                countryCapital.text = countriesModel.countryData.capital?.first() ?: ""
            }
        }

        }


        inner class SectionHeaderViewHolder(private val binding: CountryAbbreBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(countriesModel: CountriesModel) {
                binding.countryHeader.text = countriesModel.name
            }
        }
    fun getFilter():Filter {
        return cityFilter
    }

    private val cityFilter=object:Filter() {
       override fun performFiltering(constraint:CharSequence?):FilterResults{
            val filteredList: ArrayList<CountriesModel> = ArrayList()
            if(constraint == null || constraint.isEmpty()){
                intialCountryList.let { filteredList.addAll(it) }
            }else {
                val query = constraint.toString().trim().lowercase()
                intialCountryList.forEach {
                    if (it.countryData.name.official?.lowercase(Locale.ROOT)
                            ?.contains(query) == true || it.countryData.continents?.first()
                            ?.lowercase(
                                Locale.ROOT)?.contains(query) == true
                    ) {
                        filteredList.add(it)
                    }
                }
            }
            val results= FilterResults()
            results.values=filteredList
            return results
        }
        override fun publishResults(constraint:CharSequence?, results:FilterResults?){
            if(results?.values is ArrayList<*>){
                dataSet.clear()
                dataSet.addAll(results.values as ArrayList<CountriesModel>)
                notifyDataSetChanged()
            }
        }
    }

}



























