import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//using HERE location platform (Google Maps analog)
//https://platform.here.com/admin/apps/rwHj0vqitGDUUN9F1hTS
public class Finder {

    private static final String GEOCODING_RESOURCE = "https://geocode.search.hereapi.com/v1/geocode";
    public static final String REVERSE_GEOCODING_RESOURCE = "https://revgeocode.search.hereapi.com/v1/revgeocode";
    private static final String API_KEY = "wLdioPNGe6VILOPfX6Je_sL9REZqesRYJy7Nx5H0Mq4";

    public static Location getLocationFromCoordinates(Double latitude, Double longitude) {
        try {
            String geocode = getGeocodeResponseFromCoordinates(latitude, longitude);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode responseJsonNode = mapper.readTree(geocode);
            JsonNode items = responseJsonNode.get("items");

            return getLocationFromJsonNode(items.get(0));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Location> getLocationFromQuery(String query) {
        try {
            String geocode = getGeocodeResponseFromQuery(query);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode responseJsonNode = mapper.readTree(geocode);
            JsonNode items = responseJsonNode.get("items");

            List<Location> resultList = new ArrayList<>();
            items.forEach(item -> resultList.add(getLocationFromJsonNode(item)));
            return resultList;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getGeocodeResponseFromQuery(String query) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);

        String requestUri = GEOCODING_RESOURCE
                + "?apiKey=" + API_KEY
                + "&q=" + encodedQuery
                + "&lang=ru-RUS";

        HttpRequest geocodingRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(requestUri))
                .timeout(Duration.ofMillis(5000))
                .build();

        HttpResponse<String> geocodingResponse = httpClient.send(geocodingRequest, HttpResponse.BodyHandlers.ofString());
        return geocodingResponse.body();
    }

    private static String getGeocodeResponseFromCoordinates(Double latitude, Double longitude) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();

        String requestUri = REVERSE_GEOCODING_RESOURCE
                + "?apiKey=" + API_KEY
                + "&at=" + latitude + "," + longitude
                + "&lang=ru-RUS";

        HttpRequest geocodingRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(requestUri))
                .timeout(Duration.ofMillis(5000))
                .build();

        HttpResponse<String> geocodingResponse = httpClient.send(geocodingRequest, HttpResponse.BodyHandlers.ofString());
        return geocodingResponse.body();
    }

    private static Location getLocationFromJsonNode(JsonNode node) {
        JsonNode address = node.get("address");
        JsonNode position = node.get("position");
        String label = Objects.isNull(address.get("label")) ? "" : address.get("label").asText();
        String countryCode = Objects.isNull(address.get("countryCode")) ? "" : address.get("countryCode").asText();
        String countryName = Objects.isNull(address.get("countryName")) ? "" : address.get("countryName").asText();
        String county = Objects.isNull(address.get("county")) ? "" : address.get("county").asText();
        String city = Objects.isNull(address.get("city")) ? "" : address.get("city").asText();
        String district = Objects.isNull(address.get("district")) ? "" : address.get("district").asText();
        String street = Objects.isNull(address.get("street")) ? "" : address.get("street").asText();
        String houseNumber = Objects.isNull(address.get("houseNumber")) ? "" : address.get("houseNumber").asText();

        return Location.builder()
                .latitude(position.get("lat").asDouble())
                .longitude(position.get("lng").asDouble())
                .countryCode(countryCode)
                .countryName(countryName)
                .county(county)
                .city(city)
                .district(district)
                .street(street)
                .houseNumber(houseNumber)
                .description(label)
                .build();
    }
}

