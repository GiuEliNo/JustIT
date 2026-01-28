package it.dosti.justit.api;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

public class NominatimService {

    //query esempio = "https://nominatim.openstreetmap.org/search?q=Piazza+Duomo+Milano&format=json&addressdetails=1&limit=1";

    private final String NOMINATIM_BASE = "https://nominatim.openstreetmap.org/search?";
    private final HttpClient client = HttpClient.newHttpClient();


    public CompletableFuture<String> searchAddress(String query) {
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String urlString = String.format(NOMINATIM_BASE + "?q=%s&format=json&limit=1", encodedQuery);

        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(urlString)).header("User-Agent", "UniversityProject-JustIT/1.0 (giuelinomail@gmail.com)").GET().build();
        return client.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body);
    }
}
