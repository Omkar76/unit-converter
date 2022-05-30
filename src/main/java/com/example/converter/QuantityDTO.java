package com.example.converter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class QuantityDTO {
    public String name;
    public String baseUnit;
    public HashMap<String, String> units;

    @Override
    public String toString() {
        return name;
    }

    public static class Unit {
        String name;
        BigDecimal conversionFactor;

        public Unit(Map.Entry<String, String> entry) {
            name = entry.getKey();
            conversionFactor = new BigDecimal(entry.getValue());
        }

        @Override
        public String toString() {
            return name;
        }
    }
}

