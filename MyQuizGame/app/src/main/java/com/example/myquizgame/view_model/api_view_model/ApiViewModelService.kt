package com.example.myquizgame.view_model.api_view_model

import androidx.lifecycle.LiveData
import com.example.callapibyretrofit.model.DataModel

interface ApiViewModelService {
    fun getDataFromRepository(dataModel: DataModel)

    fun getData(): LiveData<DataModel>
}