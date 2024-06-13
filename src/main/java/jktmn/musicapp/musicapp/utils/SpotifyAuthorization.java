package jktmn.musicapp.musicapp.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SpotifyAuthorization {

    private static final String clientId = "xxx";
    private static final String clientSecret = "xxx";
    private static final String redirectUri = "http://localhost:8888/callback";
    private static final String authURL = "https://accounts.spotify.com/api/token";

    public String getAuthorizationUrl() {
        return "https://accounts.spotify.com/authorize?" +
               "client_id=" + clientId +
               "&response_type=code" +
               "&redirect_uri=" + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8) +
               "&scope=" + URLEncoder.encode("user-read-recently-played", StandardCharsets.UTF_8);
    }

    public String getAccessToken(String authorizationCode) {
        String credentials = clientId + ":" + clientSecret;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(authURL))
                .header("Authorization", "Basic " + encodedCredentials)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=authorization_code&code=" + authorizationCode + "&redirect_uri=" + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8)))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
                return jsonObject.get("access_token").getAsString();
            } else {
                System.err.println("Error fetching access token: " + response.statusCode());
                System.err.println("Response body: " + response.body());
                return null;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
