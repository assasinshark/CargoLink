package com.android.cargolink.data

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.cargolink.entities.Driver
import com.android.cargolink.entities.Shipment
import com.android.cargolink.util.ScheduleBuilding
import org.json.JSONObject
import java.lang.StringBuilder
import javax.inject.Inject


/**
 * For the moment I won't create functionality to update already loaded data, like
 * if the user selects another file to merge the data from both files.
 */
class ShipmentRepository @Inject constructor(val scheduleBuilder: ScheduleBuilding, private val fileSourceReader: DataFileSourceReader) {

    //Could have a default file url, but will leave the user to load all the info.
    private var schedule: MutableLiveData<List<Pair<Driver, Shipment>>> = MutableLiveData(emptyList())
    private var drivers: MutableLiveData<List<Driver>> = MutableLiveData(emptyList())
    //Unused for now private var shipment: MutableLiveData<List<Shipment>> = MutableLiveData(emptyList())

    //Arrays for preprocessing of json data. might be deleted later and
    // just use parameters in functions. Not used for now
    //private var drivers: MutableList<Driver> = mutableListOf()
    //private var shipments: MutableList<Shipment> = mutableListOf()

    //TODO: Add return value or exception to inform that data hasn't been loaded in
    // case there's a problem with file or number of items
    suspend fun loadDataFromFile(filePath: Uri?, context: Context) {
        val data = fileSourceReader.loadDataFromFile(filePath, context)
        if(data.first.size == data.second.size)
            schedule.postValue(scheduleBuilder.buildSchedule(data.first, data.second))
    }

    fun getSchedule(): LiveData<List<Pair<Driver,Shipment>>> {
        return schedule
    }
}