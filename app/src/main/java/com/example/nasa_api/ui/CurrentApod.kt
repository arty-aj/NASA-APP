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
import androidx.compose.material3.CircularProgressIndicator
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
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import com.example.nasa_api.data.APOD
import com.example.nasa_api.ui.components.ExpandingText
import com.example.nasa_api.ui.components.MyDateClicker
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable
import java.time.Instant
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentApod(
    apod: APOD,
    modifier: Modifier = Modifier,
    onDateClicked: (Long) -> Unit
) {
    //1
    //State hoisting example
    var showDatePicker by rememberSaveable{
        mutableStateOf(false)
    }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = getTodaysDate(),
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val selectedDate = turnToUTC(utcTimeMillis)
                return selectedDate <= getTodaysDate()
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
        SubcomposeAsyncImage(
            model = apod.hdurl,
            contentDescription = apod.title,
            modifier
                .padding(end = 10.dp, start = 10.dp, bottom = 10.dp)
                .clip(RoundedCornerShape(10.dp))
                .zoomable(zoomState),
            loading = {CircularProgressIndicator() }
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
                datePickerState.selectedDateMillis?.let { date ->
                    val newDate = turnToUTC(date)
                    onDateClicked(newDate)
                }
            }
        )
}
fun getTodaysDate() : Long {
    return Instant.now().toEpochMilli()
}

fun turnToUTC(date: Long): Long {
    val selectedInstant = Instant.ofEpochMilli(date)
        .atZone(ZoneId.of("UTC"))
        .toLocalDate()

    return selectedInstant.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
}

