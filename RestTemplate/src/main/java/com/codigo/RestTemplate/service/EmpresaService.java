package com.codigo.RestTemplate.service;

import com.codigo.RestTemplate.aggregates.response.ResponseSunat;

public interface EmpresaService {
    ResponseSunat getInfoSunat(String ruc);
}