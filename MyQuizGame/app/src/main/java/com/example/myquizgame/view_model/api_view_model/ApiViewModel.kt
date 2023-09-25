package com.example.myquizgame.view_model.api_view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.callapibyretrofit.model.DataModel
import com.example.myquizgame.data.repository.api_repository.ApiRepository

class ApiViewModel: ViewModel(), ApiViewModelService {
    private val data = MutableLiveData<DataModel>()
    private val apiRepository = ApiRepository(this)

    init {
        apiRepository.getData()
    }

    override fun getDataFromRepository(dataModel: DataModel) {
        this.data.value = dataModel
    }

    override fun getData(): LiveData<DataModel> = data
}