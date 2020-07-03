package com.hari.keralacovidtracker

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import com.hari.keralacovidtracker.Adapter.FriendAdapter
import com.hari.keralacovidtracker.Adapter.VehicleAdapter
import kotlinx.android.synthetic.main.activity_friends.*
import kotlinx.android.synthetic.main.activity_vehicles.*

class VehiclesActivity : AppCompatActivity() {
    lateinit var adapter: VehicleAdapter


    val DEFAULT=1
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicles)
        val spreference: SharedPreferences = getSharedPreferences("counter3", Context.MODE_PRIVATE)
        val count= spreference.getInt("count",DEFAULT)
        val sference: SharedPreferences = getSharedPreferences("vehicleD", Context.MODE_PRIVATE)
        val f1=sference.getString("vehicle1","")
        val vehiclearray= MutableList(count){f1}
        for(i in 2..count){
            val friend= sference.getString("vehicle$i","")
            vehiclearray.add(friend)
        }
        adapter= VehicleAdapter(this,vehiclearray)
        VehicleView.adapter=adapter
    }
}