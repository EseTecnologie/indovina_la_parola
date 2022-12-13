package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static java.rmi.server.LogStream.log;

public class Server {
    static List<ClientHandler> clients;
    ServerSocket serverSocket;
    static int numOfUsers = 0;
    Socket socket;
    String wordToFind;
    RankingManager rankingManager;

    public Server() {
        clients = new ArrayList<>();
        rankingManager = new RankingManager();
        try {
            serverSocket = new ServerSocket(Constants.PORT);
        } catch (IOException ex) {
            log("Server : " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.watiConnection();
    }

    private void watiConnection() {
        log("Server Running...");
        while (true) {
            try {
                socket = serverSocket.accept();
            } catch (IOException ex) {
                log("waitConnection : " + ex.getMessage());
            }

            log("Client accepted : " + socket.getInetAddress());
            numOfUsers++;


            try {
                wordToFind = fileConnector.getRandomLineFromFile(Constants.WORDS_FILE_PATH);
            } catch (IOException e) {
                log("Non è stato possibile trovare una parola");
                log("Closing...");
                throw new RuntimeException(e);
            }
            log("Client" + numOfUsers + " trova --> " + wordToFind);

            ClientHandler handler = new ClientHandler(socket, "user" + numOfUsers, clients, wordToFind, rankingManager);

            Thread thread = new Thread(handler);
            addClient(handler);
            thread.start();
        }
    }


    public static List<ClientHandler> getClients() {
        return clients;
    }

    private void addClient(ClientHandler client) {
        clients.add(client);
    }

    private void log(String message) {
        System.out.println(message);
    }
}

class RankingManager{
    private ArrayList<Integer> ranking;
    private ArrayList<String> usernames;

    public RankingManager() {
        this.ranking = new ArrayList<Integer>();
        this.usernames = new ArrayList<String>();
    }
    public void addToRanking(String user, int score) {
        if(!this.usernames.contains(user)) {
            this.ranking.add(score);
            this.usernames.add(user);
        }else{
            this.ranking.set(this.usernames.indexOf(user) ,score);
        }
        bubbleSort(ranking, usernames);
        System.out.println(this.ranking.toString());
    }
    public String getRankingInString(){
        if(usernames.size()!=0){
            StringBuilder toReturn = new StringBuilder();
            for (int i = 0; i < ranking.size(); i++){
                toReturn.append(usernames.get(i)).append(";").append(ranking.get(i)).append(";\n");
            }
            return toReturn.toString();
        }
       return " ; ;\n";
    }
    private static void bubbleSort(ArrayList<Integer> arr , ArrayList<String> usernames){
        int n = arr.size();
        int temp1 = 0;
        String temp2 = "";
        for(int i=0; i < n; i++){
            for(int j=1; j<(n-i);j++){
                if(arr.get(j-1)>arr.get(j)){
                    temp1 = arr.get(j-1);
                    arr.set(j-1,arr.get(j));
                    arr.set(j,temp1);
                    temp2 = usernames.get(j-1);
                    usernames.set(j-1,usernames.get(j));
                    usernames.set(j,temp2);
                }
            }
        }
    }
}