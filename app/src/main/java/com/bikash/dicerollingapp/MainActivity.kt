package com.bikash.dicerollingapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.bikash.dicerollingapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var listOfDice: ArrayList<Int> = arrayListOf(4, 6, 8, 10, 12, 20)
    private var selectedDice = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        retrieveDataFromSharedPref()


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

        binding.CreateBtn.setOnClickListener(){
            val customDice = binding.customDiceId.text.toString()

            if (customDice.isNotEmpty()){
                if(customDice.toInt() == 0){
                    Toast.makeText(this, "The dice cannot be zero.",Toast.LENGTH_SHORT).show()
                }
                if(listOfDice.contains(customDice.toInt())){
                    Toast.makeText(this, "The dice entered is already created.",Toast.LENGTH_SHORT).show()
                }
                val key = listOfDice.size - 1
                storeUserDiceData(key =key, value = customDice.toInt())
            }
        }

        binding.RollOnceBtn.setOnClickListener{
            if(selectedDice != 0){
                val dice = Dice(numberOfSides = selectedDice)
                dice.rollDice()
                val randomFace = dice.getRandomSideUp()
                binding.rollTwiceOutput.visibility = View.GONE
                binding.parentOutputId.visibility = View.VISIBLE
                binding.rollOnceOutput.text = randomFace.toString()
            }else{
                Toast.makeText(this, "Select dice", Toast.LENGTH_SHORT).show()
            }

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


private fun storeUserDiceData(key: Int, value: Int){
    val sharedPreference = getSharedPreferences("MyDicePreference", Context.MODE_PRIVATE)
    val editor = sharedPreference.edit()
    editor.putInt(key.toString(),value)
    editor.apply()
    listOfDice.add(value)
}

    private fun retrieveDataFromSharedPref(){
        val sharedPrefs = getSharedPreferences("MyDicePreference", Context.MODE_PRIVATE)
        val diceValues = sharedPrefs.all
        for(i in 0 until diceValues.size -1){
            val dice = sharedPrefs.getInt(i.toString(), 0)
            listOfDice.add(dice)
        }

    }



}