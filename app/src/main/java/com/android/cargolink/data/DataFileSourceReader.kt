package com.android.cargolink.data

import android.content.Context
import android.net.Uri
import com.android.cargolink.entities.Driver
import com.android.cargolink.entities.Shipment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

//Will create only one json source reader
// but would've liked to have shipments and
// drivers in diff files
//We could also later separate the Entities from here by using generics
interface DataFileSourceReader {
    val SHIPMENTS_ARRAY_KEY: String
        get() = "shipments"
    val DRIVERS_ARRAY_KEY: String
        get() = "drivers"

    suspend fun loadDataFromFile(filePath: Uri?, context: Context): Pair<List<Driver>,List<Shipment>>
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataFileSourceReaderModule {
    @Binds
    abstract fun bindDataFileSourceReader(
        dataFileSourceReaderImp: JsonDataFileSourceReader
    ): DataFileSourceReader
}