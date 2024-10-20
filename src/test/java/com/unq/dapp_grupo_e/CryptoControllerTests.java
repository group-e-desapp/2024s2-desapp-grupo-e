package com.unq.dapp_grupo_e;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.unq.dapp_grupo_e.model.CryptoCurrency;
import com.unq.dapp_grupo_e.model.CryptoCurrencyList;
import com.unq.dapp_grupo_e.model.exceptions.InvalidCurrencyException;
import com.unq.dapp_grupo_e.service.BinanceService;
import com.unq.dapp_grupo_e.service.CryptoCurrencyService;
import com.unq.dapp_grupo_e.utilities.factories.CryptoCurrencyFactory;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test") 
@AutoConfigureMockMvc
@SpringBootTest
public class CryptoControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Mock
    private BinanceService binanceService;
    @MockBean
    private CryptoCurrencyService cryptoCurrencyService;


    @Test
    void consultOfCryptoCotizationReturnsCode200() throws Exception{

        String validCrypto = "NEOUSDT";

        CryptoCurrency cryptoResponse = CryptoCurrencyFactory.createWithSymbolAndPrice(validCrypto, 1.052);

        when(binanceService.getCrypto(validCrypto)).thenReturn(cryptoResponse);

        mockMvc.perform(get("/crypto/price")
                    .param("cryptoSymbol", validCrypto)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
    }

    @Test
    void consultCotizationOfInvalidCryptoReturnsCode404() throws Exception{

        String invalidcrypto = "FakeUSDT";

        when(cryptoCurrencyService.getCryptoValue(invalidcrypto))
            .thenThrow(new InvalidCurrencyException(invalidcrypto + " is not a valid crypto currency"));

        mockMvc.perform(get("/crypto/price")
                    .param("cryptoSymbol", invalidcrypto)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
    }

    @Test
    void consultAllCryptoCotizationsReturnsCode200() throws Exception {
        CryptoCurrency cryptoCAKE = CryptoCurrencyFactory.createWithSymbolAndPrice("CAKEUSDT", 1.812);
        cryptoCAKE.setLastUpdateDateAndTime("17/10/2024 15:00:00");

        CryptoCurrency cryptoAXS = CryptoCurrencyFactory.createWithSymbolAndPrice("AXSUSDT", 1.352);
        cryptoAXS.setLastUpdateDateAndTime("17/10/2024 15:00:00");

        CryptoCurrencyList mockCryptoList = new CryptoCurrencyList();
        mockCryptoList.addCrypto(cryptoCAKE);
        mockCryptoList.addCrypto(cryptoAXS);

        when(cryptoCurrencyService.getAllCryptoValues()).thenReturn(mockCryptoList);

        mockMvc.perform(get("/crypto/allPrices")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) 
                .andExpect(jsonPath("$.cryptos").isArray())
                .andExpect(jsonPath("$.cryptos[0].symbol").value("CAKEUSDT"))
                .andExpect(jsonPath("$.cryptos[0].price").value(1.812))
                .andExpect(jsonPath("$.cryptos[1].symbol").value("AXSUSDT"))
                .andExpect(jsonPath("$.cryptos[1].price").value(1.352));
    }

    
}