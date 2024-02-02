package com.example.nasa_api.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.nasa_api.data.APOD
import com.example.nasa_api.ui.components.ExpandingText
import com.example.nasa_api.ui.components.MyDateClicker
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable
import java.util.Calendar
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentApod(
    apod: APOD,
    modifier: Modifier = Modifier,
    onDateClicked: (Long) -> Unit
) {
    //State hoisting example
    var showDatePicker by rememberSaveable{
        mutableStateOf(false)
    }

    val datePickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates{
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val calendar  = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                return calendar.timeInMillis > utcTimeMillis
            }
        }
    )

    val zoomState = rememberZoomState()

    Column (
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = apod.title,
            modifier
                .fillMaxWidth()
                .padding(top = 15.dp),
            textAlign = TextAlign.Center
        )
        Spacer(
            modifier
                .height(15.dp)
        )
        AsyncImage(
            model = apod.hdurl,
            contentDescription = apod.title,
            modifier
                .padding(end = 10.dp, start = 10.dp, bottom = 10.dp)
                .clip(RoundedCornerShape(10.dp))
                .zoomable(zoomState)
        )
        ExpandingText(text = apod.explanation)

        Button(
            onClick = { showDatePicker = true }
        ) {
            Text(
                apod.date
            )
        }
    }
    if (showDatePicker)
        MyDateClicker(
            state = datePickerState,
            onDismiss = {
                showDatePicker = false
                //let, getting datepicker state, ? null check, if it is null
                //it doesnt run the lambda, if it isnt null it will run it
                datePickerState.selectedDateMillis?.let { date ->
                    onDateClicked(date)
                }
            }
        )
}

