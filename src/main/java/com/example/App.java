package com.example;

import com.example.service.CurrencyRateService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class App {
    public static void main(String[] args) throws IOException {
        ApplicationContext context = SpringApplication.run(App.class, args);
        CurrencyRateService currencyRateService = context.getBean(CurrencyRateService.class);

        String response = currencyRateService.getCurrencyRate("EUR", "20210227");
        System.out.println(response);
    }
}
