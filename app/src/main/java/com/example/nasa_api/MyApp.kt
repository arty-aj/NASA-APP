package com.example.nasa_api

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.nasa_api.models.APOD
import com.example.nasa_api.ui.theme.NASAAPITheme
import com.example.nasa_api.utils.RetrofitInstance
import com.example.nasa_api.views.CurrentApod
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

@Composable
fun MyApp(){
    NASAAPITheme {
        //Controller to control navigation
        val navController = rememberNavController()

        //View model instance
        val viewModel: MyAppViewModel = viewModel()
        //Observer
        val apod by viewModel.apods.collectAsState()

        //List of apods... No usage yet
        var ListApod by remember {
            mutableStateOf(listOf<APOD>())
        }
        CurrentApod(apod = apod)
    }
}