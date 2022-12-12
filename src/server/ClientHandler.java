package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.in;

public class ClientHandler implements Runnable {

    public String name;
    public int tentative;
    private boolean isLosggedIn;
    private boolean win;
    private DataInputStream input;
    private DataOutputStream output;
    private RankingManager rankingManager;
    private final Socket socket;
    private final Scanner scan;
    private String wordToFind;
    private final List<ClientHandler> clients;

    public ClientHandler(Socket socket, String name, List<ClientHandler> clients, String wordToFind) {
        this.socket = socket;
        scan = new Scanner(in);
        this.name = name;
        isLosggedIn = true;
        this.clients = clients;
        this.wordToFind = wordToFind;
        this.tentative = 0;
        this.win = false;
        this.rankingManager = new RankingManager();

        try {
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());

        } catch (IOException ex) {
            log("ClientHander : " + ex.getMessage());
        }
    }

    @Override
    public void run() {
        String received;
        write(output, "Your name : " + name);

        System.out.println("Online players:");
        for (ClientHandler client : clients
        ) {
            if (client.isLosggedIn) {
                log("Player " + client.name);

            } else {
                log("Player " + client.name + " is inactive");

            }
        }

        while (true) {
            received = read();
            if(received.trim().charAt(0) == Constants.PREFIX) {
                if (received.trim().substring(1).equalsIgnoreCase(Constants.LOGOUT)) {
                    this.isLosggedIn = false;
                    log("Client " + name + " logged out");
                    closeSocket();
                    closeStreams();
                    break;
                }
                if(received.trim().substring(1).equalsIgnoreCase(Constants.NEW_GAME)){
                    try {
                        this.wordToFind = fileConnector.getRandomLineFromFile(Constants.WORDS_FILE_PATH);
                        this.win = false;
                        write(output, "Parola da trovare --> ;" + FindWordManager.convertToAsterisks(wordToFind, wordToFind.substring(0,1)));
                    } catch (IOException e) {
                        log("Non è stato possibile trovare una parola");
                        log("Closing...");
                        throw new RuntimeException(e);
                    }
                    continue;
                }
                if(received.trim().substring(1).equalsIgnoreCase(Constants.CLASSIFICA)){
                    write(output,rankingManager.getRankingInString());
                    continue;
                }
                if (received.trim().substring(1,Constants.USERNAME.length()+1).equalsIgnoreCase(Constants.USERNAME)) {
                    String oldUsername = this.name;
                    this.name = received.substring((Constants.PREFIX + Constants.USERNAME).length());
                    log("User name " + oldUsername + " changed in " + this.name);
                    continue;
                }
            }

            try {
            forwardToClient(received);
            } catch (Exception e) {
                log("Client " + name + " logged out");
                isLosggedIn = false;
                return;
            }
        }
        closeStreams();
    }

    private void
    forwardToClient(String received) {
        log(name + " --> " + received);
        if (!this.win) {
            String convertedToAsterix = FindWordManager.convertToAsterisks(wordToFind, received);
            if (FindWordManager.checkWord(wordToFind, convertedToAsterix)) {
                log("Client " + name + " win");
                write(output, "win;" + convertedToAsterix + ";" + tentative);
                rankingManager.addToRanking(name, tentative);
                this.win = true;
                return;
            }
            tentative++;
            write(output, "lose;" + convertedToAsterix + ";" + tentative);
        }
    }

    private String read() {
        String line = "";
        try {
            line = input.readUTF();
        } catch (IOException ex) {
            if (ex.getMessage().equals("Broken pipe")) {
                isLosggedIn = false;
                log("Client " + name + " logged out");
                closeSocket();
                closeStreams();
                return "";
            }
            log("read : " + ex.getMessage());
        }
        return line;
    }

    private void write(DataOutputStream output, String message) {
        try {
            output.writeUTF(message);
        } catch (IOException ex) {
            if (ex.getMessage().equals("Broken pipe")) {
                log("Client " + name + " logged out");
                isLosggedIn = false;
                return;
            }
            log("write : " + ex.getMessage());
        }
    }

    private void closeStreams() {
        this.isLosggedIn = false;
        try {
            this.input.close();
            this.output.close();
        } catch (IOException ex) {
            log("closeStreams : " + ex.getMessage());
        }
    }

    private void closeSocket() {
        this.isLosggedIn = false;
        try {
            socket.close();
        } catch (IOException ex) {
            log("closeSocket : " + ex.getMessage());
        }
    }
    private void log(String msg) {
        System.out.println(msg);
    }
}

class FindWordManager {
    public static boolean checkWord(String wordToFind, String userWord) {
        return wordToFind.equals(userWord);
    }

    public static String convertToAsterisks(String wordToFind, String userWord) {
        wordToFind = wordToFind.trim();
        userWord = userWord.trim();
        StringBuilder toReturn = new StringBuilder();
        for (int i = 0; i < wordToFind.length(); i++) {
            try {
                if (wordToFind.charAt(i) == userWord.charAt(i)) toReturn.append(wordToFind.charAt(i));
                else toReturn.append("*");
            } catch (StringIndexOutOfBoundsException ex) {
                toReturn.append('*');
            }
        }
        return toReturn.toString();
    }
}

class RankingManager{
    private ArrayList<String> ranking;

    public RankingManager() {
        this.ranking = new ArrayList<String>();;
    }
    public void addToRanking(String user, int score) {
        this.ranking.add(score+";"+user);
        //this.ranking.sort(score, user);
    }

    public ArrayList<String> getRanking() {
        return ranking;
    }
    public String getRankingInString(){
        String toReturn = "";
        for (String s : ranking){
            toReturn += s+"\n";
        }
        return toReturn;
    }

/*    static void bubbleSort(int[] arr){
        int n = arr.length;
        int temp = 0;
        for(int i=0; i < n; i++){  
                 for(int j=1; j < (n-i); j++){  
                          if(arr[j-1] > arr[j]){  
                                 //swap elements  
                                 temp = arr[j-1];  
                                 arr[j-1] = arr[j];  
                                 arr[j] = temp;  
                         }  
                          
                 }  
}*/
}