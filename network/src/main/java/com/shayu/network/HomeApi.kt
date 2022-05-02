package com.shayu.network

import com.shayu.network.model.SliderResponce
import dagger.Provides
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


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

interface HomeApi {

    @GET("slider_images")
    suspend fun sliderData(): SliderResponce

 /*   @GET("home_page_flags/{id}")
    suspend fun homePageFlags(@Path(value = "id", encoded = false) id: Int): HomeFlagsResponce

    @POST("register_live_event")
    suspend fun registerLiveEvent(@Body body: RequestBody): RegisterLiveEventResponce*/


/*  @FormUrlEncoded
@POST("login")
suspend fun userLogin(
   @Field("email") email: String,
   @Field("password") password: String
) : Response<AuthResponse>*/
}