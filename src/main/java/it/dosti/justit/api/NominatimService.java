package it.dosti.justit.api;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

public class NominatimService {

    private static final String NOMINATIM = "https://nominatim.openstreetmap.org/search";
    private final HttpClient client = HttpClient.newHttpClient();


    public CompletableFuture<String> searchAddress(String query) {
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String urlString = String.format("%s?q=%s&format=json&limit=1",NOMINATIM, encodedQuery);


        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(urlString)).header("User-Agent", "UniversityProject-JustIT/1.0 (giuelinomail@gmail.com)").GET().build();
        return client.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body);
    }


}
