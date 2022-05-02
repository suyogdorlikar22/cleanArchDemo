package com.shayu.cleanarchdemo.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shayu.cleanarchdemo.repository.HomeRepository
import com.shayu.network.Resource
import com.shayu.network.model.SliderResponce
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 *
 * @author SHYAM BORSE
 *
 *         © Copyright APAC Financial Services
 *
 *         File Name : NumberEmployeeUnderManager.java
 *
 *         Modification History
 *
 *         16-Oct-2020 Shyam Borse : Initial version
 *                               01-Jul-2021 First Last : Fix issue with getting reportee details method
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {


    private val _sliderResponse: MutableLiveData<Resource<SliderResponce>> = MutableLiveData()
    val loading = mutableStateOf(false)

    //slider responce
    val sliderResponse: LiveData<Resource<SliderResponce>>
        get() = _sliderResponse

    //slider api
    fun sliderData() = viewModelScope.launch {
        loading.value = true
        _sliderResponse.value = repository.sliderData()
        loading.value = false
    }
}