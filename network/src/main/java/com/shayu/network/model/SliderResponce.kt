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
data class SliderResponce(val Data:  List<SliderModel>, val Message: String, val Status: Int)
