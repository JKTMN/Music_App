package jktmn.musicapp.musicapp.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SpotifyDataExporter {

    public void exportDataToFile(String data, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(data);
            System.out.println("Listening history dumped to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
