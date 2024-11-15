package com.codigo.OpenFeing.client;

import com.codigo.OpenFeing.aggregates.response.ResponseSunat;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "clientSunat", url = "https://api.apis.net.pe/v2/sunat")
public interface ClientSunat {

    @GetMapping("/ruc")
    ResponseSunat getEmpresaSunat(@RequestParam("numero") String numero,
                                  @RequestHeader("Authorization") String authorization);
}
