package com.example.guardiantest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val word = "GUARDIAN"
        val score = getScore(word)

        Log.d("Score", "The score is $score")

        val rack = getSevenRandomTiles()
        Log.d("RacK", "The rack is $rack")

        val sevenTilesFromBag = getSevenTilesFromBag()
        Log.d("Seven Tiles from bag", "The seven tiles from bag are $sevenTilesFromBag")

    }

    private fun getScore(word: String): Int {

        var sum = 0

        for (letter in word) {

            sum += when (letter.toUpperCase()) {
                'A', 'E', 'I', 'O', 'N', 'R', 'T', 'L', 'S', 'U' -> 1
                'D', 'G' -> 2
                'B', 'C', 'M', 'P' -> 3
                'F', 'H', 'V', 'W', 'Y' -> 4
                'K' -> 5
                'J', 'X' -> 8
                'Q', 'Z' -> 10
                else -> 0
            }

        }

        return sum
    }

    fun getSevenRandomTiles(): List<Char> {

        val tile = mutableListOf<Char>()
        val upperCaseLetters = CharRange('A', 'Z')

        while (tile.size != 7) {
            val character = upperCaseLetters.random()

            if (!tile.contains(character))
                tile.add(character)

        }
        return tile
    }

    fun getSevenTilesFromBag(): List<Char> {

        val tile = mutableListOf<Char>()

        val distributionMap = getDistributionMap()

        val bag = getBag(distributionMap)

        while (tile.size != 7) {
            val indexRange = IntRange(0, bag.size - 1)
            val character = bag[indexRange.random()]

            var allowedCount = 0

            distributionMap.forEach {
                if (it.value.contains(character))
                    allowedCount = it.key
            }

            val actualCount = tile.count { it == character }

            if (actualCount < allowedCount)
                tile.add(character)
        }

        return tile
    }

    fun getBag(distributionMap: HashMap<Int, MutableList<Char>>): List<Char> {

        val bag = mutableListOf<Char>()

        distributionMap.forEach { entry ->
            repeat(entry.key)
            {
                bag.addAll(entry.value)
            }
        }

//        Log.d("Bag", "Bag looks like $bag and size is ${bag.size}") // Size must be 98.

        return bag
    }

    fun getDistributionMap(): HashMap<Int, MutableList<Char>> {

        val map = hashMapOf<Int, MutableList<Char>>()
        map.put(12, mutableListOf('E'))
        map.put(9, mutableListOf('A', 'I'))
        map.put(8, mutableListOf('O'))
        map.put(6, mutableListOf('N', 'R', 'T'))
        map.put(4, mutableListOf('L', 'S', 'U', 'D'))
        map.put(3, mutableListOf('G'))
        map.put(2, mutableListOf('B', 'C', 'M', 'P', 'F', 'H', 'V', 'W', 'Y'))
        map.put(1, mutableListOf('K', 'J', 'X', 'Q', 'Z'))

        return map
    }
}