package com.codigo.OpenFeing.service.impl;

import com.codigo.OpenFeing.aggregates.constans.Constants;
import com.codigo.OpenFeing.aggregates.response.ResponseSunat;
import com.codigo.OpenFeing.client.ClientSunat;
import com.codigo.OpenFeing.redis.RedisService;
import com.codigo.OpenFeing.service.EmpresaService;
import com.codigo.OpenFeing.util.Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    private final ClientSunat clientSunat;
    private final RedisService redisService;

    @Value("${token.api}")
    private String token;

    public EmpresaServiceImpl(ClientSunat clientSunat, RedisService redisService) {
        this.clientSunat = clientSunat;
        this.redisService = redisService;
    }

    @Override
    public ResponseSunat getInfoSunat(String ruc) {
        ResponseSunat datosSunat = new ResponseSunat();

        String redisInfo = redisService.getDataFromRedis(Constants.REDIS_KEY_API_SUNAT+ruc);

        if(Objects.nonNull(redisInfo)){
            datosSunat = Util.convertirDesdeString(redisInfo,ResponseSunat.class);
        }else{
            datosSunat = executionSunat(ruc);
            String dataForRedis = Util.convertAString(datosSunat);
            redisService.saveInRedis(Constants.REDIS_KEY_API_SUNAT+ruc,dataForRedis,Constants.REDIS_TTL);
        }
        return datosSunat;
    }

    private ResponseSunat executionSunat(String ruc){
        String tokenOK = Constants.BEARER+token;
        return clientSunat.getEmpresaSunat(ruc,tokenOK);
    }
}

