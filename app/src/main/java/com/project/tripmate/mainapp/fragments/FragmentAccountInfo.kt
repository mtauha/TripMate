package com.project.tripmate.mainapp.fragments

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.gson.GsonBuilder
import com.project.tripmate.R
import com.project.tripmate.mainapp.CommonFunctions
import com.project.tripmate.signing.CredentialsInterface
import com.project.tripmate.signing.CurrentUser
import com.project.tripmate.signing.SignInPage
import com.project.tripmate.signing.UpdationStatus
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FragmentAccountInfo : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var firstNameText: EditText
    lateinit var lastNameText: EditText
    lateinit var emailText: EditText
    lateinit var phoneText: EditText
    lateinit var dobText: EditText
    lateinit var cityText: AutoCompleteTextView

    private fun setValuesToInfo(view : View){
        val profilePicture = view.findViewById<ImageView>(R.id.accountProfilePicture)

        firstNameText = view.findViewById<EditText>(R.id.accountPageFirstName)
        lastNameText = view.findViewById<EditText>(R.id.accountPageLastName)
        emailText = view.findViewById<EditText>(R.id.accountPageEmail)
        phoneText = view.findViewById<EditText>(R.id.accountPagePhoneNumber)
        dobText = view.findViewById<EditText>(R.id.accountPageDob)
        cityText = view.findViewById<AutoCompleteTextView>(R.id.accountPageCity)

        val firstNameTextEdit = view.findViewById<ImageView>(R.id.accountPageFirstNameEdit)
        val lastNameTextEdit = view.findViewById<ImageView>(R.id.accountPageLastNameEdit)
        val emailTextEdit = view.findViewById<ImageView>(R.id.accountPageEmailEdit)
        val phoneTextEdit = view.findViewById<ImageView>(R.id.accountPagePhoneNumberEdit)
        val dobTextEdit = view.findViewById<ImageView>(R.id.accountPageDobEdit)
        val cityTextEdit = view.findViewById<ImageView>(R.id.accountPageCityEdit)

        val changePassword = view.findViewById<Button>(R.id.accountPageChangePassword)
        changePassword.setOnClickListener{
            showChangePasswordDialog()
        }


        val saveChanges = view.findViewById<Button>(R.id.accountSaveChangesButton)
        saveChanges.setOnClickListener{
            if(!CommonFunctions.checkName(firstNameText.text.toString(), lastNameText.text.toString(), requireContext()))
                return@setOnClickListener
            if(!CommonFunctions.checkEmail(emailText.text.toString(), requireContext()))
                return@setOnClickListener
            if(!CommonFunctions.checkPhoneNumber(phoneText.text.toString(), requireContext()))
                return@setOnClickListener
            showPasswordInputDialog()
        }



        firstNameTextEdit.setOnClickListener{
                focusObject(firstNameText, true)
            firstNameTextEdit.visibility = View.INVISIBLE
            saveChanges.visibility = View.VISIBLE
        }
        lastNameTextEdit.setOnClickListener{
            focusObject(lastNameText, true)
            lastNameTextEdit.visibility = View.INVISIBLE
            saveChanges.visibility = View.VISIBLE
        }
        emailTextEdit.setOnClickListener{
                focusObject(emailText, true)
            emailTextEdit.visibility = View.INVISIBLE
            saveChanges.visibility = View.VISIBLE
        }

        phoneTextEdit.setOnClickListener{
                focusObject(phoneText, true)
                phoneTextEdit.visibility = View.INVISIBLE
            saveChanges.visibility = View.VISIBLE
        }

        dobText.setOnClickListener{
            CommonFunctions.showDatePickerDialog(dobText, requireContext(),true)
            saveChanges.visibility = View.VISIBLE
        }
        dobTextEdit.setOnClickListener{
            focusObject(dobText, true)
            dobTextEdit.visibility = View.INVISIBLE
            saveChanges.visibility = View.VISIBLE
        }

        cityTextEdit.setOnClickListener{
            focusObject(cityText, true)
            cityTextEdit.visibility = View.INVISIBLE
            saveChanges.visibility = View.VISIBLE
        }


        val imgResId = R.drawable.title
        profilePicture.setImageResource(imgResId)

        val logoutButton = view.findViewById<Button>(R.id.logoutButton)
        logoutButton.setOnClickListener{
            activity?.finish()
            startActivity(Intent(context, SignInPage::class.java))
        }

        firstNameText.setText(CurrentUser.userData.first_name)
        lastNameText.setText(CurrentUser.userData.last_name)
        emailText.setText(CurrentUser.userData.user_email)
        phoneText.setText("0${CurrentUser.userData.phone_number}")
        dobText.setText(CurrentUser.userData.date_of_birth)
        cityText.setText(CurrentUser.userData.city_name)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view  = inflater.inflate(R.layout.fragment_account_info, container, false)

        setValuesToInfo(view)

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentAccountInfo().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun focusObject(textEdit: EditText, focus: Boolean){
        textEdit.isFocusable = focus
        textEdit.isClickable = focus
        textEdit.isEnabled = focus
        textEdit.isFocusableInTouchMode = focus
    }
    private fun focusObject(textEdit: AutoCompleteTextView, focus: Boolean){
        textEdit.isFocusable = focus
        textEdit.isClickable = focus
        textEdit.isEnabled = focus
        textEdit.isFocusableInTouchMode = focus
    }

    private fun showPasswordInputDialog() {
        val dialogView = layoutInflater.inflate(R.layout.enter_password_layout, null)
        val passwordEditText: EditText = dialogView.findViewById(R.id.saveChangesPasswordField)
        val okButton: Button = dialogView.findViewById(R.id.saveChangesEnterButton)

        val alertDialogBuilder = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setTitle("Enter Password")

        val alertDialog = alertDialogBuilder.create()

        okButton.setOnClickListener {
            val enteredPassword = passwordEditText.text.toString()
            if(CommonFunctions.md5(enteredPassword) == CurrentUser.userData.password){
                editUserDetails(firstNameText.text.toString(),
                    lastNameText.text.toString(),
                    emailText.text.toString(),
                    dobText.text.toString(),
                    phoneText.text.toString(),
                    cityText.text.toString())
            }
            else{
                Toast.makeText(requireContext(), "Wrong Password", Toast.LENGTH_SHORT).show()
            }
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    private fun showChangePasswordDialog(){
        val dialogView = layoutInflater.inflate(R.layout.change_password_layout, null)
        val oldPasswordEditText: EditText = dialogView.findViewById(R.id.changePasswordOldPassword)
        val newPasswordEditText: EditText = dialogView.findViewById(R.id.changePasswordNewPassword)
        val okButton: Button = dialogView.findViewById(R.id.saveChangePasswordEnterButton)

        val alertDialogBuilder = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setTitle("Change Password")

        val alertDialog = alertDialogBuilder.create()

        okButton.setOnClickListener {
            val enteredPassword = oldPasswordEditText.text.toString()
            if(CommonFunctions.md5(enteredPassword) == CurrentUser.userData.password){
                if(CommonFunctions.checkPassword(newPasswordEditText.text.toString(), requireContext()))
                    changePassword(newPasswordEditText.text.toString())
            }
            else{
                Toast.makeText(requireContext(), "Wrong Password", Toast.LENGTH_SHORT).show()
            }
            alertDialog.dismiss()
        }
        alertDialog.show()
    }

    private fun editUserDetails(firstName: String,
                                lastName: String,
                                userEmail: String,
                                dob: String,
                                number: String,
                                city: String)
    {
        println(CurrentUser.userData.user_id)
        println(firstName)
        println(lastName)
        println(userEmail)
        println(dob)
        println(number)
        println(city)
        try {
            val retroFit = Retrofit.Builder()
                .baseUrl(CommonFunctions.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
                .build()
                .create(CredentialsInterface::class.java)


            retroFit.editUser(CurrentUser.userData.user_id, firstName, lastName, userEmail, dob, number, city)
                .enqueue(object : Callback<UpdationStatus> {
                    override fun onResponse(
                        call: Call<UpdationStatus>,
                        response: Response<UpdationStatus>
                    ) {

                        val result = response.body()
                        if (response.isSuccessful) {
                            if (result != null) {
                                if(result.success == null){
                                    Toast.makeText(requireContext(), "Unable To change Data", Toast.LENGTH_SHORT).show()
                                }
                                else{
                                    Toast.makeText(requireContext(), "Data Changed Successfully", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(requireContext(), "Response body is null", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(requireContext(), "Unsuccessful response: ${response.code()}", Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onFailure(call: Call<UpdationStatus>, t: Throwable) {
                        if (t is HttpException) {
                            val responseBody = t.response()?.errorBody()?.string()
                            println("Error Body: $responseBody")
                        }
                        println(t.message)
                        println(t.localizedMessage)
                        println(t.stackTrace)
                        Toast.makeText(requireContext(), "Failure: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
        } catch (e: Exception) {
            // Handle other exceptions
            Toast.makeText(requireContext(), "Exception: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun changePassword(password: String){
        try {
            val retroFit = Retrofit.Builder()
                .baseUrl(CommonFunctions.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
                .build()
                .create(CredentialsInterface::class.java)

            println(CurrentUser.userData.user_id)
            println(password)

            retroFit.changePassword(CurrentUser.userData.user_id, password)
                .enqueue(object : Callback<UpdationStatus> {
                    override fun onResponse(
                        call: Call<UpdationStatus>,
                        response: Response<UpdationStatus>
                    ) {

                        val result = response.body()
                        if (response.isSuccessful) {
                            if (result != null) {
                                if(result.success == null){
                                    Toast.makeText(requireContext(), "Unable To change Password", Toast.LENGTH_SHORT).show()
                                }
                                else{
                                    Toast.makeText(requireContext(), "Password Changed Successfully", Toast.LENGTH_SHORT).show()
                                    CurrentUser.userData.password = CommonFunctions.md5(password)
                                }
                            } else {
                                Toast.makeText(requireContext(), "Response body is null", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(requireContext(), "Unsuccessful response: ${response.code()}", Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onFailure(call: Call<UpdationStatus>, t: Throwable) {
                        if (t is HttpException) {
                            val responseBody = t.response()?.errorBody()?.string()
                            println("Error Body: $responseBody")
                        }
                        println(t.message)
                        println(t.localizedMessage)
                        println(t.stackTrace)
                        Toast.makeText(requireContext(), "Failure: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
        } catch (e: Exception) {
            // Handle other exceptions
            Toast.makeText(requireContext(), "Exception: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}