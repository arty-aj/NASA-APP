package com.example.nasa_api.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nasa_api.data.APOD
import com.example.nasa_api.utils.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.TimeZone

class MyAppViewModel : ViewModel() {

    //View shows views
    //Viewmodel prepares the state for views
    //Repository get states from model
    //Model is actual data were fetching
    private val _apods : MutableStateFlow<APOD> = MutableStateFlow(APOD())
    val apods : StateFlow<APOD> = _apods.asStateFlow()

    private val _currentDate : MutableStateFlow<String> = MutableStateFlow(String())
    val currentDate : StateFlow<String> = _currentDate.asStateFlow()

    private val newLongDate = Instant.now()
    private val convertLong = newLongDate.toEpochMilli()

    //private  val system  = System.currentTimeMillis()

    init {
        dataRetrieval(convertTime(convertLong))
    }

    //Should call a repository class to get data
    fun dataRetrieval(date: String){
        viewModelScope.launch {
            //connect to data
            val response = try {
                RetrofitInstance.api.getApodDate(date)
            }catch (e: IOException) {
                Log.e("Apod IOException", "${e.message}")
                return@launch
            }catch (e: HttpException) {
                Log.e("HTTP Exception", "${e.message}")
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

    //Bug might happen on startup due to time zone.
    //Locale vs UTC
    fun convertTime(timeStamp: Long) : String{
        val localDate = Instant.ofEpochMilli(timeStamp)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

        val formattedDate = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        //updateCurrentDate(formattedDate)
        return(formattedDate)
    }

}
