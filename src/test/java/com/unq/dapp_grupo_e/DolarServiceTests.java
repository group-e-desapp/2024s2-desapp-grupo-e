package com.unq.dapp_grupo_e;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import com.unq.dapp_grupo_e.dto.DolarApiResponseDTO;
import com.unq.dapp_grupo_e.service.DolarApiService;

import jakarta.persistence.EntityNotFoundException;

@ActiveProfiles("test") 
@SpringBootTest
class DolarServiceTests {


    @Mock
    private RestTemplate restTemplate;
    private DolarApiService dolarApiService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        dolarApiService = new DolarApiService(
            restTemplate
        );
        
    }

    @Test
    void getCurrentCotizationOfDolar() {

        DolarApiResponseDTO dolarCotzationMock = new DolarApiResponseDTO("USD", 989.52);
        String consultURL = "https://dolarapi.com/v1/dolares/oficial";

        when(restTemplate.getForObject(consultURL,DolarApiResponseDTO.class)).thenReturn(dolarCotzationMock);

        Assertions.assertEquals(989.52, dolarApiService.getDolarCotization());
    }

    @Test
    void exceptionForDolarApiServiceFailure() {

        String consultURL = "https://dolarapi.com/v1/dolares/oficial";

        when(restTemplate.getForObject(consultURL,DolarApiResponseDTO.class)).thenReturn(null);

        Assertions.assertThrows(EntityNotFoundException.class, () -> dolarApiService.getDolarCotization());
    }
    
}
