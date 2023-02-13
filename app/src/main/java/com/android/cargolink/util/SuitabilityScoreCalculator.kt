package com.android.cargolink.util

import com.android.cargolink.entities.Driver
import com.android.cargolink.entities.Shipment

interface SuitabilityScoreCalculator {
    fun suitabilityScoreMatrix(drivers: List<Driver>,
                                    shipments: List<Shipment>)
            : Array<Array<Double>>

    fun suitabilityScore(driver: Driver, shipment: Shipment): Double
}