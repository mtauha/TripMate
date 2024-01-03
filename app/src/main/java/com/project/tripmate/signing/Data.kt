package com.project.tripmate.signing

data class Data(
    val city_name: String,
    val date_of_birth: String,
    val first_name: String,
    val last_name: String,
    var password: String,
    val phone_number: String,
    val profile_picture_id: String,
    val user_email: String,
    val user_id: String
)