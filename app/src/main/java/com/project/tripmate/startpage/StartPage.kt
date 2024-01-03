package com.project.tripmate.startpage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.project.tripmate.R
import com.project.tripmate.mainapp.CommonFunctions
import com.project.tripmate.mainapp.fragments.triprecord.TripDataInterface
import com.project.tripmate.mainapp.fragments.triprecord.TripDataX
import com.project.tripmate.mainapp.fragments.triprecord.TripRecord
import com.project.tripmate.signing.SignInPage
import com.project.tripmate.signing.SignUpPage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StartPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_page)

        val signInButton = findViewById<Button>(R.id.signInOptionButton)
        signInButton.setOnClickListener{
            startActivity(Intent(this, SignInPage::class.java))
        }

        val signUpButton = findViewById<Button>(R.id.registerOptionButton)
        signUpButton.setOnClickListener{
            startActivity(Intent(this, SignUpPage::class.java))
        }

    }
}