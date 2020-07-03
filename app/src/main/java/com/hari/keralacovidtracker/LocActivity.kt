package com.hari.keralacovidtracker

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.ListView
import com.hari.keralacovidtracker.Adapter.myAdapter
import com.hari.keralacovidtracker.Models.myLocation
import kotlinx.android.synthetic.main.activity_loc.*

class LocActivity : AppCompatActivity() {
    val DEFAULT=1

    lateinit var adapter:myAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loc)



        val spreference: SharedPreferences = getSharedPreferences("counter", Context.MODE_PRIVATE)
        val count= spreference.getInt("count",DEFAULT)
        val sference: SharedPreferences = getSharedPreferences("locdata", Context.MODE_PRIVATE)
        val locarray:MutableList<myLocation>
        val l1= sference.getString("locLat1", "")
        val l2= sference.getString("locLong1", "")
        val ob=object:myLocation(l1,l2){}
        locarray= MutableList(count){ob}



        for(i in 2..count) {


            val lat = sference.getString("locLat$i", "")
            val long = sference.getString("locLong$i", "")
            val loc= object :myLocation(lat.toString(),long.toString()){}
            locarray.add(loc)


        }
        adapter= myAdapter(this,locarray)
        listView.adapter=adapter














    }
}