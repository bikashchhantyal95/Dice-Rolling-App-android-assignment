package com.bikash.dicerollingapp

import kotlin.random.Random

class Dice {

//    private varibales
    private var _numberOfSides: Int = 0
    private var _randomDiceSideFace: Int = 0


    constructor(){
//        intial value of dice
        _numberOfSides = 6
        rollDice()
    }


//secondary constructor
    constructor(numberOfSides: Int): this() {
        this._numberOfSides = numberOfSides
        rollDice()
    }

//    getter funtion for getting randomDiceSideFace
    public fun getRandomSideUp():Int{
        return _randomDiceSideFace
    }

//    function to roll dice
     fun rollDice() {
//     get random number between 1 and number of sides
    _randomDiceSideFace = Random.nextInt(1, _numberOfSides +1)
    }

}