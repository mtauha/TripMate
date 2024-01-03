package com.project.tripmate.mainapp

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.project.tripmate.mainapp.fragments.triprecord.TripData
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

object CommonFunctions {
    private val BASE_URL: String = "http:hostIP/"
    public fun getBaseUrl(): String{
        return BASE_URL
    }
    public fun checkName(firstName: String, lastName: String, context: Context): Boolean{
        val regex = "[^a-zA-Z ]".toRegex()
        if(firstName.contains(regex)) {
            println("Invalid First Name")
            Toast.makeText(context, "Invalid First Name", Toast.LENGTH_SHORT).show()
            if(firstName.isEmpty())
            {
                Toast.makeText(context, "First Name Can Not Be Empty", Toast.LENGTH_SHORT).show()
            }
            return false
        }
        if(lastName.contains(regex)) {
            Toast.makeText(context, "Invalid Last Name", Toast.LENGTH_SHORT).show()
            println("Invalid Last Name")
            return false
        }
        return true
    }
    public fun checkEmail(email: String, context: Context): Boolean{
        val regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$".toRegex()
        if(regex.matches(email))
            return true
        else
            Toast.makeText(context, "Invalid Email", Toast.LENGTH_SHORT).show()
        return false
    }
    public fun checkPassword(password: String, context: Context): Boolean{
        if(password.length >= 8)
            return true
        else
            Toast.makeText(context, "Password should be at least 8 characters long", Toast.LENGTH_SHORT).show()
        return false
    }
    public fun checkPhoneNumber(number: String, context: Context): Boolean{
        val regex = """^(\+92|0)(\d{10})$""".toRegex()
        if(number.matches(regex))
            return true
        else
            Toast.makeText(context, "Invalid Phone Number", Toast.LENGTH_SHORT).show()
        return false
    }

    public fun showDatePickerDialog(date: EditText, context: Context, dob: Boolean) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context,
            DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = formatDate(selectedYear, selectedMonth + 1, selectedDay)
                date.setText(selectedDate)
                println(selectedDate)
            },
            year,
            month,
            day
        )

        // Set min and max date
        val minCalendar = Calendar.getInstance()
        if(dob)
            minCalendar.set(year - 120, Calendar.JANUARY, 1)
        else
            minCalendar.set(year, month, day)
        datePickerDialog.datePicker.minDate = minCalendar.timeInMillis

        val maxCalendar = Calendar.getInstance()
        if(dob)
            maxCalendar.set(year - 15, Calendar.DECEMBER, 31)
        else
            maxCalendar.set(year+1, month, day)
        datePickerDialog.datePicker.maxDate = maxCalendar.timeInMillis

        datePickerDialog.show()
    }

    private fun formatDate(year: Int, month: Int, day: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, day) // Note: month is 0-based
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    public fun checkTripName(name: String, context: Context): Boolean{
        if(name.isEmpty()){
            Toast.makeText(context, "Trip Name Can not be Empty", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    public fun checkNumber(value: String, type: Int, context: Context): Boolean{
        if(value.isEmpty()){
            Toast.makeText(context, "You Must Fill All Fields", Toast.LENGTH_SHORT).show()
        }
        val numericValue: Int? = value.toIntOrNull()
        if(numericValue == null || numericValue <= 0){
            if(type == 1)
                Toast.makeText(context, "Invalid Number of Persons", Toast.LENGTH_SHORT).show()
            else if(type == 2)
                Toast.makeText(context, "Invalid Budget", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(context, "Invalid Number of Days", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    public fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        val byteArray = md.digest(input.toByteArray())
        return byteArray.joinToString("") { "%02x".format(it) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentDate(): String {
        val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return LocalDate.now().format(dateFormat)
    }

    fun getCurrentTimestamp(): String {
        val timestampFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return timestampFormat.format(Date())
    }

    public lateinit var currentTrip: TripData;
}