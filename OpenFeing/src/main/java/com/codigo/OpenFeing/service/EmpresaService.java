package com.codigo.OpenFeing.service;

import com.codigo.OpenFeing.aggregates.response.ResponseSunat;

public interface EmpresaService {
    ResponseSunat getInfoSunat(String ruc);
}
