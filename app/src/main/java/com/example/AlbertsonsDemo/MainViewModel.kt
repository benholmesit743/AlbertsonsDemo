package com.example.AlbertsonsDemo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    val retrofit = RetrofitClient.getInstance()
    val apiInterface = retrofit.create(ApiInterface::class.java)

    val _sfLiveData = MutableLiveData<List<AcromineSF>>()
    val SFLiveData: LiveData<List<AcromineSF>> = _sfLiveData

    fun searchText(text: String?) {
        if (text == null) return

        viewModelScope.launch {
            try {
                val response = apiInterface.getShortForms(text)
                if (response.isSuccessful) {

                    val result = response.body()
                    if (result != null) {
                        Log.i("Body", response.body().toString())
                        _sfLiveData.value = result
                    }
                } else {
                    Log.e("Error", response.errorBody().toString())
                    _sfLiveData.value = emptyList()
                }
            }catch (Ex:Exception){
                Log.e("Error",Ex.localizedMessage)
            }
        }
    }
}