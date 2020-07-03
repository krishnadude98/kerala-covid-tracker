package com.hari.keralacovidtracker.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.hari.keralacovidtracker.R

class FriendAdapter(context:Context, friend: MutableList<String?>):BaseAdapter(){
    val context=context
    val friend=friend
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val convertview:View?
        convertview= LayoutInflater.from(context).inflate(R.layout.friend_model,null)
        val text:TextView= convertview.findViewById(R.id.friendTxt)
        val f=friend[position]
        text.text=f

        return convertview
    }

    override fun getItem(position: Int): String? {
        return friend[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return friend.count()
    }

}