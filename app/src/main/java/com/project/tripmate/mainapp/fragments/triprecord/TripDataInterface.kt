package com.project.tripmate.mainapp.fragments.triprecord

import com.project.tripmate.mainapp.fragments.triprecord.noteandchat.BudgetInsertResult
import com.project.tripmate.mainapp.fragments.triprecord.noteandchat.InsertChatResult
import com.project.tripmate.mainapp.fragments.triprecord.noteandchat.InsertNoteResult
import com.project.tripmate.mainapp.fragments.triprecord.noteandchat.PreviousNotes
import com.project.tripmate.mainapp.fragments.triprecord.noteandchat.RetreiveChatResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TripDataInterface {
    @GET("apis/pages/all_trips.php/details")
    fun getTripData(
        @Query("user_id") userId: String
    ): Call<TripDataX>

    @GET("apis/pages/new_trip.php/details")
    fun createNewTrip(
        @Query("trip_name") tripName: String,
        @Query("source_city") sourceCity: String,
        @Query("destination_city") destinationCity: String,
        @Query("start_date") startDate: String,
        @Query("trip_days") tripDays: String,
        @Query("trip_budget") tripBudget: String,
        @Query("no_of_persons") noOfDays: String,
        @Query("admin_id") adminId: String
    ): Call <NewTripApiReturn>

    @GET("apis/pages/note.php/details")
    fun retrieveNotes(
        @Query("user_id") userId: String,
        @Query("trip_id") tripId: String
    ): Call<PreviousNotes>

    @GET("apis/Insert/insert_note_data.php/details")
    fun insertNote(
        @Query("user_id") userId: String,
        @Query("trip_id") tripId: String,
        @Query("heading") heading: String,
        @Query("body") body: String,
        @Query("note_date") date: String
    ): Call<InsertNoteResult>

    @GET("apis/Insert/insert_chat_data.php/details")
    fun insertChat(
        @Query("user_id") userId:String,
        @Query("trip_id") tripId: String,
        @Query("message") message: String,
        @Query("chat_time") time: String
    ): Call<InsertChatResult>

    @GET("apis/Pages/chat.php/details")
    fun getChats(
        @Query("trip_id") tripId: String
    ): Call<RetreiveChatResult>

    @GET("apis/Insert/insert_budget_data.php/details")
    fun insertBudget(
        @Query("trip_id") tripId: String,
        @Query("amount") amount: String,
        @Query("reason") reason: String
    ): Call<BudgetInsertResult>
}