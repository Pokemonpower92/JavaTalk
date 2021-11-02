package JavaTalk;

import java.util.*;

// Information for chatrooms.
public class ChatData{
    ArrayList<Client> clients;

    public ChatData(){
        clients = new ArrayList<Client>();
    }
    
    // Send the message to all clients.
    public synchronized void broadcastMessage(String message){
        for(Client c : this.clients){
            c.out.println(message);
        }
    }

    // Compose a string from all clients. 
    // This would be prohibatively slow for large rooms.
    public String sendClients(){
        String out = "";
        for(Client c: this.clients){
            out = out + c.name + " ";
        }
        
        return out;
    }
    
    // Add a client to the arraylist. Needs to be atomic.
    public synchronized void addClient(Client c){
        this.clients.add(c);
    }

    // Remove a client. Needs to be atomic. 
    // Don't really know a better way to do this. 
    // Would perfer to hold clients in a hashtable.
    public synchronized void removeClient(Client c){
        int index = 0;

        for(Client cur: this.clients){
            if (cur == c) break;
            index++;
        }

        this.clients.remove(index);
    }
}