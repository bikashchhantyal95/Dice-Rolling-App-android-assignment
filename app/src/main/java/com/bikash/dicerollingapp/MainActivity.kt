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

//    list of dice
    private var listOfDice: ArrayList<Any> = arrayListOf()

//    initial value of select dice
    private var selectedDice = 0
    private var hintTextForSpinner: String = "Choose the dice size"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        retrieve data from shared preference
        retrieveDataFromSharedPref()

//create spinner adapter
        val spinnerAdapter =
            ArrayAdapter(this,  android.R.layout.simple_spinner_item, listOfDice)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        binding.diceTypesId.adapter = spinnerAdapter

//        adding listener to spinner
        binding.diceTypesId.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                val selectedSpinnerItem = binding.diceTypesId.selectedItem.toString()
                if(selectedSpinnerItem != hintTextForSpinner){
                    selectedDice = binding.diceTypesId.selectedItem.toString().toInt()
                }else{
                    binding.parentOutputId.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        binding.CreateBtn.setOnClickListener(){
            val customDice = binding.customDiceId.text.toString()
//if edit text is not empty check another condition
            if (customDice.isNotEmpty()){
                if(customDice.toInt() == 0){
                    Toast.makeText(this, "The dice cannot be zero.",Toast.LENGTH_SHORT).show()
                }
                else if(listOfDice.contains(customDice.toInt())){
                    Toast.makeText(this, "The dice entered is already created.",Toast.LENGTH_SHORT).show()
                }else{
                    val key = listOfDice.size - 1
                    storeUserDiceData(key =key, value = customDice.toInt())
                }

            }else{ // show toast if text input is empty
                Toast.makeText(this, "The text is empty.",Toast.LENGTH_SHORT).show()
            }
//            clear the text input after the value is added to spinner
            binding.customDiceId.text.clear()
        }

//        added listener to rollonce btn
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

//        listenter added to rolltwice btn
        binding.RollTwiceBtn.setOnClickListener{
//             if the selectd dice in spinner in not 0 run the code in the bidy
            if(selectedDice != 0){
//                create dice from Dice class
                val dice = Dice(numberOfSides = selectedDice)

//                roll dice
                dice.rollDice()
//                save the value
                val randomFace1 = dice.getRandomSideUp()
//                set the result view to visible
                binding.parentOutputId.visibility = View.VISIBLE

//                Display the first output
                binding.rollOnceOutput.text = randomFace1.toString()
//                roll dice again
                dice.rollDice()
//                save the value after the dice is rolled
                val randomFace2 = dice.getRandomSideUp()
//                Display the random side on second output
                binding.rollTwiceOutput.text = randomFace2.toString()
                binding.rollTwiceOutput.visibility = View.VISIBLE
            }else{
//                show toast if the selected dice is 0
                Toast.makeText(this, "Select dice.", Toast.LENGTH_SHORT).show()
            }

        }
    }


//    store data locally using shared preference
private fun storeUserDiceData(key: Int, value: Int){

// Get the shared preferences object for the app,
// using a unique name to identify the preference file.
    val sharedPreference = getSharedPreferences("MyDicePreference", Context.MODE_PRIVATE)

    if(value != 0){
        val editor = sharedPreference.edit()

        // Store the 'value' parameter in the shared preferences
        // file using the 'key' parameter as the identifier.
        editor.putInt(key.toString(),value)

        // Apply the changes to the shared preferences file.
        editor.apply()
    }

    //add the value parameter to the listofdice
    listOfDice.add(value)
}

    private fun retrieveDataFromSharedPref(){
        listOfDice.add(hintTextForSpinner)
//        get shared preference file using the name of it
        val sharedPrefs = getSharedPreferences("MyDicePreference", Context.MODE_PRIVATE)

        // get all the stored values from the share preference
        val diceValues = sharedPrefs.all

        // loop through each kay-value pair
        for(i in 0 until diceValues.size -1){

            val dice = sharedPrefs.getInt(i.toString(), 0)

            // add dice if it is not equal to 0.
            if(dice != 0){
                listOfDice.add(dice)
            }

        }

    }

}