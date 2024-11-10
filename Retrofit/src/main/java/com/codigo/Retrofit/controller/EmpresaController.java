package com.codigo.Retrofit.controller;

import com.codigo.Retrofit.aggregates.response.ResponseSunat;
import com.codigo.Retrofit.service.EmpresaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/v1/empresa")
public class EmpresaController {
    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @GetMapping("/sunat/{ruc}")
    public ResponseEntity<ResponseSunat> getInfoSunat(@PathVariable String ruc) throws IOException {
        return new ResponseEntity<>(empresaService.getInfoSunat(ruc), HttpStatus.OK);
    }
}
