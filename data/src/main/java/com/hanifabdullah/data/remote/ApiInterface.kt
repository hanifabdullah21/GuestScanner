package com.hanifabdullah.data.remote

import com.hanifabdullah.data.model.BasicResponseModel
import com.hanifabdullah.data.model.app.GuestModel
import com.hanifabdullah.data.model.app.HomeModel
import com.hanifabdullah.data.model.app.UserModel
import io.reactivex.Observable
import retrofit2.http.*

interface ApiInterface {

    @GET("exec")
    fun getGuestById(
        @Query("action") action: String?,
        @Query("id") id: String?
    ): Observable<BasicResponseModel<GuestModel>>

    @GET("exec")
    fun getHome(
        @Query("action") action: String?,
        @Query("brides_id") bridesId: String?
    ): Observable<BasicResponseModel<HomeModel>>

    @FormUrlEncoded
    @POST("exec")
    fun login(
        @Field("action") action: String?,
        @Field("username") username: String?,
        @Field("password") password: String?
    ): Observable<BasicResponseModel<UserModel>>

    @GET("exec")
    fun getAllGuest(
        @Query("action") action: String?,
        @Query("brides_id") bridesId: String?
    ): Observable<BasicResponseModel<ArrayList<GuestModel>>>

    @FormUrlEncoded
    @POST("exec")
    fun verification(
        @Field("action") action: String?,
        @Field("id") id: String?
    ): Observable<BasicResponseModel<String?>>
}