package com.example.myquizgame.view.api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.example.myquizgame.R
import com.example.myquizgame.data.api.ApiCall
import com.example.myquizgame.databinding.ActivityGetApiBinding
import com.example.myquizgame.view_model.api_view_model.ApiViewModel

class GetApiActivity : AppCompatActivity() {
    private lateinit var getApiBinding: ActivityGetApiBinding
    private lateinit var apiTransaction: FragmentTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getApiBinding = ActivityGetApiBinding.inflate(layoutInflater)
        setContentView(getApiBinding.root)

        apiTransaction = supportFragmentManager.beginTransaction()

        val apiFragment = ApiFragment()
        apiTransaction.replace(R.id.container_api, apiFragment).addToBackStack(null).commit()
    }
}