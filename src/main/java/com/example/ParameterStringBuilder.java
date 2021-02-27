package com.example;

import java.util.Map;

public class ParameterStringBuilder {
    public static String getParamsString(Map<String, String> params) {
        StringBuilder result = new StringBuilder();

        params.forEach((key, value) -> result.append(String.format("%s=%s&", key, value)));

        return String.format("?%sjson", result.toString());
    }
}
