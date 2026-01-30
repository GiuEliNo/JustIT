package it.dosti.justit.DAO;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.dosti.justit.api.NominatimService;
import it.dosti.justit.model.Coordinates;

import java.util.concurrent.CompletableFuture;

public class CoordinatesDAOAPI implements CoordinatesDAO {

    public CompletableFuture<Coordinates> getCoordinates(String address) {
        NominatimService nominatimService = new NominatimService();
        return nominatimService.searchAddress(address).thenApply(jsonResponse -> {
            System.out.println("JSON Ricevuto: " + jsonResponse);

            ObjectMapper objectMapper = new ObjectMapper();

            try {
                JsonNode root = objectMapper.readTree(jsonResponse);
                if(root.isArray() && !root.isEmpty()) {
                    JsonNode firstResult = root.get(0);
                    double latitude = firstResult.get("lat").asDouble();
                    double longitude = firstResult.get("lon").asDouble();

                    System.out.println("Latitude : " + latitude);
                    System.out.println("Longitude : " + longitude);
                    return new Coordinates(latitude, longitude);
                }
            }catch(Exception e) {

                e.printStackTrace();

            }
            System.out.println("JSON ricevuto vuoto, Indirizzo sbagliato?");
            return null;
        });
    }
}
