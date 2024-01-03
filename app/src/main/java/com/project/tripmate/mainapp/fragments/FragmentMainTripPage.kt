package com.project.tripmate.mainapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.project.tripmate.R
import com.project.tripmate.mainapp.CommonFunctions
import com.project.tripmate.mainapp.fragments.triprecord.CurrentTrip
import com.project.tripmate.mainapp.fragments.triprecord.TripDataInterface
import com.project.tripmate.mainapp.fragments.triprecord.noteandchat.BudgetInsertResult
import com.project.tripmate.mainapp.fragments.triprecord.noteandchat.InsertChatResult
import com.project.tripmate.signing.CurrentUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FragmentMainTripPage : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var fragmentManger : FragmentManager?= null

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
        val view = inflater.inflate(R.layout.fragment_main_trip_page, container, false)

        val savedOption = view.findViewById<TextView>(R.id.tripMainSavedOption)
        val itineraryOption = view.findViewById<TextView>(R.id.tripMainItineraryOption)
        val chatOption = view.findViewById<TextView>(R.id.tripMainChatOption)

        val budget = view.findViewById<Button>(R.id.tripAvailableMoney)
        budget.setText("Budget: ${CurrentTrip.trip.trip_budget} Rs/-")

        budget.setOnClickListener{
            showBudgetDialog()
        }

        savedOption.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_blue))

        savedOption.setOnClickListener{
            loadFragment(FragmentTripPageSaved())
            savedOption.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_blue))
            itineraryOption.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            chatOption.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        }

        itineraryOption.setOnClickListener{
            loadFragment(FragmentTripPageItinerary())
            itineraryOption.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_blue))
            savedOption.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            chatOption.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        }

        chatOption.setOnClickListener{
            loadFragment(FragmentTripPageChat())
            chatOption.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_blue))
            savedOption.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            itineraryOption.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        }

        fragmentManger = childFragmentManager

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentMainTripPage().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun loadFragment(fragment : Fragment){
        fragmentManger?.popBackStack()
        val fragmentTransaction = fragmentManger?.beginTransaction()
        fragmentTransaction?.replace(R.id.tripOptionsFrgmentContainer, fragment)
        fragmentTransaction?.addToBackStack(null)
        fragmentTransaction?.commit()
    }

    private fun showBudgetDialog(){
        val dialogView = layoutInflater.inflate(R.layout.budget_record_layout, null)
        val reasonInput: EditText = dialogView.findViewById(R.id.moneySpentReasonInput)
        val amountInput: EditText = dialogView.findViewById(R.id.moneySpentAmountInput)

        val recyclerView = dialogView.findViewById<RecyclerView>(R.id.moneyRecordRecyclerView)

        val addButton = dialogView.findViewById<Button>(R.id.moneyRecordAddButton)
        val closeButton = dialogView.findViewById<Button>(R.id.moneyRecordCloseButton)

        val alertDialogBuilder = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setTitle("Enter Password")

        val alertDialog = alertDialogBuilder.create()

        addButton.setOnClickListener{
            if(reasonInput.text.isEmpty())
                Toast.makeText(requireContext(), "Reason Can Not Be Empty", Toast.LENGTH_SHORT).show()
            else{
                val numericValue: Int? = amountInput.text.toString().toIntOrNull()
                if(numericValue == null)
                    Toast.makeText(requireContext(), "Invalid Amount", Toast.LENGTH_SHORT).show()
                else if(numericValue > CurrentTrip.trip.trip_budget.toInt())
                    Toast.makeText(requireContext(), "Amount Can not be greater than Trip Budget", Toast.LENGTH_SHORT).show()
                else{
                    insertBudgetData(amountInput.text.toString(), reasonInput.text.toString())
                }
            }
        }

        closeButton.setOnClickListener{
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    private fun insertBudgetData(amount: String, reason: String){
        try {
            val retroFit = Retrofit.Builder()
                .baseUrl(CommonFunctions.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TripDataInterface::class.java)


            retroFit.insertBudget(CurrentTrip.trip.trip_id, amount, reason)
                .enqueue(object : Callback<BudgetInsertResult> {
                    override fun onResponse(
                        call: Call<BudgetInsertResult>,
                        response: Response<BudgetInsertResult>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(requireContext(), "Money Added Successfully", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(requireContext(), "Unsuccessful response: ${response.code()}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<BudgetInsertResult>, t: Throwable) {
                        Toast.makeText(requireContext(), "Failure: ${t.message}", Toast.LENGTH_LONG).show()
                    }
                })
        } catch (e: Exception) {
            // Handle other exceptions
            Toast.makeText(requireContext(), "Exception: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

}