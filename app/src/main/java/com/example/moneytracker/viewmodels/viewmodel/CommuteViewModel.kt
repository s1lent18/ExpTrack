package com.example.moneytracker.viewmodels.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytracker.models.interfaces.CommuteAPI
import com.example.moneytracker.models.model.AddCommuteRequest
import com.example.moneytracker.models.model.AddCommuteResponse
import com.example.moneytracker.models.model.GetCommutesResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommuteViewModel @Inject constructor(
    private val commuteAPI: CommuteAPI
) : ViewModel() {

    private val _addCommuteResult = MutableStateFlow<AddCommuteResponse?>(null)
    val addCommuteResult : StateFlow<AddCommuteResponse?> = _addCommuteResult

    private val _getCommuteResult = MutableStateFlow<GetCommutesResponse?>(null)
    val getCommuteResult : StateFlow<GetCommutesResponse?> = _getCommuteResult

    private val _deleteCommuteResult = MutableStateFlow<String?>(null)
    val deleteCommuteResult : StateFlow<String?> = _deleteCommuteResult

    fun addCommute(userId: Int, addCommuteRequest: AddCommuteRequest, token: String) {
        viewModelScope.launch {
            try {
                val response = commuteAPI.addCommute(
                    userId = userId,
                    token = token,
                    request = addCommuteRequest
                )
                if (response.isSuccessful && response.code() == 200) {
                    response.body()?.let {
                        _addCommuteResult.value = it
                    }
                }
                else {
                    _addCommuteResult.value = null
                }
            } catch (e: Exception) {
                Log.d("Exception", "$e")
            }
        }
    }

    fun getCommute(userId: Int, token: String) {
        viewModelScope.launch {
            try {
                val response = commuteAPI.getCommutes(
                    userId = userId,
                    token = token
                )

                if (response.isSuccessful && response.code() == 200) {
                    response.body()?.let {
                        _getCommuteResult.value = it
                    }
                }
                else {
                    _addCommuteResult.value = null
                }
            } catch (e: Exception) {
                Log.d("Exception", "$e")
            }
        }
    }

    fun deleteCommute(userId: Int, token: String, id: Int) {
        viewModelScope.launch {
            try {
                val response = commuteAPI.deleteCommute(
                    userId = userId,
                    commuteId = id,
                    token = token
                )

                if (response.isSuccessful && response.code() == 200) {
                    response.body()?.let {
                        _deleteCommuteResult.value = it
                    }
                }
                else {
                    _addCommuteResult.value = null
                }
            } catch (e: Exception) {
                Log.d("Exception", "$e")
            }
        }
    }

}