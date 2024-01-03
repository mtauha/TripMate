package com.project.tripmate.mainapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CityNameInterface {
    @GET("APIs/Pages/location_dropdown.php/details")
    fun getCityNames(@Query("city_name") cityName: String, @Query("limit") limit: Int): Call<CitiesList>
}