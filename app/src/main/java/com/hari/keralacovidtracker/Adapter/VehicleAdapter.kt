package com.hari.keralacovidtracker.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.hari.keralacovidtracker.R

class VehicleAdapter(context: Context, vehicles: MutableList<String?>): BaseAdapter() {
    val context=context
    val vehicles=vehicles
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val convertview:View?
        convertview= LayoutInflater.from(context).inflate(R.layout.vehicle_model,null)
        val text: TextView = convertview.findViewById(R.id.vehicleId)
        val v=vehicles[position]
        text.text=v

        return convertview
    }

    override fun getItem(position: Int): String? {
        return vehicles[position]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return vehicles.count()
    }
}