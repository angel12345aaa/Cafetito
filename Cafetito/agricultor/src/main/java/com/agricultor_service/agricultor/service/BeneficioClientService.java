package com.agricultor_service.agricultor.service;

import com.agricultor_service.agricultor.dto.CuentaBeneficioRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BeneficioClientService {

    private final RestTemplate restTemplate;

    public BeneficioClientService() {
        this.restTemplate = new RestTemplate();
    }

    public void crearCuentaEnBeneficio(CuentaBeneficioRequest request) {

        String url = "http://beneficio-service:8083/api/cuentas/interno";

        restTemplate.postForObject(
                url,
                request,
                Object.class
        );
    }
}