package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    static List<ClientHandler> clients;
    ServerSocket serverSocket;
    static int numOfUsers = 0;
    Socket socket;

    String wordToFind;

    public Server() {
        clients = new ArrayList<>();
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
                wordToFind = fileConnector.getRandomLineFromFile("src/server/parole.csv");
            } catch (IOException e) {
                System.out.println("Non è stato possibile trovare una parola");
                System.out.println("Closing...");
                throw new RuntimeException(e);
            }
            log("Client" + numOfUsers + " trova --> " + wordToFind);

            ClientHandler handler = new ClientHandler(socket, "user" + numOfUsers, clients, wordToFind);

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