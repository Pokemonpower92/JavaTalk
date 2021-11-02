package JavaTalk;

import java.util.*;
import java.net.*;
import java.io.*;

public class ClientThread extends Thread {
    TreeMap<String, ChatData> chatRooms;
    ChatData room;
    Socket s;
    
    public ClientThread(Socket s, TreeMap<String, ChatData> chatRooms){
        this.chatRooms = chatRooms;
        this.s     = s;
    }

    @Override
    public void run() {
        try (
            PrintWriter out   = new PrintWriter(this.s.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(this.s.getInputStream()));
        ){
            Client c;
            String message;
            String name;

            //get name from client.
            name = in.readLine();

            // Send chatrooms to client.
            for( Map.Entry<String, ChatData> entry : this.chatRooms.entrySet()){
                out.print(entry.getKey() + ": ");
                out.println(entry.getValue().sendClients());
            }

            // Get the client into a chatroom and add them to the ChatData for it.
            out.println("");
            out.println("Enter chat room:");
            while((this.room = this.chatRooms.get(in.readLine())) == null){
                out.println("Enter chat room:");
            }

            c = new Client(name, out);
            this.room.addClient(c);

            // Start sending messages.
            this.room.broadcastMessage(name + " has joined.");
            while((message = in.readLine()) != null){
                this.room.broadcastMessage(name + ": " + message);
            }

            // Remove them when they leave.
            this.room.removeClient(c);
            this.room.broadcastMessage(name + " has left.");
        }
        catch(IOException ie){
            System.out.println(ie.getMessage());
        }
    }
}