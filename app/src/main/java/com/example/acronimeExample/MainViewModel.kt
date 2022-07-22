package com.example.acronimeExample

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    val _sfLiveData = MutableLiveData<List<AcronimesSF>>()
    val SFLiveData: LiveData<List<AcronimesSF>> = _sfLiveData

    fun searchText(text: String) {
        var retrofit = RetrofitClient.getInstance()
        var apiInterface = retrofit.create(ApiInterface::class.java)
        viewModelScope.launch {
            try {
                val response = apiInterface.getShortForms("HMM")
                if (response.isSuccessful()) {

                    val result = response?.body()
                    if (result != null) {
                        Log.i("Body", response.body().toString())
                        _sfLiveData.value = result
                    }
                } else {
                    Log.e("Error", response.errorBody().toString())
                }
            }catch (Ex:Exception){
                Log.e("Error",Ex.localizedMessage)
            }
        }
    }
}