package com.codigo.RestTemplate.service.impl;

import com.codigo.RestTemplate.Util.Util;
import com.codigo.RestTemplate.aggregates.constants.Constants;
import com.codigo.RestTemplate.aggregates.response.ResponseSunat;
import com.codigo.RestTemplate.redis.RedisService;
import com.codigo.RestTemplate.service.EmpresaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

public class EmpresaServiceImpl implements EmpresaService {

    private final RestTemplate restTemplate;
    private final RedisService redisService;
    @Value("${token.api}")
    private String token;

    public EmpresaServiceImpl(RestTemplate restTemplate, RedisService redisService) {
        this.restTemplate = restTemplate;
        this.redisService = redisService;
    }

    @Override
    public ResponseSunat getInfoSunat(String dni) {
        ResponseSunat responseSunat = new ResponseSunat();
        ResponseSunat datosReniec = new ResponseSunat();

        String redisInfo = redisService.getDataFromRedis(Constants.REDIS_KEY_API_SUNAT + dni);

        if (Objects.nonNull(redisInfo)) {
            datosReniec = Util.convertirDesdeString(redisInfo, ResponseSunat.class);
            return datosReniec;
        } else {
            datosReniec = executeRestTemplate(dni);
            String dataForRedis = Util.convertAString(datosReniec);
            redisService.saveInRedis(Constants.REDIS_KEY_API_SUNAT + dni, dataForRedis, Constants.REDIS_TTL);
            return datosReniec;
        }
    }

    private ResponseSunat executeRestTemplate(String dni) {

        String url = "https://api.apis.net.pe/v2/reniec/dni?numero=" + dni;

        ResponseEntity<ResponseSunat> executeRestTemplate = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(createHeaders()),
                ResponseSunat.class
        );

        if(executeRestTemplate.getStatusCode().equals(HttpStatus.OK)){
            return executeRestTemplate.getBody();
        } else {
            return null;
        }
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","Bearer " + token);
        return headers;
    }
}
