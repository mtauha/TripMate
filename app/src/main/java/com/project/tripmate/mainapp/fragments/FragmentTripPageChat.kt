package com.project.tripmate.mainapp.fragments

import android.content.Intent
import android.icu.text.ListFormatter.Width
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.marginBottom
import androidx.core.view.setPadding
import com.project.tripmate.R
import com.project.tripmate.R.drawable.message_background
import com.project.tripmate.mainapp.CommonFunctions
import com.project.tripmate.mainapp.fragments.triprecord.CurrentTrip
import com.project.tripmate.mainapp.fragments.triprecord.TripDataInterface
import com.project.tripmate.mainapp.fragments.triprecord.noteandchat.InsertChatResult
import com.project.tripmate.mainapp.fragments.triprecord.noteandchat.InsertNoteResult
import com.project.tripmate.mainapp.fragments.triprecord.noteandchat.RetreiveChatResult
import com.project.tripmate.signing.CurrentUser
import com.project.tripmate.startpage.StartPage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Timer
import java.util.TimerTask
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FragmentTripPageChat : Fragment() {
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

    lateinit var layout: LinearLayout

    private val scheduler: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()

    fun scheduleTaskAtFixedRate(task: Runnable, initialDelay: Long, period: Long) {
        scheduler.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.MILLISECONDS)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_trip_page_chat, container, false)

        layout = view.findViewById<LinearLayout>(R.id.chatLinearLayout)
        retrieveChats()

        val messageField = view.findViewById<EditText>(R.id.chatMessageInputField)
        val sendMessage = view.findViewById<ImageView>(R.id.sendMessageButton)

        sendMessage.setOnClickListener{
            if(messageField.text.isNotEmpty()){
                writeMessage("${CurrentUser.userData.first_name} ${CurrentUser.userData.last_name}: ${messageField.text.toString()}", true)
                insertChat("${CurrentUser.userData.first_name} ${CurrentUser.userData.last_name}: ${messageField.text.toString()}")
                messageField.setText("")
            }
        }


        // Inflate the layout for this fragment
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentTripPageChat().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun writeMessage(message: String, lef: Boolean){
        val text = TextView(requireContext())
        text.setBackgroundResource(R.drawable.message_background)
        text.text = message
        text.textSize = 20F
        if(lef)
            text.gravity = Gravity.LEFT
        else
            text.gravity = Gravity.RIGHT
        text.setPadding(15, 5, 15, 5)


        // Set margins programmatically
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            // Set your desired margins here
            if(lef)
                setMargins(25, 8, 20, 8)
            else
                setMargins(250, 8, 25, 8)
        }

        text.layoutParams = layoutParams

        layout.addView(text)
    }

    private fun insertChat(message: String){
            try {
                val retroFit = Retrofit.Builder()
                    .baseUrl(CommonFunctions.getBaseUrl())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(TripDataInterface::class.java)


                println(CurrentUser.userData.user_id)
                println(CurrentTrip.trip.trip_id)
                println(message)
                println(CommonFunctions.getCurrentTimestamp())

                retroFit.insertChat(CurrentUser.userData.user_id, CurrentTrip.trip.trip_id, message, CommonFunctions.getCurrentTimestamp())
                    .enqueue(object : Callback<InsertChatResult> {
                        override fun onResponse(
                            call: Call<InsertChatResult>,
                            response: Response<InsertChatResult>
                        ) {
                            if (response.isSuccessful) {
                                Toast.makeText(requireContext(), "Note Added Successfully", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(requireContext(), "Unsuccessful response: ${response.code()}", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<InsertChatResult>, t: Throwable) {
                            Toast.makeText(requireContext(), "Failure: ${t.message}", Toast.LENGTH_LONG).show()
                        }
                    })
            } catch (e: Exception) {
                // Handle other exceptions
                Toast.makeText(requireContext(), "Exception: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    fun retrieveChats(){
        try {
            val retroFit = Retrofit.Builder()
                .baseUrl(CommonFunctions.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TripDataInterface::class.java)

            println(CommonFunctions.getCurrentTimestamp())

            retroFit.getChats(CurrentTrip.trip.trip_id)
                .enqueue(object : Callback<RetreiveChatResult> {
                    override fun onResponse(
                        call: Call<RetreiveChatResult>,
                        response: Response<RetreiveChatResult>
                    ) {
                        if (response.isSuccessful) {
                            val chat = response.body()
                            if(chat != null){
                                val n = chat.chat_data.size
                                Toast.makeText(requireContext(), "Chat Successfully Retrieved", Toast.LENGTH_SHORT).show()
                                for (i in 0..<n){
                                    if(chat.chat_data[i].user_id == CurrentUser.userData.user_id)
                                        writeMessage(chat.chat_data[i].message, true)
                                    else
                                        writeMessage(chat.chat_data[i].message, false)
                                }
                            }
                        } else {
                            Toast.makeText(requireContext(), "Unsuccessful response: ${response.code()}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<RetreiveChatResult>, t: Throwable) {
                        Toast.makeText(requireContext(), "Failure: ${t.message}", Toast.LENGTH_LONG).show()
                    }
                })
        } catch (e: Exception) {
            // Handle other exceptions
            Toast.makeText(requireContext(), "Exception: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

}