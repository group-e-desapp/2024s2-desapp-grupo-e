package com.unq.dapp_grupo_e.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.unq.dapp_grupo_e.dto.DolarApiResponseDTO;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DolarApiService {

    private final RestTemplate restTemplate;

    public DolarApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Double getDolarCotization() {
        String consultURL = "https://dolarapi.com/v1/dolares/oficial";
        DolarApiResponseDTO response = restTemplate.getForObject(consultURL, DolarApiResponseDTO.class);
        if (response != null) {
            return response.getSale();
        } else {
            throw new EntityNotFoundException("An unexpected error has ocurred from the query side, please try later");
        }
    }
    
}
