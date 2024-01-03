package com.project.tripmate.mainapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.project.tripmate.R
import com.project.tripmate.mainapp.fragments.FragmentAccountInfo
import com.project.tripmate.mainapp.fragments.FragmentNewTrip
import com.project.tripmate.mainapp.fragments.FragmentNotifications
import com.project.tripmate.mainapp.fragments.FragmentSearchPage
import com.project.tripmate.mainapp.fragments.FragmentTripRecord

class MainAppPage : AppCompatActivity() {

    private lateinit var accountButton:ImageButton
    private lateinit var searchButton:ImageButton
    private lateinit var tripButton:ImageButton
    private lateinit var notificationButton:ImageButton

    private val fragmentManger = supportFragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_app_page)

        accountButton = findViewById<ImageButton>(R.id.accountFragmentButton)
        searchButton = findViewById<ImageButton>(R.id.searchFragmentButton)
        tripButton = findViewById<ImageButton>(R.id.tripFragmentButton)
        notificationButton = findViewById<ImageButton>(R.id.notificationFragmentButton)

        accountButton.setOnClickListener{
            loadFragment(FragmentAccountInfo())
            accountButton.setImageResource(R.drawable.account_button_selected)
        }
        searchButton.setOnClickListener{
            loadFragment(FragmentSearchPage())
            searchButton.setImageResource(R.drawable.search_button_selected)
        }
        tripButton.setOnClickListener {
            loadFragment(FragmentTripRecord())
            tripButton.setImageResource(R.drawable.trip_button_selected)
        }
        notificationButton.setOnClickListener{
            loadFragment(FragmentNotifications())
            notificationButton.setImageResource(R.drawable.notification_button_selected)
        }

    }

    private fun loadFragment(fragment : Fragment){
        clearSelectedImage()
        fragmentManger.popBackStack()
        val fragmentTransaction = fragmentManger.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun clearSelectedImage(){
        accountButton.setImageResource(R.drawable.account_button)
        searchButton.setImageResource(R.drawable.search_button)
        tripButton.setImageResource(R.drawable.trip_button)
        notificationButton.setImageResource(R.drawable.notification_button)
    }
}