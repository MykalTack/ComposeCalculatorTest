package com.example.calculatortest

import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.DecimalFormat

class CalculatorViewModel: ViewModel() {

    val entryLive: LiveData<String> get() = entry
    private val entry = MutableLiveData("")
    val mathErrorLive: LiveData<Int> get() = mathError
    private val mathError = MutableLiveData(-1)
    var newText = ""
    private val numberRegex = Regex("[+[-]*/]")

    fun onTextChange(userEntry: String) {
        if (numberRegex.containsMatchIn(userEntry) && numberRegex.containsMatchIn(entry.value!!.toString())) {
            newText = numberRegex.replace((entry.value as CharSequence), userEntry)
            entry.value = newText
        } else {
            newText += userEntry
            entry.value = newText
        }
    }

    fun onBackspace() {
        newText = entry.value?.dropLast(1).toString()
        entry.value = newText
    }

    fun onEquals() {
        mathError.value = -1
        if (numberRegex.containsMatchIn(entry.value!!.toString())) {
            var result = 0.0
            when {
                entry.value!!.contains("+") -> {
                    val splitEquation = entry.value!!.split("+")
                    if (operandCheck(splitEquation)) {
                        result = splitEquation[0].toDouble() + splitEquation[1].toDouble()
                    } else {
                        return
                    }
                }
                entry.value!!.contains("-") -> {
                    val splitEquation = entry.value!!.split("-")
                    if (operandCheck(splitEquation)) {
                        result = splitEquation[0].toDouble() - splitEquation[1].toDouble()
                    } else {
                        return
                    }
                }
                entry.value!!.contains("*") -> {
                    val splitEquation = entry.value!!.split("*")
                    if (operandCheck(splitEquation)) {
                        result = splitEquation[0].toDouble() * splitEquation[1].toDouble()
                    } else {
                        return
                    }
                }
                entry.value!!.contains("/") -> {
                    val splitEquation = entry.value!!.split("/")
                    if (operandCheck(splitEquation)) {
                        result = splitEquation[0].toDouble() / splitEquation[1].toDouble()
                    } else {
                        return
                    }
                }
            }
            val decimalFormat = DecimalFormat("#.##")
            newText = decimalFormat.format(result)
            entry.value = newText
        } else {
            mathError.value = R.string.math_error
        }
    }

    private fun operandCheck(splitEquation: List<String>): Boolean {
        return if (splitEquation[0].isEmpty() || splitEquation[1].isEmpty()) {
            mathError.value = R.string.operand_error
            false
        } else {
            true
        }
    }
}