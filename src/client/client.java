package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class client {

    Scanner scan ;
    Socket socket = null;
    DataInputStream input = null;
    DataOutputStream output = null;
    InetAddress ip;


    public client(){
        try{
            ip = InetAddress.getByName("localhost");
            socket = new Socket(ip,Constants.PORT);

            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());

            scan = new Scanner(System.in);
        }catch(UnknownHostException ex){
            log("Client : " + ex.getMessage());
        }catch(IOException ex){
            log("Client : " + ex.getMessage());
        }

    }
    public String readMessageThread(){
                String msg="";
                    try{
                        msg = input.readUTF();
                        log(msg);
                    }catch(IOException ex){
                        log("readMessageThread : " + ex.getMessage());
                    }
                    return msg;
    }

    public void writeMessageThread(String msg){
                    try{
                        output.writeUTF(msg);
                    }catch(IOException ex){
                        log("writeMessageThread : " + ex.getMessage());
                    }
    }

    private void log(String msg){
        System.out.println(msg);
    }
}
