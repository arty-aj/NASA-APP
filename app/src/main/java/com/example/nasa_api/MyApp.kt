package com.example.nasa_api

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nasa_api.ui.theme.NASAAPITheme
import com.example.nasa_api.viewmodel.MyAppViewModel
import com.example.nasa_api.ui.CurrentApod

@Composable
fun MyApp(){
    NASAAPITheme {
        //Controller to control navigation
        //val navController = rememberNavController()

        //View model instance
        val viewModel: MyAppViewModel = viewModel()
        //Observer
        //WithLifeCycle() is lifecycle aware.
        //Does not collect state updates from the flow at inappropriate times.
        //Such as when we move to different page.
        val apod by viewModel.apods.collectAsState()
        val currentDate by viewModel.currentDate.collectAsState()

        CurrentApod(
            apod = apod
        ) {
            val convertedDate = viewModel.convertTime(it)
            viewModel.dataRetrieval(convertedDate)
        }
    }
}