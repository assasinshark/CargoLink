package com.android.cargolink.util

import com.android.cargolink.entities.Driver
import com.android.cargolink.entities.Shipment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Inject

/**
 * I was thinking on using companion object and provide it's instance to hilt for the
 * use in the repository as we don't have any internal properties but for growth
 * possibilities, will leave this as an actual class and object
 */
class ExDeepSearchScheduleBuilding @Inject constructor(val suitabilityScoreCalc: SuitabilityScoreCalculator): ScheduleBuilding {
    /**
     * Rule for matching drivers and shipments:
     * ****Deleted the rules as I am not sure I can share them directly in text ****
     *
     * Assumptions:
     * - By "destination street name" I understand a name without house
     * number or apt information ex. "63187 Volkman Garden Suite 447" would be converted
     * to only "Volkman Garden"
     * - By Driver's name we could do 2 things, use full name or just take the
     * first name. For this exercise I will use only the first name from the
     * provided data for the drivers assignment calculations.
     *
     * More explanation about the decision of the way of implementing the algorithm is
     * available at the end of the file.
     */

    /**
     * Method to apply the scheduling algorithm to the list of drivers and packages.
     * Assumptions:
     * There's the same number or drivers and shipments.
     *
     * It's a suspend function as it may process a considerable amount
     * of information and it's a continuation of loading info from file.
     * @param drivers List of drivers available.
     * @param shipments List of shipments available to be matched with drivers.
     * @return List of pairs between drivers and shipments on a 1 to 1 basis as
     * required and relation is defined by the algorithm. This could be instead
     * a map with the driver as key and shipment as value but for now it doesn't
     * add much value to the needs of the app but it adds more memory and
     * unneeded structure.
     */
    override suspend fun buildSchedule(drivers: List<Driver>,
                                       shipments: List<Shipment>):
            List<Pair<Driver, Shipment>> {
        val suitabilityMatrix = suitabilityScoreCalc.suitabilityScoreMatrix(drivers, shipments)
        val suitabilityGuide = findMaxSuitabilityScore(suitabilityMatrix)
        //As the assumption is that every driver has a shipment, I can just map them one for each
        val res: MutableList<Pair<Driver, Shipment>> = mutableListOf()
        for(index in drivers.indices) {
            res.add(Pair(
                drivers[index],
                shipments[suitabilityGuide.second.elementAt(index)]
            ))
        }
        return res
    }

    private suspend fun findMaxSuitabilityScore(scoresMatrix: Array<Array<Double>>): Pair<Double, Set<Int>> {
        //Branch to all possible shipments.
        var maxRes: Pair<Double, Set<Int>> = Pair(0.0, linkedSetOf())
        if(scoresMatrix.isEmpty())
            return maxRes

        var branchRes: Pair<Double, Set<Int>>?
        for (shipInd in 0 until scoresMatrix[0].size) {
            //This shipment hasn't been used in this branch, explore.
            branchRes = findMaxSuitabilityScore(0,
                shipInd,
                scoresMatrix,
                linkedSetOf(),
                0.0) //To mutable set is used to copy the set
            branchRes.let {
                if(branchRes.first > maxRes.first)
                    maxRes = branchRes

            }
        }
        return maxRes
    }

    /**
     * Utility method to create the suitability matrix between the packages and drivers.
     * @param driverIndex The driver index we are currently evaluating. (From matrix)
     * @param shipmentIndex The shipment index we are currently evaluating. (From Matrix)
     * @param scoresMatrix matrix containing the suitability score of the driver/shipment relation
     * @param shipmentsUsed set containing the shipments already used for this branch evaluation
     * @param score score until the current step.
     * @return a pair of information, the pair contains a double representing the max
     * suitability score found in that branch and a set containing the shipments used
     * for this branch so we can recreate the obtained score.
     *
     * Runs the matrix stepping one driver at a time and branching to all yet unused shipments for
     * the next driver. - More info in the end of the file.
     */
    private suspend fun findMaxSuitabilityScore(driverIndex: Int,
                                                shipmentIndex: Int,
                                                scoresMatrix: Array<Array<Double>>,
                                                shipmentsUsed: MutableSet<Int>,
                                                score:Double): Pair<Double, Set<Int>> {
        val currentScore = score + scoresMatrix[driverIndex][shipmentIndex]
        shipmentsUsed.add(shipmentIndex)
        var result = Pair<Double, Set<Int>>(currentScore, shipmentsUsed)
        if(driverIndex == scoresMatrix.size - 1) {
            //We reached last level, we must return the score adding this last step score.
            return result
        }
        //Branch to all other possible shipment.
        var branchRes: Pair<Double, Set<Int>>?
        for (shipInd in 0 until scoresMatrix[driverIndex].size ) {
            if(!shipmentsUsed.contains(shipInd)) {
                //This shipment hasn't been used in this branch, explore.
                branchRes = findMaxSuitabilityScore(driverIndex+1,
                    shipInd,
                    scoresMatrix,
                    shipmentsUsed.toMutableSet(),
                    currentScore) //To mutable set is used to copy the set
                branchRes.let {
                    if(branchRes.first > result.first)
                        result = branchRes

                }
            }
        }
        return result
    }
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class ScheduleBuildingModule {
    @Binds
    abstract fun bindScheduleBuilding(
        scheduleBuildingImp: ExDeepSearchScheduleBuilding
    ): ScheduleBuilding
}

/**
 * Train of thoughts regarding the implementation of the algorithm:
 *
 * The first solution that comes to mind is deep search algorithm but I wanted to find
 * something more optimal.
 *
 * I first thought about checking NP kind of problems as it seemed
 * similar to a travelling salesman variant problem  or even a knapsack
 * variant but after not being able to relate it with graph kinds, I then checked
 * subset problem, specially multiple subset and fair subset problem.
 *
 * I couldn't actually use an algorithm for these kind of problems on the exercise even though
 * I really though they were very similar. After giving up on those kind of problems I went back
 * to Deep Search algorithm knowing that it would exponentially increase processing time but
 * at least would give a functional solution. So that's the implemented answer a Deep search
 * algorithm that branches between the possible shipments to find the max suitability score
 * with just one little improvement which is using some extra memory to have a record of the
 * used shipments in each branch to avoid extra branches. This saves up some time with the
 * disadvantage of using some more memory.
 *
 */
