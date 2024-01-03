package com.project.tripmate.signing

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CredentialsInterface {
    @GET("APIs/Pages/register.php/details")
    fun insertUserData(
        @Query("user_email") userEmail: String,
        @Query("password") password: String,
        @Query("first_name") firstName: String,
        @Query("last_name") lastName: String,
        @Query("city_name") city: String,
        @Query("date_of_birth") dob: String,
        @Query("phone_number") number: String
    ): Call<SignUpResult>

    @GET("APIs/Pages/profile.php/details")
    fun getUserDetails(
        @Query("user_email") userEmail: String
    ): Call<SignInUserDetail>

    @GET("APIs/Pages/edit_profile.php/details")
    fun changePassword(
        @Query("user_id") userId: String,
        @Query("password") password: String,
    ): Call<UpdationStatus>

    @GET("APIs/Pages/edit_profile.php/details")
    fun editUser(
        @Query("user_id") userId: String,
        @Query("first_name") firstName: String,
        @Query("last_name") lastName: String,
        @Query("user_email") userEmail: String,
        @Query("date_of_birth") dateOfBirth: String,
        @Query("phone_number") phoneNumber: String,
        @Query("city_name") cityName: String
    ): Call<UpdationStatus>
}
