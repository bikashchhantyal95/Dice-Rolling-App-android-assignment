package com.bikash.dicerollingapp

import kotlin.random.Random

class Dice {

    private var _numberOfSides: Int = 0
    private var _randomDiceSideFace: Int = 0

    constructor(){
        _numberOfSides = 6
        rollDice()
    }



    constructor(numberOfSides: Int): this() {
        this._numberOfSides = numberOfSides
        rollDice()
    }

    public fun getRandomSideUp():Int{
        return _randomDiceSideFace
    }

     fun rollDice() {
         _randomDiceSideFace = Random.nextInt(1, _numberOfSides +1)
    }

}