package com.example.tipper_theexclusivetipcalc

import android.animation.ArgbEvaluator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import com.google.android.material.slider.Slider
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var tipPercent : Int
        tipPercentText.text = tipSlider.value.toString()
        var baseAmtVar : Float? = 100.0F
        val feedback = Feedback
        tipSlider.addOnChangeListener { slider, value, fromUser ->

            tipPercent = value.toInt()
            tipPercentText.text = "${tipPercent}%"
            computeTipAndTotal()
            updateFeedback(value.toInt())

        }

        base.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if(p0.toString()==""){
                    tip.text = ""
                    total.text = ""
                    return
                }
                baseAmtVar = p0.toString().toFloat()
                computeTipAndTotal()
            }

        })



    }

    private fun updateFeedback(value:Int) {
        val tipDesc = when(value){
            in 0..9 -> "Poor"
            in 10..14 ->"Acceptable"
            in 15..20 ->"Good"
            in 21..25 ->"Great"
            else ->"Amazing"
        }
        Feedback.text = tipDesc
        val color = ArgbEvaluator().evaluate(
            value/tipSlider.valueTo,
            ContextCompat.getColor(this,R.color.worstTip),
            ContextCompat.getColor(this,R.color.BestTip),
        )as Int
        Feedback.setTextColor(color)
    }

    private fun computeTipAndTotal() {
        //Get tipPercentage and baseAmount
        val tipPercentage = tipSlider.value
        val baseAmt = base.text.toString().toFloat()

        //Calc tipVar and totalVar
        val tipVar = (tipPercentage*baseAmt)/100
        val totalVar = tipVar + baseAmt

        //Assign values to the views
        tip.text = "%.2f".format(tipVar)
        total.text = "%.2f".format(totalVar)

    }
}