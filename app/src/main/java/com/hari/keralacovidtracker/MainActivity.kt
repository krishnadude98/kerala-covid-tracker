package com.hari.keralacovidtracker


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.leo.simplearcloader.SimpleArcLoader
import kotlinx.android.synthetic.main.popup.*
import kotlinx.android.synthetic.main.popup.view.*
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Math.acos
import kotlin.math.cos
import kotlin.math.sin

class MainActivity : AppCompatActivity() {
    lateinit var tvTotal:TextView
    private var locationManager : LocationManager? = null
    lateinit var tvActive:TextView
    lateinit var tvRecovered:TextView
    lateinit var tvDead:TextView
    lateinit var tvtTotal:TextView
    lateinit var tvtActive:TextView
    lateinit var tvtRecovered:TextView
    lateinit var tvtDead:TextView
    lateinit var simpleArcLoader: SimpleArcLoader
    lateinit var pieChart: PieChart
    lateinit var scrollview:ScrollView

    lateinit var tvKerala:TextView
    lateinit var avKerala:TextView
    lateinit var reKerala:TextView
    lateinit var deKerala:TextView
    lateinit var Arrow:ImageView
    lateinit var tvi:TextView
    lateinit var ai:TextView
    lateinit var ri:TextView
    lateinit var di:TextView
    lateinit var arrowtop:ImageView
    private var permissionaccepted=0
    private val file:String="locations"
    private val DEFAULT=1








    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        tvTotal= findViewById(R.id.tvTotal)
        tvActive= findViewById(R.id.tvActive)
        tvRecovered= findViewById(R.id.tvRecovered)
        tvDead= findViewById(R.id.tvDead)
        tvtTotal= findViewById(R.id.tvtTotal)
        tvtActive= findViewById(R.id.tvtActive)
        tvtRecovered= findViewById(R.id.tvtRecovered)
        tvtDead= findViewById(R.id.tvtDeaths)
        simpleArcLoader=findViewById(R.id.loader)
        pieChart=findViewById(R.id.piechart)
        scrollview=findViewById(R.id.ScrollStats)
        tvKerala= findViewById(R.id.tvKerala)
        avKerala=findViewById(R.id.avKerala)
        deKerala=findViewById(R.id.dvKerala)
        reKerala= findViewById(R.id.reKerala)
        Arrow= findViewById(R.id.ActiveImageView)
        tvi= findViewById(R.id.tvitotal)
        ai= findViewById(R.id.avitotal)
        ri= findViewById(R.id.tvirecovered)
        di= findViewById(R.id.tvidead)
        arrowtop= findViewById(R.id.iarrow)




        getData()

        if (ContextCompat.checkSelfPermission(this@MainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION) !==
            PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this@MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_BACKGROUND_LOCATION), 1)
            } else {
                ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_BACKGROUND_LOCATION), 1)
            }



        }else {

                getLocation()



        }





    }
    fun PlacesBtnClicked(view:View){
        val intent= Intent(this,LocActivity::class.java)
        startActivity(intent)

    }
    fun freindsBtnClicked(view:View){
        val intent= Intent(this,FriendsActivity::class.java)
        startActivity(intent)
    }
    fun vehiclesBtnClicked(view:View){
        val intent= Intent(this,VehiclesActivity::class.java)
        startActivity(intent)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                    if ((ContextCompat.checkSelfPermission(this@MainActivity,
                            Manifest.permission.ACCESS_FINE_LOCATION) ===
                                PackageManager.PERMISSION_GRANTED&&ContextCompat.checkSelfPermission(this@MainActivity,
                            Manifest.permission.ACCESS_COARSE_LOCATION) ===
                                PackageManager.PERMISSION_GRANTED)&&ContextCompat.checkSelfPermission(this@MainActivity,
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION)==PackageManager.PERMISSION_GRANTED) {
                                permissionaccepted=1







                    }
                } else {
                    Toast.makeText(this,"WE NEED YOUR LOCATION ",Toast.LENGTH_SHORT).show()

                }
                return
            }
        }
    }
    @SuppressLint("MissingPermission")
    fun getLocation() {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?
        if (locationManager != null) {
            Log.d("IN LOCATION MANAGER","NOT NULL")
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0L,0f,locationListener)
        }
        else{
            Log.d("OUT LOCATION MANAGER","NULL")
        }


    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            Environment.getRootDirectory()
            val latitude= location.latitude.toString()
            val longitude= location.longitude.toString()


            val spreference:SharedPreferences= this@MainActivity.getSharedPreferences("counter", Context.MODE_PRIVATE)
            val count= spreference.getInt("count",DEFAULT)
            //Code to get previous location
            val prevSharedpreference:SharedPreferences= this@MainActivity.getSharedPreferences("PrevLocData", Context.MODE_PRIVATE)
            val Prevlatitude=prevSharedpreference.getInt("latitude",0)
            val Prevlongitude=prevSharedpreference.getInt("longitude",0)
            //Code to sget 2nd counter
            //counter 2 is for friend
            val counter2:SharedPreferences= getSharedPreferences("counter2", Context.MODE_PRIVATE)
            val count2= counter2.getInt("count",DEFAULT)
            //code to get 3 rd counter
            //counter 3 is for vehicle
            val counter3:SharedPreferences= getSharedPreferences("counter3", Context.MODE_PRIVATE)
            val count3= counter3.getInt("count",DEFAULT)


            val sharedprefences:SharedPreferences= getSharedPreferences("locdata", Context.MODE_PRIVATE)

            val editor:SharedPreferences.Editor=sharedprefences.edit()
            editor.putString("locLat$count",latitude)
            editor.putString("locLong$count",longitude)
            editor.commit()
            Log.d("SAVED",latitude+longitude)
            val distance= getKilometeres(latitude.toDouble(),longitude.toDouble(),Prevlatitude.toDouble(),Prevlongitude.toDouble())*1000
            if(distance>10){
                val mDialogView=LayoutInflater.from(this@MainActivity).inflate(R.layout.popup,null)
                val mBuilder= AlertDialog.Builder(this@MainActivity).setView(mDialogView).setTitle("Going Out??")
                val mAlertDialog=mBuilder.show()
                mDialogView.OkClicked.setOnClickListener {
                    val sharedPreference1:SharedPreferences=getSharedPreferences("friendD", Context.MODE_PRIVATE)
                    val editor1= sharedPreference1.edit()
                    val sharedPreference2:SharedPreferences=getSharedPreferences("vehicleD", Context.MODE_PRIVATE)
                    val editor2= sharedPreference2.edit()

                    if(Efriend.text.isNotEmpty()&&Evehicle.text.isNotEmpty()){
                        editor1.putString("friend$count2",Efriend.text.toString())
                        addCount2(count2)
                        editor1.commit()
                        editor2.putString("vehicle$count3",Evehicle.text.toString())
                        addCount3(count3)
                        editor2.commit()
                        mAlertDialog.dismiss()

                    }else if(Efriend.text.isNotEmpty()){
                        editor1.putString("friend$count2",Efriend.text.toString())
                        addCount2(count2)
                        editor1.commit()
                        mAlertDialog.dismiss()
                    }else{
                        editor2.putString("vehicle$count3",Evehicle.text.toString())
                        addCount3(count3)
                        editor2.commit()
                        mAlertDialog.dismiss()
                    }

                }
                mDialogView.cancelClicked.setOnClickListener {
                    mAlertDialog.dismiss()
                }
            }
            addCount(count)
            loadNewCoordinates(latitude,longitude)







        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {
        }
    }
    private fun getKilometeres(lat:Double,long:Double,prevLat:Double,prevLong:Double):Double{
        if(prevLat==0.0&&prevLong==0.0){
            return 0.0
        }
        else{
            val PI_RAD:Double=Math.PI/180.0
            val ph1:Double= lat*PI_RAD
            val ph2:Double= prevLat*PI_RAD
            val lam1:Double=long*PI_RAD
            val lam2:Double=prevLong*PI_RAD
            return 6371.01* acos(sin(ph1)* sin(ph2)+ cos(ph1)* cos(ph2)*cos(lam2-lam1))

        }

    }
    private fun loadNewCoordinates(lat:String,long:String){
        Environment.getRootDirectory()
        val sharedprefences:SharedPreferences= getSharedPreferences("PrevLocData", Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor=sharedprefences.edit()
        editor.putString("latitude",lat)
        editor.putString("latitude",long)
    }




    private fun addCount(count:Int){
        Environment.getRootDirectory()
        val sharedprefences:SharedPreferences= getSharedPreferences("counter", Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor=sharedprefences.edit()
        val newCount=count+1
        editor.putInt("count",newCount)

        editor.commit()
        Log.d("COUNT VALUE",newCount.toString())
    }
    private fun addCount2(count:Int){
        Environment.getRootDirectory()
        val sharedprefences:SharedPreferences= getSharedPreferences("counter2", Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor=sharedprefences.edit()
        val newCount=count+1
        editor.putInt("count",newCount)

        editor.commit()

    }
    private fun addCount3(count:Int){
        Environment.getRootDirectory()
        val sharedprefences:SharedPreferences= getSharedPreferences("counter3", Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor=sharedprefences.edit()
        val newCount=count+1
        editor.putInt("count",newCount)

        editor.commit()

    }








    fun getData(){


        val urlIn= "https://api.covidindiatracker.com/total.json"
        val urlKr="https://api.covidindiatracker.com/state_data.json"
        simpleArcLoader.start()
        var fadein= AnimationUtils.loadAnimation(this,R.anim.fade_in)
        val request= object:StringRequest(Request.Method.GET,urlIn,Response.Listener { res->
            try {
                val jsonob:JSONObject= JSONObject(res.toString())
                tvtTotal.setText(jsonob.getString("confirmed"))
                tvtActive.setText(jsonob.getString("active"))
                tvtRecovered.setText(jsonob.getString("recovered"))
                tvtDead.setText(jsonob.getString("deaths"))
                tvi.setText(jsonob.getString("cChanges"))
                ai.setText(jsonob.getString("aChanges"))
                ri.setText(jsonob.getString("rChanges"))
                di.setText(jsonob.getString("dChanges"))
                val ck= ai.toString().toInt()
                if(ck<0) {
                    val rsid= this.resources.getIdentifier("postivedown","drawable",this.packageName)
                    arrowtop.setImageResource(rsid)

                }







            }catch (e:Exception){

                Toast.makeText(this,"Check Connection",Toast.LENGTH_LONG)
                simpleArcLoader.visibility= View.GONE
            }




        },Response.ErrorListener { err->
            Toast.makeText(this,err.toString(),Toast.LENGTH_SHORT).show()
        }){}
        val request2= object:StringRequest(Request.Method.GET,urlKr,Response.Listener { res->
            try {
                val jsonob:JSONArray= JSONArray(res)
                var i=18

                        lateinit var tkerala:String
                        lateinit var akerala:String
                        lateinit var rkerala:String
                        lateinit var dkerala:String

                        var jsob:JSONObject=jsonob.getJSONObject(i)
                        tvTotal.setText(jsob.getString("confirmed"))
                        tvActive.setText(jsob.getString("active"))
                        tvRecovered.setText(jsob.getString("recovered"))
                        tvDead.setText(jsob.getString("deaths"))
                        tvKerala.setText(jsob.getString("cChanges"))
                        avKerala.setText(jsob.getString("aChanges"))
                        reKerala.setText(jsob.getString("rChanges"))
                        deKerala.setText(jsob.getString("dChanges"))












                        val tes=(tvActive.text).toString().toInt()
                        if(tes<0){
                            val resid=this.resources.getIdentifier("postivedown","drawable",this.packageName)
                            Arrow.setImageResource(resid)
                        }
                        val tvT=Integer.parseInt(tvTotal.text.toString())
                        val tvA=Integer.parseInt(tvActive.text.toString())
                        val tvR=Integer.parseInt(tvRecovered.text.toString())
                        val tvD=Integer.parseInt(tvDead.text.toString())



                        pieChart.addPieSlice(PieModel("Total Cases",tvT.toFloat(),Color.parseColor("#FC6A03")))
                        pieChart.addPieSlice(PieModel("Recovered",tvR.toFloat(),Color.parseColor("#00FF00")))
                        pieChart.addPieSlice(PieModel("Active",tvA.toFloat(),Color.parseColor("#FF0000")))
                        pieChart.addPieSlice(PieModel("Death",tvD.toFloat(),Color.parseColor("#E6E600")))
                        pieChart.startAnimation()





                var s1:ImageView= findViewById(R.id.tc_image)
                var s2:ImageView= findViewById(R.id.ac_image)
                var s3:ImageView= findViewById(R.id.rImage)
                var s4:ImageView= findViewById(R.id.dImage)
                s1.startAnimation(fadein)
                s2.startAnimation(fadein)
                s3.startAnimation(fadein)
                s4.startAnimation(fadein)

                simpleArcLoader.stop()
                simpleArcLoader.visibility= View.GONE
                scrollview.visibility= View.VISIBLE




            }catch (e:Exception){
                Toast.makeText(this,"Please Check Your Connection",Toast.LENGTH_LONG).show()
                simpleArcLoader.stop()
                simpleArcLoader.visibility= View.GONE
                scrollview.visibility= View.VISIBLE
            }

        },Response.ErrorListener { err->
            Toast.makeText(this,err.toString(),Toast.LENGTH_LONG).show()
        }){}




        val RequestQueue:RequestQueue= Volley.newRequestQueue(this)
        RequestQueue.add(request)
        RequestQueue.add(request2)

    }
}