package com.hari.keralacovidtracker

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import com.hari.keralacovidtracker.Adapter.FriendAdapter
import kotlinx.android.synthetic.main.activity_friends.*

class FriendsActivity : AppCompatActivity() {
    lateinit var adapter: FriendAdapter

    val DEFAULT=1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)

        val spreference: SharedPreferences = getSharedPreferences("counter2", Context.MODE_PRIVATE)
        val count= spreference.getInt("count",DEFAULT)
        val sference: SharedPreferences = getSharedPreferences("friendD", Context.MODE_PRIVATE)
        val f1=sference.getString("friend1","")
        val friendarray= MutableList(count){f1}
        for(i in 2..count){
            val friend= sference.getString("friend$i","")
            friendarray.add(friend)
        }
        adapter= FriendAdapter(this,friendarray)
        FriendView.adapter=adapter

    }
}