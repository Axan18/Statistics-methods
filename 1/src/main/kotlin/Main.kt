package org.example

import kotlin.math.pow
import kotlin.random.Random

fun main() {
    val moneyA = 50
    val moneyB = 50
    val resultsExp = (1..100).associateBy { winProbA ->
        val probability = winProbA / 100.0
        Pair(probability, List(100) {
            runGame(moneyA, moneyB, probability)
        }.count { it }.div(100.0))
    }
    val resultsAnalytical = (1..100).associateBy { winProbA ->
        val probabilityA = winProbA / 100.0
        Pair(probabilityA, analyticalCompute(moneyA, moneyB, probabilityA, 1- probabilityA))
    }
    println(resultsExp)
    println(resultsAnalytical)

}

fun analyticalCompute(
    moneyA: Int,
    moneyB: Int,
    winProbA: Double,
    winProbB: Double
): Double = (winProbB / winProbA).let {
    (it.pow(moneyA) - it.pow(moneyA + moneyB)) / (1 - it.pow(moneyA + moneyB))
}

fun runGame(
    moneyA: Int,
    moneyB: Int,
    winProbA: Double,
): Boolean {
    var moneyA = moneyA
    var moneyB = moneyB
    while (true) {
        Random.nextDouble().let {
            if (it < winProbA)
                moneyA -= 1
            else
                moneyB -= 1
        }
        if (moneyA == 0 || moneyB == 0)
            break
    }
    return moneyB == 0
}