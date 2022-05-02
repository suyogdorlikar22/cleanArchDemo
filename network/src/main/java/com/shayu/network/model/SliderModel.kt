package com.shayu.network.model


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

data class SliderModel(val image_name: String,val title: String,val id: Int,val description: String,val path: String,val isPartOfSeries: Int,
                  val encoded_id: String,var selected: Boolean,var actionId: String,var actionType: String,var activeLiveEventUrl: String)