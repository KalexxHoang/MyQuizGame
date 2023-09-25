package com.example.myquizgame.data.api

import android.content.Context
import android.widget.Toast
import com.example.callapibyretrofit.model.DataModel
import retrofit.*

class ApiCall {
    // Link API: http://apilayer.net/api/live?access_key=843d4d34ae72b3882e3db642c51e28e6&currencies=VND&source=USD&format=1

    fun getData(callback: (DataModel) -> Unit) {
        // Create a Retrofit instance with the base URL (Domain) and
        // a GsonConverterFactory for parsing the response.
        val retrofit: Retrofit = Retrofit.Builder().baseUrl("http://apilayer.net/")
            .addConverterFactory(GsonConverterFactory.create()).build()

        //  Create an ApiService instance from the Retrofit instance.
        val service: ApiService = retrofit.create(ApiService::class.java)

        // Call the getData() method of the ApiService to make an API request
        val call: Call<DataModel> = service.getData("843d4d34ae72b3882e3db642c51e28e6", "VND", "USD", 1)

        // Use the enqueue() method of the Call object to
        // make an asynchronous API request.
        call.enqueue(object : Callback<DataModel> {
            // This is an anonymous inner class that implements the Callback interface.
            override fun onResponse(response: Response<DataModel>?, retrofit: Retrofit?) {
                // This method is called when the API response is received successfully.

                if (response!!.isSuccess) {
                    // If the response is successful, parse the
                    // response body to a DataModel object.
                    val myDataObject = response.body() as DataModel

                    // Call the callback function with the DataModel
                    // object as a parameter.
                    callback(myDataObject)
                }
            }

            override fun onFailure(t: Throwable?) {
                // This method is called when the API request fails.
//                Toast.makeText(context, "Request Fail", Toast.LENGTH_SHORT).show()
            }
        })
    }
}