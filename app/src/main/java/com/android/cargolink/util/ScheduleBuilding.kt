package com.android.cargolink.util

import com.android.cargolink.entities.Driver
import com.android.cargolink.entities.Shipment

interface ScheduleBuilding {
    suspend fun buildSchedule(drivers: List<Driver>,
                              shipments: List<Shipment>):
            List<Pair<Driver,Shipment>>

}