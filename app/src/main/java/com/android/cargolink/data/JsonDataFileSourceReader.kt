package com.android.cargolink.data

import android.content.Context
import android.net.Uri
import com.android.cargolink.entities.Driver
import com.android.cargolink.entities.Shipment
import org.json.JSONObject
import java.lang.StringBuilder
import javax.inject.Inject

//Will create only one json source reader
// but would've liked to have shipments and
// drivers in diff files
class JsonDataFileSourceReader @Inject constructor(): DataFileSourceReader {
    //val fileName = "data.json"
    /**
     *
     * Method to read the json formatted information from user selected file.
     * Assumptions: the file format is a valid json format based in text in UTF-8 charset
     *
     * It's a suspend function as it may process a considerable amount
     * of information from file.
     * @param filePath The Uri object to user selected file to read data from.
     * @return Pair object containing the list of drivers and the list of shipments
     * obtained from the file
     */
    override suspend fun loadDataFromFile(filePath: Uri?, context: Context): Pair<List<Driver>,List<Shipment>> {
        filePath?.let {
            val sBuilder = StringBuilder()
            context.contentResolver.openInputStream(filePath).use { reader ->
                reader?.bufferedReader()?.forEachLine { line ->
                    sBuilder.appendLine(line)
                }
            }
            //JSON has been read
            //JSON needs to be processed.
            return processJsonData(sBuilder.toString())
        }
        return Pair(emptyList(), emptyList())
    }

    /**
     *
     * Method to process the json obtained from user selected file.
     * Assumptions: the file format is a valid json format with one principal
     * object containing two string arrays called: "shipments" and "drivers"
     *
     * It's a suspend function as it may process a considerable amount
     * of information and it's a continuation of loading info from file.
     * @param jsonStr The json string that represent the contents of file.
     * @return Pair object containing the list of drivers and the list of shipments
     * obtained from the file
     */
    private suspend fun processJsonData(jsonStr: String): Pair<List<Driver>,List<Shipment>> {
        val jsonData = JSONObject(jsonStr)
        val shipmentsJson = jsonData.getJSONArray(SHIPMENTS_ARRAY_KEY)
        var shipments = mutableListOf<Shipment>()
        for(index in 0 until shipmentsJson.length()) {
            shipmentsJson.get(index)?.let { element ->
                shipments.add(Shipment(element as String))
            }
        }
        val driversJson = jsonData.getJSONArray(DRIVERS_ARRAY_KEY)
        var drivers = mutableListOf<Driver>()
        for(index in 0 until driversJson.length()) {
            driversJson.get(index)?.let { element ->
                drivers.add(Driver(element as String))
            }
        }
        return Pair(drivers, shipments)
    }
}