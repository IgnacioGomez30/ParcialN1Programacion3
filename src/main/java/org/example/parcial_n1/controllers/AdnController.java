package org.example.parcial_n1.controllers;

import org.example.parcial_n1.dto.AdnRequest;
import org.example.parcial_n1.dto.AdnResponse;
import org.example.parcial_n1.services.AdnService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mutant")
public class AdnController {

    private final AdnService adnService;

    public AdnController(AdnService adnService) {
        this.adnService = adnService;
    }

    @PostMapping
    public ResponseEntity<AdnResponse> checkMutant(@RequestBody AdnRequest adnRequest) {
        // Aquí puedes hacer validaciones básicas antes de llamar al servicio
        if (adnRequest.getAdn() == null || adnRequest.getAdn().length == 0) {
            // Si el ADN está vacío o es nulo, retornamos un error.
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AdnResponse(false));
        }

        // Llamada al servicio para analizar el ADN
        boolean isMutant = adnService.analyzeAdn(adnRequest.getAdn());
        AdnResponse adnResponse = new AdnResponse(isMutant);

        // Retornamos el estado 200 si es mutante o 403 si no lo es
        if (isMutant) {
            return ResponseEntity.ok(adnResponse);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(adnResponse);
        }
    }
}

