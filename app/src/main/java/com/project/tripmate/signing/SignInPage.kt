package com.project.tripmate.signing

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import com.project.tripmate.mainapp.MainAppPage
import com.project.tripmate.R
import com.project.tripmate.mainapp.CityListAdapter
import com.project.tripmate.mainapp.CommonFunctions
import com.project.tripmate.startpage.StartPage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.MessageDigest


class SignInPage : AppCompatActivity() {
    var userExists: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_page)

        val email = findViewById<EditText>(R.id.LogInEmailAddressField)
        val password = findViewById<EditText>(R.id.logInPasswordField)

        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener{
            getUserDetails(email.text.toString(), password.text.toString())
        }

        val signUpButton = findViewById<Button>(R.id.forgotPasswordButton)
        signUpButton.setOnClickListener{
            startActivity(Intent(this, ActivityForgotPasswordPage::class.java))
        }
    }

    private fun checkCredentials(email: String, password: String): Boolean {
        return true
    }



    private fun getUserDetails(email: String, password: String): Boolean{
        try {
            println(email)
            val retroFit = Retrofit.Builder()
                .baseUrl(CommonFunctions.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
                .build()
                .create(CredentialsInterface::class.java)


            retroFit.getUserDetails(email)
                .enqueue(object : Callback<SignInUserDetail> {
                    override fun onResponse(
                        call: Call<SignInUserDetail>,
                        response: Response<SignInUserDetail>
                    ) {
                        val result = response.body()
                        if (response.isSuccessful) {
                            if (result != null) {
                                if(result.data == null){
                                    Toast.makeText(this@SignInPage, "Email Not Registered", Toast.LENGTH_SHORT).show()
                                    userExists = false
                                    }
                                else{
                                    if(CommonFunctions.md5(password) == result.data.password){
                                        userExists = true
                                        Toast.makeText(this@SignInPage, "Welcome User: ${result.data.first_name} ${result.data.last_name}", Toast.LENGTH_SHORT).show()
                                        CurrentUser.userData = result.data
                                        startActivity(Intent(this@SignInPage, MainAppPage::class.java))
                                    }
                                    else{
                                        userExists = false
                                        Toast.makeText(this@SignInPage, "Wrong Password", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            } else {
                                Toast.makeText(this@SignInPage, "Response body is null", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this@SignInPage, "Unsuccessful response: ${response.code()}", Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onFailure(call: Call<SignInUserDetail>, t: Throwable) {
                        if (t is HttpException) {
                            val responseBody = t.response()?.errorBody()?.string()
                            println("Error Body: $responseBody")
                        }
                        println(t.message)
                        println(t.localizedMessage)
                        println(t.stackTrace)
                        Toast.makeText(this@SignInPage, "Failure: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
        } catch (e: Exception) {
            // Handle other exceptions
            Toast.makeText(this, "Exception: ${e.message}", Toast.LENGTH_SHORT).show()
        }
        return userExists
    }


}