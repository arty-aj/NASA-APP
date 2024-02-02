package com.example.nasa_api.ui.components

import android.widget.Toast
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDateClicker(
    state: DatePickerState,
    onDismiss: (Boolean) -> Unit
){
    //Composable stateless
    DatePickerDialog(
        onDismissRequest = {
            onDismiss(false)
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDismiss(false)

                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss(false)
                }
            ) {
                Text("CANCEL")
            }
        }
    ) {
        DatePicker(
            state = state
        )
    }
}

