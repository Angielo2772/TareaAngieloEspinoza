package com.codigo.Retrofit.retrofit.impl;

import com.codigo.Retrofit.aggregates.constants.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientSunatServiceImpl {
    private static final Logger log = LogManager.getLogger(ClientSunatServiceImpl.class);
    private static String BASE_URL = Constants.BASE_URL;

    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            log.info("ClientSunatServiceImpl -> CREANDO CLIENTE RETROFIT CON URL Y PARAMETROS(SIN VALORES)");
        }
        return retrofit;
    }
}
