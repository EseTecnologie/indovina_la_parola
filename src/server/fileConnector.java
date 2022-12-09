package server;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class fileConnector {
    private static ArrayList<String> fileLines = new ArrayList<>();
    public static ArrayList<String> readFile(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line = reader.readLine();
        while (line != null) {
            fileLines.add(line.substring(0, line.length()-1));
            line = reader.readLine();
        }
        return fileLines;
    }
    
    public static String getRandomLineFromFile (String fileName) throws IOException {
        readFile(fileName);
        Random rnd = new Random();
        return fileLines.get(rnd.nextInt(fileLines.size()));
    }
}
