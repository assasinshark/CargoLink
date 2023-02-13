package com.android.cargolink.util

import com.android.cargolink.entities.Driver
import com.android.cargolink.entities.Shipment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Inject
import kotlin.math.min

class ExerciseSuitabilityScoreCalculator @Inject constructor() : SuitabilityScoreCalculator {
    /**
     * Utility method to create the suitability matrix between the packages and drivers.
     * @param drivers List of drivers available.
     * @param shipments List of shipments available to be matched with drivers.
     * @return 2D matrix containing the suitability score of every driver matched with
     * every shipment. [drivers][shipments]
     * The matrix is not completely required to implement the algorithm (I could do the
     * calculations during the algorithm implementation) but helps us to save
     * some time on repeated calculations during the creation of the schedule
     */
    override fun suitabilityScoreMatrix(drivers: List<Driver>,
                                    shipments: List<Shipment>)
            : Array<Array<Double>> {
        var scoreMatrix: Array<Array<Double>> = Array(drivers.size) { Array(shipments.size) { 0.0 } }
        for(driverInd in drivers.indices) {
            for(shipmntInd in shipments.indices) {
                scoreMatrix[driverInd][shipmntInd] = suitabilityScore(drivers[driverInd], shipments[shipmntInd])
            }
        }
        return scoreMatrix
    }

    override fun suitabilityScore(driver: Driver, shipment: Shipment): Double {

        var score = if(shipment.streetName.length % 2 == 0 ) {
            //Even
            driver.firstName.vowels() * 1.5
        } else {
            //Odd
            driver.firstName.consonants().toDouble()
        }
        score *= if (commonFactors(shipment.streetName.length, driver.firstName.length)) 1.5 else 1.0
        return score
    }

    //I could store factors for drivers and shipments to avoid recalculation but
    // doesn't seem appropiate or that cost justify the extra memory and work.
    private fun commonFactors(val1: Int, val2: Int): Boolean {
        val min = min(val1, val2)
        val max = if (min == val1) val2 else val1
        var ind = 2
        while (ind <= min) {
            if(min % ind == 0 && max % ind == 0)
                return true
            ind++
        }
        return false
    }
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class SuitabilityScoreCalculatorModule {
    @Binds
    abstract fun bindSuitabilityScoreCalculator(
        suitabilityScoreCalculatorImp: ExerciseSuitabilityScoreCalculator
    ): SuitabilityScoreCalculator
}

/**
 * Assumptions:
 * - The driver names will only contain characters from the 27 letters in the
 * English dictionary, either lowercase or uppercase
 * - Continuing with previous, the drivers names will not contain special characters
 * like: ', -, _, ,, or any other
 * - The vowels are defined as a, e, i, o, u. Even though sometimes 'y' is sometimes
 * taken as vowel, we will ignore it as vowel for the moment.
 * So the methods to come are only prepared to handle letters and spaces.
 * */
private val VOWELS = setOf<Char>('a','e','i','o','u','A','E','I','O','U') //To avoid converting to lowercase
fun String.vowels(): Int {
    var res = 0
    for(letter: Char in this.toCharArray()) {
        if(VOWELS.contains(letter))
            res += 1
    }
    return res
}
fun String.consonants(): Int {
    val temp = this.filterNot { it.isWhitespace() }
    return temp.length - vowels()
}