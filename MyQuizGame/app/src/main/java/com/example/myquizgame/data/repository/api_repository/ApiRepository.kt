package com.example.myquizgame.data.repository.api_repository

import com.example.myquizgame.data.api.ApiCall
import com.example.myquizgame.view_model.api_view_model.ApiViewModel

class ApiRepository(private val apiViewModel: ApiViewModel): ApiRepositoryService {
    override fun getData() {
        ApiCall().getData() {
            apiViewModel.getDataFromRepository(it)
        }
    }
}