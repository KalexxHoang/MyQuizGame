package com.example.myquizgame.view.api

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.myquizgame.R
import com.example.myquizgame.databinding.FragmentApiBinding
import com.example.myquizgame.view_model.api_view_model.ApiViewModel


class ApiFragment : Fragment() {
    private lateinit var apiBinding: FragmentApiBinding
    private val apiViewModel: ApiViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        apiBinding = FragmentApiBinding.inflate(layoutInflater)

        // Inflate the layout for this fragment
        return apiBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set an OnClickListener on the button view.
        apiBinding.btnJoke.setOnClickListener {
            // show the progress bar
            apiBinding.idLoadingPB.visibility = View.VISIBLE

            apiViewModel.getData().observe(viewLifecycleOwner, Observer {
                apiBinding.tvInfo.text = it.error.info
                // hide the progress bar
                apiBinding.idLoadingPB.visibility = View.GONE
            })
        }
    }
}