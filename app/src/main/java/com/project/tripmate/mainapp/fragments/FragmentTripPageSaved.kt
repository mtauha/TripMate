package com.project.tripmate.mainapp.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.tripmate.R
import com.project.tripmate.mainapp.CommonFunctions
import com.project.tripmate.mainapp.fragments.triprecord.CurrentTrip
import com.project.tripmate.mainapp.fragments.triprecord.TripDataInterface
import com.project.tripmate.mainapp.fragments.triprecord.noteandchat.InsertNoteResult
import com.project.tripmate.mainapp.fragments.triprecord.noteandchat.NoteData
import com.project.tripmate.mainapp.fragments.triprecord.noteandchat.PreviousNotes
import com.project.tripmate.mainapp.fragments.triprecord.noteandchat.TripNoteAdapter
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

class FragmentTripPageSaved : Fragment() {
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

    lateinit var recyclerView: RecyclerView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_trip_page_saved, container, false)

        recyclerView = view.findViewById(R.id.noteRecycleView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        loadNotes()

        val addNote = view.findViewById<Button>(R.id.addNoteTripButton)
        addNote.setOnClickListener{
            showAddNoteDialog()
        }

        return view
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentTripPageSaved().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showAddNoteDialog(){
        val dialogView = layoutInflater.inflate(R.layout.add_note_layout, null)
        val headerInput: EditText = dialogView.findViewById(R.id.addNoteHeadingInput)
        val bodyInput: EditText = dialogView.findViewById(R.id.addNoteBodyInput)


        val addButton = dialogView.findViewById<Button>(R.id.addNoteAddButton)

        val alertDialogBuilder = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setTitle("Enter Note")

        val alertDialog = alertDialogBuilder.create()

        addButton.setOnClickListener{
            if(headerInput.text.isEmpty() || bodyInput.text.isEmpty())
                Toast.makeText(requireContext(), "Please Fill Both Fields", Toast.LENGTH_SHORT).show()
            else{
                insertNote(headerInput.text.toString(), bodyInput.text.toString())
            }
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun insertNote(
        heading: String,
        body: String
    ){
        println(heading)
        println(body)
        try {
            val retroFit = Retrofit.Builder()
                .baseUrl(CommonFunctions.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TripDataInterface::class.java)


            retroFit.insertNote(CurrentUser.userData.user_id, CurrentTrip.trip.trip_id, heading, body, CommonFunctions.getCurrentDate())
                .enqueue(object : Callback<InsertNoteResult> {
                    override fun onResponse(
                        call: Call<InsertNoteResult>,
                        response: Response<InsertNoteResult>
                    ) {
                        if (response.isSuccessful) {
                                Toast.makeText(requireContext(), "Note Added Successfully", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(requireContext(), "Unsuccessful response: ${response.code()}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<InsertNoteResult>, t: Throwable) {
                        Toast.makeText(requireContext(), "Failure: ${t.message}", Toast.LENGTH_LONG).show()
                    }
                })
        } catch (e: Exception) {
            // Handle other exceptions
            Toast.makeText(requireContext(), "Exception: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadNotes(){
        try {
            val retroFit = Retrofit.Builder()
                .baseUrl(CommonFunctions.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TripDataInterface::class.java)

            println(CurrentUser.userData.user_id)
            println(CurrentTrip.trip.trip_id)

            retroFit.retrieveNotes(CurrentUser.userData.user_id, CurrentTrip.trip.trip_id)
                .enqueue(object : Callback<PreviousNotes> {
                    override fun onResponse(
                        call: Call<PreviousNotes>,
                        response: Response<PreviousNotes>
                    ) {
                        if (response.isSuccessful) {
                            val noteData = response.body()?.note_data
                            if (noteData != null) {
                                val n = noteData.size
                                val notes = ArrayList<NoteData>()
                                for (i in 0..<n){
                                    notes.add(noteData[i])
                                    println(notes[i].heading)
                                }
                                val adapter = TripNoteAdapter(requireContext(), notes)
                                recyclerView.adapter = adapter
                            }
                        } else {
                            Toast.makeText(requireContext(), "Unsuccessful response: ${response.code()}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<PreviousNotes>, t: Throwable) {
                        Toast.makeText(requireContext(), "Failure: ${t.message}", Toast.LENGTH_LONG).show()
                    }
                })
        } catch (e: Exception) {
            // Handle other exceptions
            Toast.makeText(requireContext(), "Exception: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}