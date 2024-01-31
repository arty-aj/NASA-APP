package com.example.nasa_api.views

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import java.util.Calendar
import java.util.Date

@Composable
fun MyDateClicker(
    context: Context
): DatePickerDialog {
    val year: Int
    val month: Int
    val day: Int

    val calendar = Calendar.getInstance()
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    val date = remember { mutableStateOf("") }
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            date.value = "$dayOfMonth/${month + 1}/$year"
        }, year, month, day
    )
    return datePickerDialog
}



//    val state = rememberDatePickerState()
//    val openDialog = remember {
//        mutableStateOf(true)
//    }
//    var pickedDate by remember {
//        mutableStateOf(LocalDate.now())
//    }
//    if(openDialog.value){
//        DatePickerDialog(
//            onDismissRequest = {
//                openDialog.value = false
//            },
//            confirmButton = {
//                TextButton(
//                    onClick = {
//                        onEndClick
//                        openDialog.value = false
//                        state.selectedDateMillis?.let {
//                            convertMillisToDate(it)
//                        }
//                    }
//                ) {
//                    Text("OK")
//                }
//            },
//            dismissButton = {
//                TextButton(
//                    onClick = {
//                        openDialog.value = false
//                        onEndClick
//                    }
//                ) {
//                    Text("CANCEL")
//                }
//            }
//        ) {
//            DatePicker(
//                state = state
//            )
//        }
//    }