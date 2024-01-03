package com.project.tripmate.mainapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.tripmate.R
import com.project.tripmate.mainapp.CommonFunctions
import com.project.tripmate.mainapp.fragments.triprecord.CurrentTrip
import com.project.tripmate.mainapp.fragments.triprecord.TripData
import com.project.tripmate.mainapp.fragments.triprecord.TripDataInterface
import com.project.tripmate.mainapp.fragments.triprecord.TripDataX
import com.project.tripmate.mainapp.fragments.triprecord.TripListAdapter
import com.project.tripmate.mainapp.fragments.triprecord.TripRecord
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

class FragmentTripRecord : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var allTripsBox: RecyclerView

    private var fragmentManger : FragmentManager ?= null

    val tripsList = ArrayList<TripData>()

    lateinit var recyclerView: RecyclerView


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
        val view = inflater.inflate(R.layout.fragment_trip_record, container, false)
        initializeTripBox(view)

        recyclerView = view.findViewById<RecyclerView>(R.id.allTripsView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        initializePreviousTrips(recyclerView)



        val newTrip = view.findViewById<LinearLayout>(R.id.newTripOption)
        newTrip.setOnClickListener{
            loadFragment(FragmentNewTrip())
        }

        fragmentManger = activity?.supportFragmentManager

        // Inflate the layout for this fragment
        return view
    }

    private fun initializeTripBox(view : View){
        allTripsBox = view.findViewById(R.id.allTripsView)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentTripRecord().apply {
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

    private fun initializePreviousTrips(recyclerView: RecyclerView){
        loadTripData()
    }

    private fun loadTripData(){
        try {
            val retroFit = Retrofit.Builder()
                .baseUrl(CommonFunctions.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TripDataInterface::class.java)

            retroFit.getTripData(CurrentUser.userData.user_id)
                .enqueue(object : Callback<TripDataX> {
                    override fun onResponse(
                        call: Call<TripDataX>,
                        response: Response<TripDataX>
                    ) {
                        if (response.isSuccessful) {
                            val tripData = response.body()?.trip_data
                            if (tripData != null) {
                                TripRecord.tripData = response.body()
                                val allTrips = TripRecord.tripData?.trip_data
                                val n = allTrips?.size
                                tripsList.clear()
                                for(i in 0..<n!!){
                                    tripsList.add(allTrips[i])
                                    println(tripsList[i].trip_name)
                                }
                                val adapter = TripListAdapter(requireContext(), tripsList)
                                recyclerView.adapter = adapter
                                adapter.setOnItemClickListener(object : TripListAdapter.OnItemClickListener{
                                    override fun onItemClick(position: Int){
                                        CurrentTrip.trip = tripsList[position]
                                        loadFragment(FragmentMainTripPage())
                                    }
                                })

                            }
                        } else {
                            Toast.makeText(requireContext(), "Unsuccessful response: ${response.code()}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<TripDataX>, t: Throwable) {
                        Toast.makeText(requireContext(), "Failure: ${t.message}", Toast.LENGTH_LONG).show()
                    }
                })
        } catch (e: Exception) {
            // Handle other exceptions
            Toast.makeText(requireContext(), "Exception: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}