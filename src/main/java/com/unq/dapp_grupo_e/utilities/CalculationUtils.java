package com.unq.dapp_grupo_e.utilities;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculationUtils {

    private CalculationUtils() {
        throw new IllegalStateException("Utility class");
      }


    public static double roundProduct(double double1, double double2) {
        BigDecimal result = BigDecimal.valueOf(double1)
                .multiply(BigDecimal.valueOf(double2))
                .setScale(2, RoundingMode.HALF_UP);
        return result.doubleValue();
    }

    public static double roundQuotient(double dividend, double divisor) {
        BigDecimal result = BigDecimal.valueOf(dividend)
                .divide(BigDecimal.valueOf(divisor))
                .setScale(2, RoundingMode.HALF_UP);
        return result.doubleValue();
    }
    
}
