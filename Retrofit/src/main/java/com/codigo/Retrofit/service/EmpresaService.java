package com.codigo.Retrofit.service;

import com.codigo.Retrofit.aggregates.response.ResponseSunat;

import java.io.IOException;

public interface EmpresaService {
    ResponseSunat getInfoSunat(String ruc) throws IOException;
}
