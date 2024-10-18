package com.unq.dapp_grupo_e.model;

import java.util.ArrayList;
import java.util.List;

import com.unq.dapp_grupo_e.utilities.CurrentDateAndTime;

public class CryptoVolume {

    private String dateTimeRequest = CurrentDateAndTime.getNewDateAsString();

    private Double totalOperationUSD;

    private Double totalOperationARS;

    private List<CryptoActive> cryptoActives = new ArrayList<>();


    public void addCryptoActive(CryptoActive cryptoActive) {
        cryptoActives.add(cryptoActive);
    }

    public void calculateTotalInARS() {
        this.totalOperationARS = cryptoActives.stream().mapToDouble(cvalue -> cvalue.getTotalCotizationARS()).sum();
    }

    public void calculateTotalInUSD(Double cotizationARS) {
        Double totalInArs = cryptoActives.stream().mapToDouble(cvalue -> cvalue.getTotalCotizationARS()).sum();
        this.totalOperationUSD = totalInArs / cotizationARS; 
    }

    public String getDateTimeRequest() {
        return dateTimeRequest;
    }

    public Double getTotalOperationUSD() {
        return totalOperationUSD;
    }

    public Double getTotalOperationARS() {
        return totalOperationARS;
    }

    public List<CryptoActive> getCryptoActives() {
        return cryptoActives;
    }
    
}
