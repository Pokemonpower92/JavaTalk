/* Server for jtalk.*/

package JavaTalk;

import java.util.*;
import java.net.*;
import java.io.*;

public class ChatServer{

    public ChatServer(String args[]){
        TreeMap<String, ChatData> chatRooms = new TreeMap<String, ChatData>();
        
        // Build rooms map.
        for(int i = 1; i < args.length; i++){
            ChatData newRoom = new ChatData();
             chatRooms.put(args[i], newRoom);
        }
    
        // Accept all connection requests.
        try(ServerSocket server = new ServerSocket(Integer.parseInt(args[0])))
        {
            while(true){
                new ClientThread(server.accept(), chatRooms).start();
            }
        }
        catch(IOException ie){
            System.out.println(ie.getMessage());
        }
    }

    public static void main(String args[]){
        ChatServer c = new ChatServer(args);
    }
}