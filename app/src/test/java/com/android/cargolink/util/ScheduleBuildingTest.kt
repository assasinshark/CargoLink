package com.android.cargolink.util

import com.android.cargolink.entities.Driver
import com.android.cargolink.entities.Shipment
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ScheduleBuildingTest {

    /**
     * Some of these test's are only available if we change the required methods to public
     * I know this requirement is sometimes not well taken but I prefer to create the tests
     * and later change the access to the methods to private and comment the test's as a
     * possibility of debugging and testing for future changes in a real life scenario.    * */

    /**
     * The other option is to change the functions to be protected and create a class
     * that inherits from ExDeepSearchScheduleBuilding class to be able to create bridge
     * functions that would allow me to test them but for the moment I will not apply this
     * as it's also not very well seen this kind of solutions and would depend on the
     * project requirements and team best practices.
     * */
    /*
    //Expected result score: 21.0 and set: 4,1,0,2,3
    @Test
    fun testFindMaxSuitabilityScore5_5() = runTest {
        val testMatrix = arrayOf<Array<Double>>(
            arrayOf(5.0,2.0,5.0,4.0,5.0),
            arrayOf(4.0,1.0,3.0,3.0,2.0),
            arrayOf(6.0,2.0,2.0,4.0,5.0),
            arrayOf(4.0,1.0,4.0,2.0,1.0),
            arrayOf(3.0,2.0,1.0,5.0,1.0)
        )
        val result = ExDeepSearchScheduleBuilding(ExerciseSuitabilityScoreCalculator()).findMaxSuitabilityScore(testMatrix)
        assert(result.first== 21.0) { "Not the expected score" }
        assert(result.second.size == 5)
        val resSet = result.second
        assert(resSet.elementAt(0) == 4)
        assert(resSet.elementAt(1) == 1)
        assert(resSet.elementAt(2) == 0)
        assert(resSet.elementAt(3) == 2)
        assert(resSet.elementAt(4) == 3)
    }

    @Test
    fun testFindMaxSuitabilityScore3_3() = runTest {
        val testMatrix = arrayOf<Array<Double>>(
            arrayOf(5.0,4.0,5.0),
            arrayOf(3.0,3.0,2.0),
            arrayOf(2.0,4.0,5.0),
        )
        val result = ExDeepSearchScheduleBuilding(ExerciseSuitabilityScoreCalculator()).findMaxSuitabilityScore(testMatrix)
        assert(result.first== 13.0) { "Not the expected score" }
        assert(result.second.size == 3)
        val resSet = result.second
        assert(resSet.elementAt(0) == 0)
        assert(resSet.elementAt(1) == 1)
        assert(resSet.elementAt(2) == 2)
    }

    @Test
    fun testFindMaxSuitabilityScoreEmpty() = runTest {
        val testMatrix = arrayOf<Array<Double>>()
        val result = ExDeepSearchScheduleBuilding(ExerciseSuitabilityScoreCalculator()).findMaxSuitabilityScore(testMatrix)
        assert(result.first== 0.0) { "Not the expected score" }
        assert(result.second.isEmpty())
    }

     */

    @Test
    fun testBuildSchedule() = runTest {
        val drivers = listOf<Driver>(Driver("Enda Krammer"),Driver("Oscar Carter"), Driver("Michael Post"))
        val shipments = listOf<Shipment>(Shipment("1234 Address St Apt. 64"), Shipment("1234 Address Apt. 64"), Shipment("746 Unknown Parkway Suite 35"))
        val res = ExDeepSearchScheduleBuilding(ExerciseSuitabilityScoreCalculator()).buildSchedule(drivers, shipments)
        assert(res[0].first == drivers[0] && res[0].second == shipments[0]) { "First Driver Shipment is not the expected" }
        assert(res[1].first == drivers[1] && res[1].second == shipments[2]) { "Second Driver Shipment is not the expected" }
        assert(res[2].first == drivers[2] && res[2].second == shipments[1]) { "Third Driver Shipment is not the expected" }
    }
}