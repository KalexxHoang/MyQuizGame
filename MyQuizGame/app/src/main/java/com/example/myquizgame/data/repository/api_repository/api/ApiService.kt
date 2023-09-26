package com.example.myquizgame.data.repository.api_repository.api

import com.example.callapibyretrofit.model.DataModel
import retrofit.Call
import retrofit.http.GET
import retrofit.http.Query

// This interface defines an API
// service for getting random jokes.
interface ApiService {
    // Link API: http://apilayer.net/api/live?access_key=843d4d34ae72b3882e3db642c51e28e6&currencies=VND&source=USD&format=1

    // This annotation specifies that the HTTP method
    // is GET and the endpoint URL is "jokes/random".
    @GET("api/live")
    // This method returns a Call object with a generic
    // type of DataModel, which represents
    // the data model for the response.
    fun getData(@Query("access_key") access_key: String,
                @Query("currencies") currencies: String,
                @Query("source") source: String,
                @Query("format") format: Int): Call<DataModel>
}