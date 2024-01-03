package com.project.tripmate.signing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.gson.GsonBuilder
import com.project.tripmate.R
import com.project.tripmate.mainapp.CityListAdapter
//import com.project.tripmate.mainapp.CityListAdapter
import com.project.tripmate.mainapp.CommonFunctions
import com.project.tripmate.startpage.StartPage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignUpPage : AppCompatActivity() {
    lateinit var list: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_page)

        setInputVariables()
    }

    private fun setInputVariables() {

        val firstNameInput = findViewById<EditText>(R.id.firstNameField)
        val lastNameInput = findViewById<EditText>(R.id.lastNameField)
        val emailInput = findViewById<EditText>(R.id.signUpEmailField)
        val passwordInput = findViewById<EditText>(R.id.signUpPasswordField)
        val cityInput = findViewById<AutoCompleteTextView>(R.id.cityField)
        val dateOfBirthInput = findViewById<EditText>(R.id.dateOfBirthField)
        val phoneNumberInput = findViewById<EditText>(R.id.phoneNumberField)

        val createAccount = findViewById<Button>(R.id.createAccountButton)


        cityInput.threshold = 3
        createAccount.setOnClickListener {
            if (!CommonFunctions.checkName(
                    firstNameInput.text.toString(),
                    lastNameInput.text.toString(),
                    this
                )
            )
                return@setOnClickListener
            if (!CommonFunctions.checkEmail(emailInput.text.toString(), this))
                return@setOnClickListener
            if (!CommonFunctions.checkPassword(passwordInput.text.toString(), this))
                return@setOnClickListener
            if (!CommonFunctions.checkPhoneNumber(phoneNumberInput.text.toString(), this))
                return@setOnClickListener
            insertUser(
                emailInput.text.toString(),
                passwordInput.text.toString(),
                firstNameInput.text.toString(),
                lastNameInput.text.toString(),
                cityInput.text.toString(),
                dateOfBirthInput.text.toString(),
                phoneNumberInput.text.toString()
            )
        }
        cityInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val newText = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
                val newText = s.toString()
                if(newText.isNotEmpty()){
                    val list = CityListAdapter.get10Cities(this@SignUpPage, newText)
                    Handler(Looper.getMainLooper()).postDelayed({
                        val adapter = ArrayAdapter<String>(this@SignUpPage, android.R.layout.simple_list_item_1 ,list)
                        cityInput.setAdapter(adapter)
                    }, 100)
                }
            }
        })

        dateOfBirthInput.setOnClickListener {
            CommonFunctions.showDatePickerDialog(dateOfBirthInput, this, true)
        }
    }

    private fun insertUser(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        city: String,
        dob: String,
        number: String
    ) {
        try {
            val retroFit = Retrofit.Builder()
                .baseUrl(CommonFunctions.getBaseUrl())
                .addConverterFactory(
                    GsonConverterFactory.create(
                        GsonBuilder().setLenient().create()
                    )
                )
                .build()
                .create(CredentialsInterface::class.java)


            retroFit.insertUserData(email, password, firstName, lastName, city, dob, number)
                .enqueue(object : Callback<SignUpResult> {
                    override fun onResponse(
                        call: Call<SignUpResult>,
                        response: Response<SignUpResult>
                    ) {
                        val result = response.body()
                        if (response.isSuccessful) {
                            if (result != null) {
                                Toast.makeText(
                                    this@SignUpPage,
                                    "User Added Successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                startActivity(Intent(this@SignUpPage, StartPage::class.java))
                            } else {
                                Toast.makeText(
                                    this@SignUpPage,
                                    "Response body is null",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Toast.makeText(
                                this@SignUpPage,
                                "Unsuccessful response: ${response.code()}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<SignUpResult>, t: Throwable) {
                        if (t is HttpException) {
                            val responseBody = t.response()?.errorBody()?.string()
                            println("Error Body: $responseBody")
                        }
                        println(t.message)
                        println(t.localizedMessage)
                        println(t.stackTrace)
                        Toast.makeText(this@SignUpPage, "Failure: ${t.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                })
        } catch (e: Exception) {
            // Handle other exceptions
            Toast.makeText(this, "Exception: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}