package com.example.nasa_api.views

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.nasa_api.MainActivity
import com.example.nasa_api.models.APOD
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable

@Composable
fun CurrentApod(apod: APOD, modifier: Modifier = Modifier) {
    var date by remember{
        mutableStateOf("Choose different date")
    }

    //State hoisting example?
    var showDatePicker by remember{
        mutableStateOf(false)
    }

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
            onClick = { showDatePicker = true }//showDatePicker = true
        ) {
            Text(
                text = apod.date //$showDatePicker Hello",
            )
        }
    }

}
