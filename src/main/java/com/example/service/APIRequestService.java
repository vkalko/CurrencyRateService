package com.example.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Service
public class APIRequestService {

    @Value("${service.api-request.connect-timeout}")
    private int connectTimeout;

    @Value("${service.api-request.read-timeout}")
    private int readTimeout;

    public String doRequest(String url, String method) throws IOException {

        URL apiEndpoint = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) apiEndpoint.openConnection();
        connection.setRequestMethod(method);
        connection.setConnectTimeout(connectTimeout);
        connection.setReadTimeout(readTimeout);

        return readResponse(connection);
    }

    private String readResponse(HttpURLConnection connection) throws IOException {
        int responseCode = connection.getResponseCode();
        BufferedReader reader;
        String response;
        if (responseCode == 200) {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        } else {
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8));
        }
        response = reader.lines().collect(Collectors.joining(System.lineSeparator()));
        reader.close();
        connection.disconnect();
        return response;
    }

}
