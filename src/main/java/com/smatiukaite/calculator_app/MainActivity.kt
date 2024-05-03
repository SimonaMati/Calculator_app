package com.smatiukaite.calculator_app

/**************************
Class: CSC244
Student: Simona Matiukaite
Project: Calculator
 ***************************/

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import net.objecthunter.exp4j.ExpressionBuilder
import kotlin.math.ln
import kotlin.math.log10
import kotlin.math.pow

const val HISTORY_EXTRA = "csc244.switchingactivity.ARRAY_LIST"

var result = 0.0

class MainActivity : AppCompatActivity() {

    private var mainText: TextView? = null
    private var history: ArrayList<String>? = null
    private var historyFileModel: HistoryFileModel? = null

    private var currentValue = 0.0
    var savedValue = 0.0
    var dirtyOperator = "none"
    var finalOperator = "none"

    var signBeforeNumber = "+"
    var record = ""
    private val sqrtSymbol = "\u221A"
    private val powerOfN = "\u005E"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        history = ArrayList()
        mainText = findViewById<View>(R.id.main_text) as TextView

        historyFileModel = HistoryFileModel(this)
        history = historyFileModel!!.getHistory()

        mainText!!.text = ""
        for (i in 0..9) {
            val resID = resources.getIdentifier("button$i", "id", packageName)
            val button = findViewById<Button>(resID)

            button.setOnClickListener {
                if (finalOperator == "none") {
                    val currentText = mainText!!.text.toString()
                    val buttonText = button.text.toString()
                    mainText!!.text = currentText + buttonText
                    val currentValue1 = "$currentText$buttonText".toInt()

                    currentValue = currentValue1.toDouble()

                } else if (finalOperator != "none") {
                    val currentText1 = mainText!!.text.toString()
                    val buttonText = button.text.toString()
                    mainText!!.text = currentText1 + buttonText
                    val currentValue1 = "$currentText1$buttonText".toInt()

                    currentValue = currentValue1.toDouble()
                }

                //Changing the sign
//                if (signBeforeNumber == "-" && finalOperator == "none") {
//                    System.out.println("before " + currentValue)
//                    currentValue = "$signBeforeNumber$currentValue".toDouble()
//                    System.out.println("after " + currentValue)
//                }

                //Checking the numbers and conditions
                if (result != 0.0 && finalOperator != "none") {
                    //Writing results to history
                        record = "$savedValue $finalOperator $currentValue".toString()
                        if (record.isNotEmpty()) {
                            history!!.add(record)

                    }

                    System.out.println("YOU ENTERED WITH RESULT")

                    getResult(finalOperator, result, currentValue)

                } else if (result == 0.0 && finalOperator != "none") {
                    //Writing results to history
//                    if (finalOperator == "nSqrRoot") {
//                        record = "$savedValue $sqrtSymbol $currentValue".toString()
//                        if (record.isNotEmpty()) {
//                            history!!.add(record)
//                        }
//                    } else if (finalOperator == "powerOfN") {
//                        record = "$savedValue $powerOfN $currentValue".toString()
//                        if (record.isNotEmpty()) {
//                            history!!.add(record)
//                        }
//                    } else {
                        record = "$savedValue $finalOperator $currentValue".toString()
                        if (record.isNotEmpty()) {
                            history!!.add(record)
                        }

                    System.out.println("YOU ENTERED WITHOUT RESULT")
                    getResult(finalOperator, savedValue, currentValue)
                }
            }


        } //Addition button
        val additionButton = findViewById<Button>(R.id.addition_button)
        additionButton.setOnClickListener {
            dirtyOperator = "+"
            savedValue = currentValue
            finalOperator = dirtyOperator
            dirtyOperator = "none"
            currentValue = 0.0
            mainText!!.text = ""
        }

        //Subtraction button
        val subtractionButton = findViewById<Button>(R.id.subraction_button)
        subtractionButton.setOnClickListener {
            dirtyOperator = "-"
            savedValue = currentValue
            finalOperator = dirtyOperator
            dirtyOperator = "none"
            currentValue = 0.0
            mainText!!.text = ""
        }

        //Multiplication button
        val multiplicationButton = findViewById<Button>(R.id.multiplication_button)
        multiplicationButton.setOnClickListener {
            System.out.println("CURRENT " + currentValue + " RESULT " + result + " SAVED " + savedValue)
            dirtyOperator = "*"
            savedValue = currentValue
            finalOperator = dirtyOperator
            dirtyOperator = "none"
            currentValue = 0.0
            mainText!!.text = ""
        }

        //Division button
        val divisionButton = findViewById<Button>(R.id.division_button)
        divisionButton.setOnClickListener {
            dirtyOperator = "/"
            savedValue = currentValue
            finalOperator = dirtyOperator
            dirtyOperator = "none"
            currentValue = 0.0
            mainText!!.text = ""
        }

        //Point button
        val pointButton = findViewById<Button>(R.id.point_button)
        pointButton.setOnClickListener {

        }

        //Sign changing button
        val signChangeButton = findViewById<Button>(R.id.sign_change_button)
        signChangeButton.setOnClickListener {
            val currentText = mainText!!.text.toString()
            signBeforeNumber = "-"
            System.out.println("before org " + currentValue)
            if (currentValue > 0.0 || result > 0.0) {
                mainText!!.text = signBeforeNumber + currentText
                currentValue = "$signBeforeNumber$currentValue".toDouble()
                System.out.println("after org " + currentValue)
            } else if (currentValue < 0.0 || result < 0.0) {
                mainText!!.text = currentText
            } else if (currentValue == 0.0 && dirtyOperator == "none") {
                mainText!!.text = signBeforeNumber.toString()
            }
        }

        //Square root button
        val squareRootButton = findViewById<Button>(R.id.square_root)
        squareRootButton?.setOnClickListener {
            System.out.println("CURRENT VALUE IN SQRT " + currentValue + " RESULT " + result)
            val two = 2
            var value = 0.0

            if (result != 0.0) {
                currentValue = result
            }

            value = currentValue
            currentValue = currentValue.toDouble().pow(0.5).toDouble()
            val currentText = mainText!!.text.toString()
            val buttonText = squareRootButton.text.toString()
            mainText!!.text = currentValue.toString()

            record = "$sqrtSymbol $value".toString()
            if (record.isNotEmpty()) {
                history!!.add(record)
            }

            result = currentValue
            currentValue = 0.0
        }

        //N square root button
        val nSquareRootButton = findViewById<Button>(R.id.n_square_root)
        nSquareRootButton?.setOnClickListener {
            dirtyOperator = sqrtSymbol
            savedValue = currentValue
            finalOperator = dirtyOperator
            dirtyOperator = "none"
            currentValue = 0.0
            mainText!!.text = ""
        }

        //X raised by 2
        val xToThePowerOf2 = findViewById<Button>(R.id.x_raised_by_2)
        xToThePowerOf2?.setOnClickListener {
            var two = 2
            if (result != 0.0) {
                currentValue = result
            }

            savedValue = currentValue
            currentValue *= currentValue
            val currentText = mainText!!.text.toString()
            val buttonText = xToThePowerOf2.text.toString()
            mainText!!.text = currentValue.toString()



            record = "$savedValue $powerOfN $two".toString()
            if (record.isNotEmpty()) {
                history!!.add(record)
            }

            result = currentValue
            currentValue = 0.0
        }

        //X raised by n
        val xToThePowerOfN = findViewById<Button>(R.id.x_raised_by_n)
        xToThePowerOfN?.setOnClickListener {
            dirtyOperator = powerOfN
            savedValue = currentValue
            finalOperator = dirtyOperator
            dirtyOperator = "none"
            currentValue = 0.0
            mainText!!.text = ""
        }

        //log button
        val logButton = findViewById<Button>(R.id.log)
        logButton?.setOnClickListener {
            val logor = "log"
            var value = currentValue.toInt()

            if (result != 0.0) {
                value = result.toInt()
                currentValue = result
            }

            currentValue = log10(currentValue)
            val currentText = mainText!!.text.toString()
            val buttonText = logButton.text.toString()
            mainText!!.text = currentValue.toString()

            record = "$logor($value)".toString()
            if (record.isNotEmpty()) {
                history!!.add(record)
            }

            System.out.println(currentValue)
            result = currentValue
            currentValue = 0.0
        }

        //ln button
        val lnButton = findViewById<Button>(R.id.ln)
        lnButton?.setOnClickListener {
            val ln = "ln"
            val value = currentValue.toInt()

            currentValue = ln(currentValue)
            val currentText = mainText!!.text.toString()
            val buttonText = lnButton.text.toString()
            mainText!!.text = currentValue.toString()

            record = "$ln($value)".toString()
            if (record.isNotEmpty()) {
                history!!.add(record)
            }

            result = currentValue
            currentValue = 0.0
        }

        //Clear button
        val clearButton = findViewById<Button>(R.id.clear_button)
        clearButton.setOnClickListener {
            mainText!!.text = ""
            result = 0.0
            dirtyOperator = "none"
            finalOperator = "none"
            currentValue = 0.0
            savedValue = 0.0
            signBeforeNumber = "+"
        }

        //Go to History List View
        val historyButton = findViewById<Button>(R.id.history_button)
        historyButton.setOnClickListener {
            val intent = Intent(this@MainActivity, History::class.java)
            intent.putStringArrayListExtra(HISTORY_EXTRA, history)
            startActivity(intent)
            mainText!!.text = ""
            result = 0.0
            dirtyOperator = "none"
            finalOperator = "none"
            currentValue = 0.0
            savedValue = 0.0
            signBeforeNumber = "+"
            true
        }

        //Passing a value from the History
        val passedNumberFromHistory = intent.getStringExtra(NUMBER_FROM_HISTORY)

        if (passedNumberFromHistory != null) {
            if (passedNumberFromHistory.isNotEmpty()) {
                val resultFrmHistoryActivity =
                    ExpressionBuilder(passedNumberFromHistory).build().evaluate()
                mainText!!.text = resultFrmHistoryActivity.toDouble().toString()
                currentValue = "$resultFrmHistoryActivity".toDouble()
                result = currentValue
                System.out.println("CURRENT HISTORY VALUE " + currentValue)
            } else {
                System.out.println("Value from History is " + passedNumberFromHistory)
            }
        }
    }


    //Equal Button
    fun getResult(operator: String, firstValue: Double, secondValue: Double) {
        val equalButton = findViewById<Button>(R.id.equal_button)
        equalButton.setOnClickListener {

            when (operator) {
                "+" -> result = firstValue + secondValue
                "-" -> result = firstValue - secondValue
                "*" -> result = firstValue * secondValue
                "/" -> result = firstValue / secondValue
                "\u221A" -> result = firstValue.pow(1 / secondValue)
                "\u005E" -> result = firstValue.toDouble().pow(secondValue)
            }

            mainText!!.text = result.toString()
            dirtyOperator = "none"
            finalOperator = "none"
        }
    }

    override fun onPause() {
        Log.d("PAUSE", "THIS")
        historyFileModel!!.save()
        super.onPause()
    }

    override fun onDestroy() {
        Log.d("DESTROY", "THIS")
        historyFileModel!!.deleteFile()
        super.onDestroy()
    }


}