package com.skripsi.adeahmadfaisal.apihelper;


import android.renderscript.Sampler;

import com.skripsi.adeahmadfaisal.model.ResponseBooking;
import com.skripsi.adeahmadfaisal.model.Userprofile.ResponseUser;
import com.skripsi.adeahmadfaisal.model.allcar.ResponseAllCar;
import com.skripsi.adeahmadfaisal.model.getmobilcustomer.ResponseMobil;
import com.skripsi.adeahmadfaisal.model.mycar.ResponseMycar;
import com.skripsi.adeahmadfaisal.model.pilihMobil.ResponsePilihmobil;
import com.skripsi.adeahmadfaisal.model.sparepart.ResponseSparePart;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BaseApiService {

    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseBody> loginRequest(@Field("email") String email,
                                    @Field("password") String password);

    @FormUrlEncoded
    @POST("Registrasimycar.php")
    Call<ResponseBody> registMycar ( @Field("car_id") String car_id,
                                     @Field("cutomer_id") String cutomer_id,
                                     @Field("no_plat") String no_plat,
                                     @Field("no_body") String no_body,
                                     @Field("no_machine") String no_machine);

    @FormUrlEncoded
    @POST("registrasi.php")
    Call<ResponseBody> registRequest(@Field("name") String name,
                                     @Field("email") String email,
                                     @Field("password") String password,
                                     @Field("phone") String phone);

    @FormUrlEncoded
    @POST("RegistrasiBookingService.php")
    Call<ResponseBody> registBooking(@Field("customer_id") String customer_id,
                                     @Field("mycar_id") String mycar_id,
                                     @Field("date") String date,
                                     @Field("hour") String hour,
                                     @Field("type_service") String type_service,
                                     @Field("problem") String problem,
                                     @Field("status") String status);
    @FormUrlEncoded
    @POST("editbookingservice.php")
    Call<ResponseBody> registEditBooking(@Field("id") String id,
                                     @Field("date") String date,
                                     @Field("hour") String hour,
                                     @Field("type_service") String type_service,
                                     @Field("problem") String problem);

    @FormUrlEncoded
    @POST("edituserprofil.php")
    Call<ResponseBody> registUserProfile(@Field("customer_id") String customer_id,
                                         @Field("name") String name,
                                         @Field("email") String email,
                                         @Field("password") String password,
                                         @Field("gender") String gender,
                                         @Field("phone") String phone,
                                         @Field("addres") String addres,
                                         @Field("birthday") String birthday);

    @FormUrlEncoded
    @POST("editmycar.php")
    Call<ResponseBody> registEditMycar(@Field("mycar_id") String id,
                                       @Field("no_plat") String date,
                                       @Field("no_body") String hour,
                                       @Field("no_machine") String type_service);

    @FormUrlEncoded
    @POST("resetpassword.php")
    Call<ResponseBody> resetpassword(@Field("email") String email,
                                     @Field("password") String password);



    @FormUrlEncoded
    @POST("deletebooking.php")
    Call<ResponseBody>deletebookingRequest(@Field("booking_id") String booking_id);

    @FormUrlEncoded
    @POST("deletemycar.php")
    Call<ResponseBody>deletemycarRequest(@Field("mycar_id") String mycar_id);


    @GET("getbookingservice.php")
    Call<ResponseBooking> getBooking(@Query("customer_id") String id);

    @GET("ListCarUser.php")
    Call<ResponsePilihmobil> getPilihmobil(@Query("cutomer_id") String id);

    @GET("getmobiluser.php")
    Call<ResponseMobil> getMycar(@Query("cutomer_id") String id);

    @GET("getmycar.php")
    Call<ResponseMycar> getAcmycar(@Query("cutomer_id") String id);

    @GET("getUserProfile.php")
    Call<ResponseUser> getUserprofile(@Query("customer_id") String customer_id);

    @GET("Allcar.php")
    Call<ResponseAllCar> getAllcar();

    @GET("getsparepart.php")
    Call<ResponseSparePart> getSparepart();

    @FormUrlEncoded
    @POST("SearchSparepart.php")
    Call<ResponseSparePart> search(@Field("name_sparepart") String name_sparepart);


}
