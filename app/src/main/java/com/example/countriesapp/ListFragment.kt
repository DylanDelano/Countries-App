package com.example.countriesapp

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countriesapp.databinding.FragmentListBinding
import com.example.countriesapp.domain.CountriesModel
import com.example.countriesapp.modal_data.countries_data
import com.example.countriesapp.ui.CountryAdapter
import com.example.countriesapp.ui.CountryViewModel
import com.example.countriesapp.ui.FilterViewModel
import com.example.countriesapp.util.ConnectivityObserver
import com.example.countriesapp.util.NetworkConnectivityObserver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.Comparator
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list) {
    private val countryViewModel by viewModels<CountryViewModel>()
    private lateinit var countryAdapter: CountryAdapter
    private var countriesData = mutableListOf<countries_data>()
    private var mSectionList: ArrayList<CountriesModel>? = null
    private val filterViewModel:FilterViewModel by activityViewModels()
    @Inject lateinit var connectivityObserver: ConnectivityObserver

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadCountry()
        connectivityObserver.observeNetworkStatus().asLiveData().observe(viewLifecycleOwner){
            it?.let {
                if (it.name =="Lost"){
                    binding.recyclerView.visibility= View.INVISIBLE
                    binding.noInternet.visibility= View.VISIBLE
                    Toast.makeText(requireContext(), "No Internet Connection", Toast.LENGTH_SHORT).show()
                }else{
                    binding.recyclerView.visibility= View.VISIBLE
                    binding.noInternet.visibility=View.INVISIBLE
                    loadCountry()
                    setUpSearchView()
                }
            }
        }
    }

    private fun showData(){
        lifecycleScope.launch {
            countryViewModel.state.collect{
                it.country.let { countries ->
                    val countriesModel: ArrayList<CountriesModel> = ArrayList()
                    countriesData = countries.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER){it.name.official!!}).toMutableList()
                    for (i in 0 until countriesData.size){
                        countriesModel.add(CountriesModel(countriesData[i].name.official, countriesData[i], false))
                    }
                    mSectionList = ArrayList()
                    getHeaderListLatter(countriesModel)
                    countryAdapter=CountryAdapter(mSectionList!!)
                    binding.recyclerView.setHasFixedSize(true)
                    binding.recyclerView.layoutManager = LinearLayoutManager(context)
                    binding.recyclerView.adapter= countryAdapter
                    }
                }
            }
        }
    private fun getHeaderListLatter(countryList: ArrayList<CountriesModel>){
        countryList.sortWith(Comparator{country1, country2 ->
            country1?.countryData?.name?.official?.uppercase(Locale.getDefault())?.compareTo(country2?.countryData?.name?.official.toString().uppercase(
                Locale.getDefault())) ?: 0
        })
        var lastHeader: String? = ""
        val size: Int = countryList.size
        for (i in 0 until size){
            val user = countryList[i]
            val header = user.countryData.name.official?.toCharArray()?.first()?.uppercase(Locale.getDefault())
            if (!TextUtils.equals(lastHeader, header)){
                lastHeader=header
                mSectionList!!.add(CountriesModel(header!!, user.countryData, true))
            }
            mSectionList!!.add(user)

        }
    }
    private fun setUpSearchView(){
        binding.searchCountries.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                countryAdapter.getFilter().filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                countryAdapter.getFilter().filter(newText)
                return true
            }
        })
    }
    private fun loadCountry(){
        filterViewModel.filter.observe(viewLifecycleOwner){
            filters -> filters?.let {
                if (filters.isEmpty()){
                    showData()
                }else{
                    countryAdapter.getFilter().filter(filters.first().childTitle)
                }
        }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }











}