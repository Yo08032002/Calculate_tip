package com.example.calculate_tip

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.calculate_tip.databinding.ActivityMainBinding
import java.sql.Types.NULL
import java.text.NumberFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculate.setOnClickListener { claculateTip() }

        // Set up a key listener on the EditText field to listen for "enter" button presses
        binding.costText.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(
                view,
                keyCode
            )
        }
    }

    private fun claculateTip() {

        val cost=binding.costText.text.toString().toDoubleOrNull()
        if(cost==null){
            Toast.makeText(this, "Please Enter Cost", Toast.LENGTH_SHORT).show()
            binding.result.text=""
            return
        }
//        val cost = input.toDouble()

        val selectedtip=binding.tipOption.checkedRadioButtonId

        val tip=when(selectedtip){
            R.id.amazing->0.2
            R.id.good->0.18
            else->0.15
        }
        var result=cost*tip
        if(binding.roundUp.isChecked){
            result=kotlin.math.ceil(result)
        }
        Locale.setDefault(Locale("en", "IN"))
        val ans=NumberFormat.getCurrencyInstance().format(result)
        binding.result.text= getString(R.string.tip_amount,ans)
    }

    private fun handleKeyEvent(view: View,keyCode:Int):Boolean{
        if(keyCode==KeyEvent.KEYCODE_ENTER){
            val variable  = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            variable.hideSoftInputFromWindow(view.windowToken,0)
            return true
        }
        return false
    }
}