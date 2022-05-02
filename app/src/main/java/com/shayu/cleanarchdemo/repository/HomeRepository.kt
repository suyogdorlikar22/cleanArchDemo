package com.shayu.cleanarchdemo.repository

import com.shayu.network.HomeApi
import com.shayu.network.SafeApiCall
import dagger.Provides
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

class HomeRepository @Inject constructor (
    private val api: HomeApi,

    ) : SafeApiCall {

    suspend fun sliderData() = safeApiCall {
        api.sliderData()
    }

    /* suspend fun homePageFlags(id:Int) = safeApiCall {
        api.homePageFlags(id)
    }
    suspend fun registerLiveEvent(body: RequestBody)= safeApiCall {
        api.registerLiveEvent(body)
    }*/
}