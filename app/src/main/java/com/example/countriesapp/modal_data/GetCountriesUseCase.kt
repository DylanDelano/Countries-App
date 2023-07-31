package com.example.countriesapp.modal_data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCountriesUseCase @Inject constructor( private val repository: country_repo) {
    operator fun invoke(): Flow<Resource<List<countries_data>>> = flow {

        try {
            emit(Resource.Loading())
            val countries = repository.getAllCountries()
            emit(Resource.Success(countries))
        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage?: "An unexpected error occurred"))

        }catch (e: IOException){
            emit(Resource.Error("Couldn't reach server. Check your connection"))
        }

    }
}