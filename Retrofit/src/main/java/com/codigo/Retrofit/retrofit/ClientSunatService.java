package com.codigo.Retrofit.retrofit;

import com.codigo.Retrofit.aggregates.response.ResponseSunat;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ClientSunatService {

    @GET("v2/sunat/ruc")
    Call<ResponseSunat> getInfoSunat(@Header("Authorization") String token,
                                      @Query("numero") String numero);
}
