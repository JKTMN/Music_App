/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package music_app;

import java.io.BufferedWriter;
import java.io.FileWriter;
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
import java.util.Scanner;


public class SpotifyListeningHistory {

    private static final String clientId = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
    private static final String clientSecret = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
    private static final String redirectUri = "http://localhost:8888/callback";
    private static final String authURL = "https://accounts.spotify.com/api/token";
    private static final String listeningHistoryURL = "https://api.spotify.com/v1/me/player/recently-played";
    private static final String scopes = "user-read-recently-played";

    public static void main(String[] args) {
        String authorizationUrl = "https://accounts.spotify.com/authorize?" +
                "client_id=" + clientId +
                "&response_type=code" +
                "&redirect_uri=" + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8) +
                "&scope=" + URLEncoder.encode(scopes, StandardCharsets.UTF_8);

        System.out.println("Open the following URL in a browser to authorize the application:");
        System.out.println(authorizationUrl);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the authorization code: ");
        String authorizationCode = scanner.nextLine();

        String accessToken = getAccessToken(authorizationCode);
        if (accessToken != null) {
            String listeningHistory = getListeningHistory(accessToken);
            if (listeningHistory != null) {
                dumpToFile(listeningHistory, "listeningHistory.json");
            } else {
                System.err.println("Failed to fetch listening history");
            }
        } else {
            System.err.println("Failed to retrieve access token");
        }
        scanner.close();
    }

    public static String getAccessToken(String authorizationCode) {
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

    public static String getListeningHistory(String accessToken) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(listeningHistoryURL))
                .header("Authorization", "Bearer " + accessToken)
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return response.body();
            } else {
                System.err.println("Error fetching listening history: " + response.statusCode());
                return null;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void dumpToFile(String data, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(data);
            System.out.println("Listening history dumped to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}