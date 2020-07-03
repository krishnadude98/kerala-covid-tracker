package com.hari.keralacovidtracker.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.hari.keralacovidtracker.Models.myLocation
import com.hari.keralacovidtracker.R

class myAdapter(context: Context,location:MutableList<myLocation>):BaseAdapter() {
    val context= context
    val location=location
    override fun getView(position: Int, convertview: View?, parent: ViewGroup?): View {
        val convertview:View?
        convertview= LayoutInflater.from(context).inflate(R.layout.model,null)
        val lat:TextView=convertview.findViewById(R.id.latText)
        val long:TextView= convertview.findViewById(R.id.longText)
        val currentloc= location[position]
        lat.text= currentloc.lat
        long.text= currentloc.long


        return convertview
    }

    override fun getItem(position: Int): Any {
        return location[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return location.count()
    }
}