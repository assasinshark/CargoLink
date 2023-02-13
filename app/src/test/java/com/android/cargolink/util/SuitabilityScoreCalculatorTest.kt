package com.android.cargolink.util

import com.android.cargolink.entities.Driver
import com.android.cargolink.entities.Shipment
import org.junit.Test

class SuitabilityScoreCalculatorTest {
    @Test
    fun testCalculateSuitabilityScoreEven() {
        val driver = Driver("Enda Krammer")
        val shipment = Shipment("1234 Address St Apt. 64")
        val score = ExerciseSuitabilityScoreCalculator().suitabilityScore(driver, shipment)
        assert(score == 4.5) { "Score doesn't match, expected: 4.5, actual: $score" }
    }

    @Test
    fun testCalculateSuitabilityScoreOdd() {
        val driver = Driver("Enda Krammer")
        val shipment = Shipment("1234 Address Apt. 64")
        val score = ExerciseSuitabilityScoreCalculator().suitabilityScore(driver, shipment)
        assert(score == 2.0) { "Score doesn't match, expected: 3.0, actual: $score" }
    }

    @Test
    fun testBuildSuitabilityScoreMatrix() {
        val drivers = listOf<Driver>(Driver("Enda Krammer"),Driver("Oscar Carter"), Driver("Michael Post"))
        val shipments = listOf<Shipment>(Shipment("1234 Address St Apt. 64"), Shipment("1234 Address Apt. 64"), Shipment("746 Unknown Parkway Suite 35"))
        val res = ExerciseSuitabilityScoreCalculator().suitabilityScoreMatrix(drivers, shipments)
        assert(res[0][0] == 4.5 && res[0][1] == 2.0 && res[0][2] == 2.0) { "First Driver Suitability Scores are not the expected" }
        assert(res[1][0] == 4.5 && res[1][1] == 3.0 && res[1][2] == 4.5) { "Second Driver Suitability Scores are not the expected" }
        assert(res[2][0] == 4.5 && res[2][1] == 6.0 && res[2][2] == 4.0) { "Third Driver Suitability Scores are not the expected" }
        // 4.5, 2.0, 2.0 - 4.5, 3.0, 4.5 - 4.5, 6.0, 4.0
    }
}