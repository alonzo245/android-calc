package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat

enum class CalculatorModes {
    None, Add, Subtract, Multiply
}

class MainActivity : AppCompatActivity() {

    private var lastButtonWasMode = false
    private var currentMode = CalculatorModes.None
    private var labelString = ""
    private var savedNum = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupCalculator()
    }

    private fun setupCalculator() {

        var allButtons =
            arrayOf(button0, button1, button2, button3, button4, button5, button6,button7, button8, button9)
        for (i in allButtons.indices) {
            allButtons[i].setOnClickListener { didPressNumber(i) }
        }

        buttonPlus.setOnClickListener { changeMode(CalculatorModes.Add) }
        buttonMinus.setOnClickListener { changeMode(CalculatorModes.Subtract) }
        buttonMultiply.setOnClickListener { changeMode(CalculatorModes.Multiply) }
        buttonEquals.setOnClickListener { didPressEquals() }
        buttonC.setOnClickListener { didPressClear() }
    }

    private fun didPressEquals() {

        if(lastButtonWasMode){
            return
        }
        val labelInt = labelString.toInt()
        when(currentMode){
            CalculatorModes.Add -> savedNum += labelInt
            CalculatorModes.Subtract -> savedNum -= labelInt
            CalculatorModes.Multiply -> savedNum *= labelInt
            CalculatorModes.None -> return
        }

        currentMode = CalculatorModes.None
        labelString = "$savedNum"
        updateText()
        lastButtonWasMode = true
        textView2.text = ""
    }

    private fun didPressClear() {
        lastButtonWasMode = false
        currentMode = CalculatorModes.None
        labelString = ""
        savedNum = 0
        textView.text = "0"
    }

    private fun updateText() {
        if(labelString.length >  11){
            didPressClear()
            textView.text = "Too Big"
            return
        }

        val labelInt = labelString.toInt()
        labelString = labelInt.toString()

        if (currentMode === CalculatorModes.None) {
            savedNum = labelInt
        }

        val df = DecimalFormat("#,###")
        textView.text = df.format(labelInt)
    }

    private fun changeMode(mode: CalculatorModes) {

        if(savedNum == 0){
            return
        }
        currentMode = mode
        lastButtonWasMode = true

        var current = textView2.text.toString()
        val modeMap = mapOf("Add" to "+","Subtract" to "-","Multiply" to "*")
        textView2.text = "$current${modeMap[currentMode.toString()]}"
    }

    private fun didPressNumber(num: Int) {
        val strVal = num.toString()

        if (lastButtonWasMode) {
            lastButtonWasMode = false
            labelString = "0"
        }
        labelString = "$labelString$strVal"
        updateText()

        var current = textView2.text.toString()
        textView2.text = "$current$strVal"
    }
}
