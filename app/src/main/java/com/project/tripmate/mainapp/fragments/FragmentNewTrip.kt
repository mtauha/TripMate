package com.project.tripmate.mainapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.google.gson.GsonBuilder
import com.project.tripmate.R
import com.project.tripmate.mainapp.CommonFunctions
import com.project.tripmate.mainapp.fragments.triprecord.CurrentTrip
import com.project.tripmate.mainapp.fragments.triprecord.NewTripApiReturn
import com.project.tripmate.mainapp.fragments.triprecord.TripDataInterface
import com.project.tripmate.signing.CredentialsInterface
import com.project.tripmate.signing.CurrentUser
import com.project.tripmate.signing.SignUpResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private var fragmentManger : FragmentManager?= null

class FragmentNewTrip : Fragment() {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_new_trip, container, false)

        val createTripButton = view.findViewById<Button>(R.id.newTripCreateButton)

        val tripName = view.findViewById<EditText>(R.id.newTripTripName)
        val sourceCity = view.findViewById<AutoCompleteTextView>(R.id.newTripSourceCity)
        val destinationCity = view.findViewById<AutoCompleteTextView>(R.id.newTripDestinationCity)
        val startDate = view.findViewById<EditText>(R.id.newTripStartDate)
        val noOfPersons = view.findViewById<EditText>(R.id.newTripNumberOfPersons)
        val budget = view.findViewById<EditText>(R.id.newTripBudget)
        val noOfDays = view.findViewById<EditText>(R.id.newTripNoOfDays)



        createTripButton.setOnClickListener{
            if(!CommonFunctions.checkTripName(tripName.text.toString(), requireContext()))
                return@setOnClickListener
            if(!CommonFunctions.checkNumber(noOfPersons.text.toString(), 1, requireContext()))
                return@setOnClickListener
            if(!CommonFunctions.checkNumber(budget.text.toString(), 2, requireContext()))
                return@setOnClickListener
            if(!CommonFunctions.checkNumber(noOfDays.text.toString(), 3, requireContext()))
                return@setOnClickListener
            insertTrip(tripName.text.toString(),
                sourceCity.text.toString(),
                destinationCity.text.toString(),
                startDate.text.toString(),
                noOfPersons.text.toString(),
                budget.text.toString(),
                noOfDays.text.toString())
        }

        startDate.setOnClickListener{
            CommonFunctions.showDatePickerDialog(startDate, requireContext(), false)
        }

        fragmentManger = activity?.supportFragmentManager

        return view
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            FragmentNewTrip().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun loadFragment(fragment : Fragment){
        fragmentManger?.popBackStack()
        val fragmentTransaction = fragmentManger?.beginTransaction()
        fragmentTransaction?.replace(R.id.container, fragment)
        fragmentTransaction?.addToBackStack(null)
        fragmentTransaction?.commit()
    }

    private fun insertTrip(name: String, source: String, destination: String, date: String, days: String, budget: String, persons: String){
        try {
            val retroFit = Retrofit.Builder()
                .baseUrl(CommonFunctions.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
                .build()
                .create(TripDataInterface::class.java)

            Toast.makeText(requireContext(), CurrentUser.userData.user_id, Toast.LENGTH_SHORT).show()

            retroFit.createNewTrip(name, source, destination, date, days,  budget, persons, CurrentUser.userData.user_id)
                .enqueue(object : Callback<NewTripApiReturn> {
                    override fun onResponse(
                        call: Call<NewTripApiReturn>,
                        response: Response<NewTripApiReturn>
                    ) {
                        Toast.makeText(requireContext(), response.body().toString(), Toast.LENGTH_SHORT).show()
                        val result = response.body()
                        println(response)
                        println(result)
                        if (response.isSuccessful) {
                            if (result != null) {
                                Toast.makeText(requireContext(), "Trip Added Successfully", Toast.LENGTH_SHORT).show()
                                loadFragment(FragmentTripRecord())
                            } else {
                                Toast.makeText(requireContext(), "Response body is null", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(requireContext(), "Unsuccessful response: ${response.code()}", Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onFailure(call: Call<NewTripApiReturn>, t: Throwable) {
                        println(t.message)
                        println(t.localizedMessage)
                        println(t.stackTrace)
                        Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                    }
                })
        } catch (e: Exception) {
            // Handle other exceptions
            Toast.makeText(requireContext(), "Exception: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}