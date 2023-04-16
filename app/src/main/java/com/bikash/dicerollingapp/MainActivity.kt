package com.bikash.dicerollingapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.bikash.dicerollingapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var listOfDice: ArrayList<Int> = arrayListOf(4, 6, 8, 10, 12, 20)
    private var selectedDice = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val spinnerAdapter =
            ArrayAdapter(this,  android.R.layout.simple_spinner_item, listOfDice)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        binding.diceTypesId.adapter = spinnerAdapter

        binding.diceTypesId.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedDice = binding.diceTypesId.selectedItem.toString().toInt()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }


        binding.RollOnceBtn.setOnClickListener{
            val dice = Dice(numberOfSides = selectedDice)
            dice.rollDice()
            val randomFace = dice.getRandomSideUp()
            binding.rollTwiceOutput.visibility = View.GONE
            binding.parentOutputId.visibility = View.VISIBLE
            binding.rollOnceOutput.text = randomFace.toString()
        }

        binding.RollTwiceBtn.setOnClickListener{
            val dice = Dice(numberOfSides = selectedDice)
            dice.rollDice()
            val randomFace1 = dice.getRandomSideUp()
            binding.parentOutputId.visibility = View.VISIBLE
            binding.rollOnceOutput.text = randomFace1.toString()
            dice.rollDice()
            val randomFace2 = dice.getRandomSideUp()
            binding.rollTwiceOutput.text = randomFace2.toString()
            binding.rollTwiceOutput.visibility = View.VISIBLE
        }
    }




}