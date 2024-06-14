package jktmn.musicapp.musicapp.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListeningHistoryExtractor {

    public List<String> extractListeningHistory(String fileName) {
        List<String> songNames = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode rootNode = objectMapper.readTree(new File(fileName));
            JsonNode itemsNode = rootNode.path("items");

            if (itemsNode.isArray()) {
                for (JsonNode itemNode : itemsNode) {
                    JsonNode trackNode = itemNode.path("track");
                    String songID = trackNode.path("id").asText();
                    songNames.add(songID);
                }
            } else {
                System.out.println("No tracks found in JSON.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return songNames;
    }
}

