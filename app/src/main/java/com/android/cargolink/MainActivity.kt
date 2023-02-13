package com.android.cargolink


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.android.cargolink.databinding.ActivityMainBinding
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.cargolink.entities.Shipment
import com.android.cargolink.schedule.ScheduleAdapter
import com.google.android.material.snackbar.Snackbar

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var scheduleAdapter: ScheduleAdapter

    private val shipmentVM: ShipmentViewModel by viewModels()

    //ActivityResultLaunchers
    private val openDocRequest = registerForActivityResult(ActivityResultContracts.OpenDocument()) { url: Uri? ->
        url?.let {
            //Open File
            binding.selFileBtn.text = url.path.toString()
            shipmentVM.loadFileData(url, applicationContext)
        }
    }
    /* As I am using a file provider I no longer need permission request for the API levels I am using
    private val permissionRequest = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted:Boolean ->
        if(isGranted) {
            openFileBrowser()
        } else {
            Toast.makeText(this, R.string.error_loading_file,Toast.LENGTH_LONG).show()
        }
    }
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.selFileBtn.setOnClickListener(View.OnClickListener {
            clickOnSelectFile()
        })

        val initialData = shipmentVM.getShipmentSchedule().value?: emptyList()
        scheduleAdapter = ScheduleAdapter(initialData, fun (shipment: Shipment): Unit {
            //I could use another activity, a dialog, a toas but I think this is a good way of showing the info, everything is an overkill
            val snack = Snackbar.make(binding.driversList, "Shipment Assigned: ${shipment.address}", Snackbar.LENGTH_INDEFINITE)
            snack.setAction(R.string.dismiss, View.OnClickListener {
                snack.dismiss()
            })
            snack.show()
        })
        binding.driversList.adapter = scheduleAdapter
        shipmentVM.getShipmentSchedule().observe(this) {
            scheduleAdapter.updateDataSet(it)
        }
        shipmentVM.loading.observe(this) {
            if(it) {
                binding.selFileBtn.isEnabled = false
                binding.loadIndicator.visibility = View.VISIBLE

            } else {
                binding.loadIndicator.visibility = View.GONE
                binding.selFileBtn.isEnabled = true
            }
        }
        binding.driversList.layoutManager = LinearLayoutManager(this)
    }

    private fun clickOnSelectFile() {
        //if(fileAccessPermissionGranted()) { No longer in use for the current implementation
            //Open intent to open file
            openFileBrowser()
        //} else {
            //Request permission
            //requestFileAccessPermission() //This would be good to have a version check as new
            // versions doesn't require permission in certain ways of opening files like the
            // one used, but will leave anyway
            //openFileBrowser()
        //}
    }

    private fun openFileBrowser() {
        openDocRequest.launch(arrayOf("application/json", "text/json"))
    }
    //Mas bien escucharemos un live data para habilitar un spinner y bloquear el boton
    private fun setLoadingState(loading: Boolean) {

    }
}