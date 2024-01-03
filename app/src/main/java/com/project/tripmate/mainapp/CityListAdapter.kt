package com.project.tripmate.mainapp

import android.R
import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CityListAdapter {
    //private lateinit var adapter:ArrayAdapter<String>
    //private var list :ArrayList<String> = ArrayList()

//    public fun getAdapter(context: Context, string: String): ArrayAdapter<String> {
//        val list = get10Cities(context, string)
//        adapter = ArrayAdapter(
//            context,
//            R.layout.simple_list_item_1,
//            list
//        )
//        return adapter
//    }


    public fun get10Cities(context: Context, nameInitial: String) : ArrayList<String>{
        val cities: ArrayList<String> = ArrayList()
        try {
            val retroFit = Retrofit.Builder()
                .baseUrl(CommonFunctions.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CityNameInterface::class.java)

            retroFit.getCityNames("$nameInitial%", 10)
                .enqueue(object : Callback<CitiesList> {
                    override fun onResponse(
                        call: Call<CitiesList>,
                        response: Response<CitiesList>
                    ) {
                        if (response.isSuccessful) {
                            val cityData = response.body()?.locations
                            if (cityData != null) {
                                CityListObject.cityList = response.body()
                                val n = CityListObject.cityList?.locations?.size!!
                                for(i in 0..<n){
                                    val location = CityListObject.cityList?.locations
                                    cities.add(location?.get(i)?.city_name.toString())
                                    println(cities[i])
                                }
                            } else {
                                Toast.makeText(context, "Response body is null", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(context, "Unsuccessful response: ${response.code()}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<CitiesList>, t: Throwable) {
                        Toast.makeText(context, "Failure: ${t.message}", Toast.LENGTH_LONG).show()
                    }
                })
        } catch (e: Exception) {
            // Handle other exceptions
            Toast.makeText(context, "Exception: ${e.message}", Toast.LENGTH_SHORT).show()
        }
        return cities
    }
}