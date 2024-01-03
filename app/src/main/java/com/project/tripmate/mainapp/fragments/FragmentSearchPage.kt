package com.project.tripmate.mainapp.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.project.tripmate.R
import com.project.tripmate.mainapp.CityListAdapter
import com.project.tripmate.mainapp.MainAppPage

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class FragmentSearchPage : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val allCities = ArrayList<String>();
    private lateinit var searchList : AutoCompleteTextView;

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
        val view = inflater.inflate(R.layout.fragment_search_page, container, false)
        initializeSearchList(view)
        return view
    }

    private fun initializeSearchList(view : View){
        searchList = view.findViewById<AutoCompleteTextView>(R.id.searchTextView)
        searchList.threshold = 2

        searchList.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val newText = s.toString()
                if(newText.isNotEmpty()){
                    val list = CityListAdapter.get10Cities(requireContext(), newText)
                    Handler(Looper.getMainLooper()).postDelayed({
                        val adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1 ,list)
                        searchList.setAdapter(adapter)
                    }, 100)
                }
            }

            override fun afterTextChanged(s: Editable?) {
                val newText = s.toString()
                println(newText)
                if (newText.isNotEmpty()) {
                }
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentSearchPage().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}