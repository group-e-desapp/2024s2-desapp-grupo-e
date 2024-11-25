package com.unq.dapp_grupo_e.model.cryptoCotizationsBody;

import java.util.ArrayList;
import java.util.List;

public class CryptoFormCotization {

    private String cryptoSymbol;
    private List<DateTimeCotization> listCotizations;

    public CryptoFormCotization(String cryptoSymbol) {
        this.cryptoSymbol = cryptoSymbol;
        this.listCotizations = new ArrayList<>();
    }

    public String getCryptoSymbol() {
        return cryptoSymbol;
    }

    public List<DateTimeCotization> getListCotizations() {
        return listCotizations;
    }

    public void addDateTimeCotization(DateTimeCotization cryptoCotization) {
        listCotizations.add(cryptoCotization);
    }
    
}
