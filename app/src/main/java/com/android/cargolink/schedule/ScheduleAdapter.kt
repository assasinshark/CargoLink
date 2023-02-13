package com.android.cargolink.schedule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.android.cargolink.R
import com.android.cargolink.entities.Driver
import com.android.cargolink.entities.Shipment

//I will use high order methods for on click lister for now, I
// could use callbacks or listeners but for the moment and
// simplicity will use high order functions
class ScheduleAdapter(private var dataSet: List<Pair<Driver, Shipment>>, val onClick: (Shipment) -> Unit): RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {

    /**
     * For the moment I will only add posibility of changing all the data, I know
     * there's the possibility of only updating some part of the information,
     * but as the repo also only loads all the info at once, there's not much sense
     * of having here a more controlled changes administration. At least for now.
     */
    fun updateDataSet(newData: List<Pair<Driver, Shipment>>) {
        dataSet = newData
        notifyDataSetChanged()
    }

    class ScheduleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private var itemLabel: AppCompatTextView

        init {
            itemLabel = itemView.findViewById(R.id.schedule_item_text)
        }

        public fun setItemText(str: String) {
            itemLabel.text = str
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.schedule_item, parent, false)
        return ScheduleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        holder.setItemText(dataSet[position].first.fullName)
        holder.itemView.setOnClickListener(View.OnClickListener {
            onClick(dataSet[position].second)
        })
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}