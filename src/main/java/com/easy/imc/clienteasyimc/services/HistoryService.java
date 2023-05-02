package com.easy.imc.clienteasyimc.services;

import com.easy.imc.clienteasyimc.entities.History;
import com.easy.imc.clienteasyimc.entities.IMC;
import com.easy.imc.clienteasyimc.entities.IMCResponse;
import com.easy.imc.clienteasyimc.entities.User;
import com.easy.imc.clienteasyimc.models.API;
import com.easy.imc.clienteasyimc.models.HistoryModel;
import com.easy.imc.clienteasyimc.models.UnitePoidsModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HistoryService {
    public static IMCResponse<HistoryModel> getAll(User user, boolean recentlyAdded, int limit){

        IMCResponse<HistoryModel> result = new IMCResponse<>();
        try {
            URL url = new URL(API.getUrlForAllHistories(recentlyAdded, limit));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(API.post);

            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            connection.setDoOutput(true);

            String requestBody = user.toString();

            try (OutputStream outputStream = connection.getOutputStream()) {
                byte[] requestBodyBytes = requestBody.getBytes(StandardCharsets.UTF_8);
                outputStream.write(requestBodyBytes);
            }
            result = getList(connection);
            connection.disconnect();

        } catch (Exception e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ":" +e.getMessage());
        }
        return result;
    }

    public static IMCResponse<HistoryModel> getHistoryByMultiCriteria(History history){

        IMCResponse<HistoryModel> result = new IMCResponse<>();
        try {
            URL url = new URL(API.getUrlForHistoryByMultiCriteria());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(API.post);

            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            connection.setDoOutput(true);

            String requestBody = history.toString();

            try (OutputStream outputStream = connection.getOutputStream()) {
                byte[] requestBodyBytes = requestBody.getBytes(StandardCharsets.UTF_8);
                outputStream.write(requestBodyBytes);
            }
            result = getList(connection);
            connection.disconnect();

        } catch (Exception e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ":" +e.getMessage());
        }
        return result;
    }

    public static IMCResponse<HistoryModel> getList(HttpURLConnection connection) throws IOException {
        IMCResponse<HistoryModel> result = new IMCResponse<>();
        int responseCode = connection.getResponseCode();
        if(responseCode == HttpURLConnection.HTTP_OK){
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }

                ObjectMapper objectMapper = new ObjectMapper();
                TypeReference<IMCResponse<HistoryModel>> typeReference = new TypeReference<>() {};
                result = objectMapper.readValue(content.toString(), typeReference);
            }
        }
        return result;
    }

}
