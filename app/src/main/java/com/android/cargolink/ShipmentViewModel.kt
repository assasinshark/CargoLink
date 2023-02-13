package com.android.cargolink

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.cargolink.data.ShipmentRepository
import com.android.cargolink.entities.Driver
import com.android.cargolink.entities.Shipment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShipmentViewModel @Inject constructor(private val shipmentRepo: ShipmentRepository): ViewModel() {

    var loading: MutableLiveData<Boolean> = MutableLiveData(false)

    fun loadFileData(fileUri: Uri, appContext: Context) {
        //Use repo to load info from a user selected file.
        loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            shipmentRepo.loadDataFromFile(fileUri, appContext)
            loading.postValue(false)
        }
    }

    //I am making the repo live data visible but in other scenarios I would make a flow return
    // type and expose here a live data or have an intermediate object in view model to hide repo
    // Also the simplicity of data allows to only expose the live data directly
    fun getShipmentSchedule(): LiveData<List<Pair<Driver, Shipment>>> {
        return shipmentRepo.getSchedule()
    }
}