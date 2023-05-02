package com.easy.imc.clienteasyimc.services;

import com.easy.imc.clienteasyimc.entities.IMCResponse;
import com.easy.imc.clienteasyimc.models.API;
import com.easy.imc.clienteasyimc.models.HistoryModel;
import com.easy.imc.clienteasyimc.models.UnitePoidsModel;
import com.easy.imc.clienteasyimc.models.UniteTailleModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UnitePoidsService {

    public static IMCResponse<UnitePoidsModel> getAll(){
        IMCResponse<UnitePoidsModel> result = new IMCResponse<>();
        try {
            URL url = new URL(API.getUrlForGetAllUnitePoids());
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

    public static IMCResponse<UnitePoidsModel> getList(HttpURLConnection connection) throws IOException {
        IMCResponse<UnitePoidsModel> result = new IMCResponse<>();
        int responseCode = connection.getResponseCode();
        if(responseCode == HttpURLConnection.HTTP_OK){
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }

                ObjectMapper objectMapper = new ObjectMapper();
                TypeReference<IMCResponse<UnitePoidsModel>> typeReference = new TypeReference<>() {};
                result = objectMapper.readValue(content.toString(), typeReference);
            }
        }
        return result;
    }

}
