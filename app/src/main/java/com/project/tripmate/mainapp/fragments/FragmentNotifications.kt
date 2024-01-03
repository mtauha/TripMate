package com.project.tripmate.mainapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import com.project.tripmate.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentNotifications.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentNotifications : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var notifications: ListView
    private lateinit var notificationList:ArrayList<String>

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
        val view = inflater.inflate(R.layout.fragment_notifications, container, false)
        showNotifications(view)

        // Inflate the layout for this fragment
        return view
    }

    private fun loadNotifications(){
        notificationList = ArrayList(20)
        notificationList.add("Notifications")
        notificationList.add("Notifications")
        notificationList.add("Notifications")
        notificationList.add("Notifications")
        notificationList.add("Notifications")
        notificationList.add("Notifications")
        notificationList.add("Notifications")
        notificationList.add("Notifications")
        notificationList.add("Notifications")
        notificationList.add("Notifications")
        notificationList.add("Notifications")
        notificationList.add("Notifications")
        notificationList.add("Notifications")
    }

    private fun showNotifications(view : View){
        loadNotifications()
        notifications = view.findViewById(R.id.notifications)
        val notAdapter : ArrayAdapter<String> = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            notificationList
        )
        notifications.adapter = notAdapter
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentNotifications().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}