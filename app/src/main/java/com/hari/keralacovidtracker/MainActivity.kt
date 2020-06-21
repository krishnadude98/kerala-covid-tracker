package com.hari.keralacovidtracker

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.hari.keralacovidtracker.Adapter.myAdapter
import com.hari.keralacovidtracker.Models.Place
import com.leo.simplearcloader.SimpleArcLoader
import kotlinx.android.synthetic.main.activity_main.*
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel
import org.intellij.lang.annotations.Identifier
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    lateinit var tvTotal:TextView
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
    lateinit var mAdView : AdView
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
    lateinit var adapter:myAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MobileAds.initialize(this) {}
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
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

    }
    fun getData(){
        lateinit var PlaceModelList:ArrayList<Place>
        PlaceModelList= ArrayList<Place>()
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
                        val district:JSONArray=jsob.getJSONArray("districtData")
                        for(i in 0..district.length()-1){
                            var sdist:JSONObject=district.getJSONObject(i)
                            var name:String= sdist.getString("id")
                            var conformed:String=sdist.getString("confirmed")
                            var recovered:String=sdist.getString("recovered")
                            var deaths:String=sdist.getString("deaths")
                            var zone:String=sdist.getString("zone")
                            var place:Place= Place(name,conformed,recovered,deaths,zone)

                            PlaceModelList.add(place)

                        }
                adapter= myAdapter(this,PlaceModelList)
                myList.adapter=adapter








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