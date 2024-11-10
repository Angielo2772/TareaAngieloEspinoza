package com.codigo.Retrofit.service.impl;

import com.codigo.Retrofit.aggregates.constants.Constants;
import com.codigo.Retrofit.aggregates.response.ResponseSunat;
import com.codigo.Retrofit.redis.RedisService;
import com.codigo.Retrofit.retrofit.ClientSunatService;
import com.codigo.Retrofit.retrofit.impl.ClientSunatServiceImpl;
import com.codigo.Retrofit.service.EmpresaService;
import com.codigo.Retrofit.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.Objects;

public class EmpresaServiceImpl implements EmpresaService {
    private static final Logger log = LogManager.getLogger(EmpresaServiceImpl.class);
    private final RedisService redisService;

    ClientSunatService sunatServiceRetrofit = ClientSunatServiceImpl
            .getRetrofit()
            .create(ClientSunatService.class);

    @Value("${token.api}")
    private String token;

    public EmpresaServiceImpl(RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public ResponseSunat getInfoSunat(String ruc) throws IOException {
        ResponseSunat datosSunat = new ResponseSunat();

        String redisInfo = redisService.getDataFromRedis(Constants.REDIS_KEY_API_SUNAT + ruc);

        if (Objects.nonNull(redisInfo)) {
            datosSunat = Util.convertirDesdeString(redisInfo, ResponseSunat.class);
        } else {
            Call<ResponseSunat> clienteRetrofit = prepareSunatRetrofit(ruc);
            log.info("getEntity -> Se Preparo todo el cliente Retrofit, listo para ejecutar!");
            Response<ResponseSunat> executeSunat = clienteRetrofit.execute();
            log.info("getEntity -> CLiente Retrofit Ejecutado");

            if (executeSunat.isSuccessful() && Objects.nonNull(executeSunat.body())) {
                datosSunat = executeSunat.body();
            }

            String dataForRedis = Util.convertirAString(datosSunat);
            redisService.saveInRedis(Constants.REDIS_KEY_API_SUNAT+ruc,dataForRedis,Constants.REDIS_TTL);
        }
        return datosSunat;
    }

    private Call<ResponseSunat> prepareSunatRetrofit(String ruc){
        String tokenComplete = Constants.BEARER + token;
        return sunatServiceRetrofit.getInfoSunat(tokenComplete, ruc);
    }
}
