package com.example.service;

import com.example.ParameterStringBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class CurrencyRateService {

    private final APIRequestService apiRequestService;

    public CurrencyRateService(APIRequestService apiRequestService) {
        this.apiRequestService = apiRequestService;
    }

    public String getCurrencyRate(String currency, String date) throws IOException {
        Map<String, String> params = Map.of("valcode", currency, "date", date);

        //Using unresolvable domain to remonstrate error handling and retry logic
        String url = "https://bank444.gov.ua/NBUStatService/v1/statdirectory/exchange"
                + ParameterStringBuilder.getParamsString(params);

        return apiRequestService.doRequest(url, "GET");
    }
}
