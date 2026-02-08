package it.dosti.justit.dao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.dosti.justit.api.NominatimService;
import it.dosti.justit.model.Coordinates;
import it.dosti.justit.utils.JustItLogger;

import java.util.concurrent.CompletableFuture;

public class CoordinatesDAOAPI implements CoordinatesDAO {

    public CompletableFuture<Coordinates> getCoordinates(String address) {
        NominatimService nominatimService = new NominatimService();
        return nominatimService.searchAddress(address).thenApply(jsonResponse -> {
            JustItLogger.getInstance().info("JSON Ricevuto: " + jsonResponse);

            ObjectMapper objectMapper = new ObjectMapper();

            try {
                JsonNode root = objectMapper.readTree(jsonResponse);
                if(root.isArray() && !root.isEmpty()) {
                    JsonNode firstResult = root.get(0);
                    double latitude = firstResult.get("lat").asDouble();
                    double longitude = firstResult.get("lon").asDouble();

                    JustItLogger.getInstance().info("Latitude : " + latitude);
                    JustItLogger.getInstance().info("Longitude : " + longitude);
                    return new Coordinates(latitude, longitude);
                }
            }catch(Exception e) {

                JustItLogger.getInstance().error("Error get coord JSON", e);

            }
            JustItLogger.getInstance().error("JSON empty, maybe address error");
            return null;
        });
    }
}
