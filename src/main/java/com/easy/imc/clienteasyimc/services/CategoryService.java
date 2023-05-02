package com.easy.imc.clienteasyimc.services;

import com.easy.imc.clienteasyimc.entities.IMCResponse;
import com.easy.imc.clienteasyimc.entities.User;
import com.easy.imc.clienteasyimc.models.*;
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

public class CategoryService {
    public static IMCResponse<CategoryModel> getAll(){
        IMCResponse<CategoryModel> result = new IMCResponse<>();
        try {
            URL url = new URL(API.getUrlForGetAllCategories());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(API.get);

            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setDoOutput(true);

            result = getList(connection);

            connection.disconnect();
        }catch (Exception e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ":" +e.getMessage());
        }
        return result;
    }

    public static IMCResponse<CategoryCountModel> getAllCounts(User user){

        IMCResponse<CategoryCountModel> result = new IMCResponse<>();
        try {
            URL url = new URL(API.getUrlForAllCategoriesCounts());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(API.post);

            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            connection.setDoOutput(true);

            String requestBody = user.toString();

            try (OutputStream outputStream = connection.getOutputStream()) {
                byte[] requestBodyBytes = requestBody.getBytes(StandardCharsets.UTF_8);
                outputStream.write(requestBodyBytes);
            }
            result = getCountList(connection);
            connection.disconnect();

        } catch (Exception e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ":" +e.getMessage());
        }
        return result;
    }


    public static IMCResponse<CategoryModel> getList(HttpURLConnection connection) throws IOException {
        IMCResponse<CategoryModel> result = new IMCResponse<>();
        int responseCode = connection.getResponseCode();
        if(responseCode == HttpURLConnection.HTTP_OK){
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }

                ObjectMapper objectMapper = new ObjectMapper();
                TypeReference<IMCResponse<CategoryModel>> typeReference = new TypeReference<>() {};
                result = objectMapper.readValue(content.toString(), typeReference);
            }
        }
        return result;
    }

    public static IMCResponse<CategoryCountModel> getCountList(HttpURLConnection connection) throws IOException {
        IMCResponse<CategoryCountModel> result = new IMCResponse<>();
        int responseCode = connection.getResponseCode();
        if(responseCode == HttpURLConnection.HTTP_OK){
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }

                ObjectMapper objectMapper = new ObjectMapper();
                TypeReference<IMCResponse<CategoryCountModel>> typeReference = new TypeReference<>() {};
                result = objectMapper.readValue(content.toString(), typeReference);
            }
        }
        return result;
    }

}
