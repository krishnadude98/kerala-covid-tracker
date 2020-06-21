package com.hari.keralacovidtracker.Adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.hari.keralacovidtracker.Models.Place
import com.hari.keralacovidtracker.R

class myAdapter(context:Context,places:List<Place>):BaseAdapter() {
    val context= context
    val places= places
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val placeView:View

        placeView= LayoutInflater.from(context).inflate(R.layout.places,null)
        val id:TextView=placeView.findViewById(R.id.placeName)
        val tc:TextView=placeView.findViewById(R.id.placeCases)
        val pr:TextView= placeView.findViewById(R.id.placeRecovered)
        val pd:TextView= placeView.findViewById(R.id.placeDeaths)
        val z:TextView= placeView.findViewById(R.id.zone)
        val lo:LinearLayout=placeView.findViewById(R.id.viewPlace)
        val place= places[position]
        id.text= place.id
        tc.text=place.conformed
        if(place.Recovered=="null"){
            pr.text="Unavailable"
        }else{
            pr.text=place.Recovered
        }
        if(place.Deaths=="null"){
            pd.text="Unavailable"
        }else{
            pd.text=place.Deaths
        }


        z.text=place.zone


        if(z.text=="RED"){
            z.setTextColor(Color.RED)
        }
        else if (z.text=="ORANGE"){
            z.setTextColor(Color.parseColor("#ff8c00"))
        }
        else{
            z.setTextColor(Color.GREEN)
        }



        return placeView
    }

    override fun getItem(position: Int): Any {
        return places[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return places.count()
    }
}

