package jktmn.musicapp.musicapp;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import jktmn.musicapp.musicapp.service.SpotifyService;
import jktmn.musicapp.musicapp.utils.ListeningHistoryExtractor;
import jktmn.musicapp.musicapp.utils.DataDumper;


import java.util.Scanner;

@SpringBootApplication
public class MusicappApplication implements CommandLineRunner {

    @Autowired
    private SpotifyService spotifyService;

    public static void main(String[] args) {
        SpringApplication.run(MusicappApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String authorizationUrl = spotifyService.getAuthorizationUrl();
        System.out.println("Open the following URL in a browser to authorize the application:");
        System.out.println(authorizationUrl);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the authorization code: ");
        String authorizationCode = scanner.nextLine();

        String fileName = "listeningHistory.json";
        spotifyService.fetchAndExportListeningHistory(authorizationCode, fileName);
        scanner.close();



        ListeningHistoryExtractor extractor = new ListeningHistoryExtractor();
        List<String> listeningData = extractor.extractListeningHistory(fileName);

        DataDumper dumper = new DataDumper();
        dumper.dumpExtractedData(listeningData, "listeningData.json" );
        
    }
}
