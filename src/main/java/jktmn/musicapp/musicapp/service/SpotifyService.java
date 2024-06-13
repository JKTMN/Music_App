package jktmn.musicapp.musicapp.service;

import org.springframework.stereotype.Service;
import jktmn.musicapp.musicapp.utils.SpotifyAuthorization;
import jktmn.musicapp.musicapp.utils.SpotifyListeningHistoryFetcher;
import jktmn.musicapp.musicapp.utils.SpotifyDataExporter;

@Service
public class SpotifyService {

    private final SpotifyAuthorization spotifyAuthorization;
    private final SpotifyListeningHistoryFetcher spotifyListeningHistoryFetcher;
    private final SpotifyDataExporter spotifyDataExporter;

    public SpotifyService() {
        this.spotifyAuthorization = new SpotifyAuthorization();
        this.spotifyListeningHistoryFetcher = new SpotifyListeningHistoryFetcher();
        this.spotifyDataExporter = new SpotifyDataExporter();
    }

    public String getAuthorizationUrl() {
        return spotifyAuthorization.getAuthorizationUrl();
    }

    public void fetchAndExportListeningHistory(String authorizationCode, String fileName) {
        String accessToken = spotifyAuthorization.getAccessToken(authorizationCode);

        if (accessToken != null) {
            String listeningHistory = spotifyListeningHistoryFetcher.fetchListeningHistory(accessToken);
            if (listeningHistory != null) {
                spotifyDataExporter.exportDataToFile(listeningHistory, fileName);
            } else {
                System.err.println("Failed to fetch listening history");
            }
        } else {
            System.err.println("Failed to retrieve access token");
        }
    }
}
