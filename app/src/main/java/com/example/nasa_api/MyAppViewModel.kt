package com.example.nasa_api

import android.support.v4.app.INotificationSideChannel._Parcel
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nasa_api.models.APOD
import com.example.nasa_api.utils.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class MyAppViewModel : ViewModel() {

    private val _apods : MutableStateFlow<APOD> = MutableStateFlow(APOD())
    val apods : StateFlow<APOD> = _apods.asStateFlow()

    init {
        dataRetrieval()
    }

    //Should call a repository class to get data
    private fun dataRetrieval(){
        viewModelScope.launch {
            //connect to data
            val response = try {
                RetrofitInstance.api.getApod()
            }catch (e: IOException) {
                Log.e("Apod IOException", "$e")
                return@launch
            }catch (e: HttpException) {
                return@launch
            }
            if(response.isSuccessful && response.body() != null){
                updateApods(response.body()!!)
            }
        }
    }

    private fun updateApods(apod: APOD){
        _apods.update {
            apod
        }
    }
}