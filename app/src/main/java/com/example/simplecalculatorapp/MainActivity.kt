package com.example.simplecalculatorapp

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var solutionTv: TextView
    private lateinit var resultTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        solutionTv = findViewById(R.id.soluction)
        resultTv = findViewById(R.id.result)

        setupNumberButtons()
        setupOperatorButtons()
        setupActions()
    }

    // ================= NUMBER BUTTONS =================
    private fun setupNumberButtons() {

        val numberButtons = listOf(
            R.id.button_0, R.id.button_1, R.id.button_2,
            R.id.button_3, R.id.button_4, R.id.button_5,
            R.id.button_6, R.id.button_7, R.id.button_8,
            R.id.button_9
        )

        for (id in numberButtons) {
            findViewById<MaterialButton>(id).setOnClickListener {
                val btn = it as MaterialButton

                if (solutionTv.text.toString() == "0") {
                    solutionTv.text = btn.text   // remove 0 first time
                } else {
                    solutionTv.append(btn.text)
                }
            }
        }
    }

    // ================= OPERATORS =================
    private fun setupOperatorButtons() {

        findViewById<MaterialButton>(R.id.button_Addition).setOnClickListener {
            appendText("+")
        }

        findViewById<MaterialButton>(R.id.button_Subtraction).setOnClickListener {
            appendText("-")
        }

        findViewById<MaterialButton>(R.id.button_multible).setOnClickListener {
            appendText("*")
        }

        findViewById<MaterialButton>(R.id.button_divide).setOnClickListener {
            appendText("/")
        }

        findViewById<MaterialButton>(R.id.button_open_bracket).setOnClickListener {
            appendText("(")
        }

        findViewById<MaterialButton>(R.id.button_close_bracket).setOnClickListener {
            appendText(")")
        }

        findViewById<MaterialButton>(R.id.button_point).setOnClickListener {
            appendText(".")
        }
    }

    // ================= ACTION BUTTONS =================
    private fun setupActions() {

        // CLEAR ALL
        findViewById<MaterialButton>(R.id.button_AC).setOnClickListener {
            solutionTv.text = ""
            resultTv.text = "0"
        }

        // BACKSPACE (C)
        findViewById<MaterialButton>(R.id.button_C).setOnClickListener {
            val text = solutionTv.text.toString()
            if (text.isNotEmpty()) {
                solutionTv.text = text.substring(0, text.length - 1)
            }
        }

        // EQUAL
        findViewById<MaterialButton>(R.id.button_equal).setOnClickListener {
            calculateResult()
        }
    }

    // ================= APPEND TEXT SAFELY =================
    private fun appendText(value: String) {
        solutionTv.append(value)
    }

    // ================= CALCULATE RESULT =================
    private fun calculateResult() {
        try {
            val expressionText = solutionTv.text.toString()

            if (expressionText.isEmpty()) {
                return
            }

            val expression = ExpressionBuilder(expressionText).build()
            val result = expression.evaluate()

            // Convert to clean number (no .0 if integer)
            val longResult = result.toLong()

            resultTv.text = if (result == longResult.toDouble()) {
                longResult.toString()
            } else {
                result.toString()
            }

        } catch (e: Exception) {
            Toast.makeText(this, "Invalid Expression", Toast.LENGTH_SHORT).show()
        }
    }
}