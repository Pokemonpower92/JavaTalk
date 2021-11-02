/* Client for jtalk */

package JavaTalk;

import java.util.*;
import java.net.*;
import java.io.*;

public class TalkClient{
    boolean interrupted; 

    public void interupt(){
        this.interrupted = true;
    }
    // This one listens to stdin and writes to the socket.
    public TalkClient(String args[]){
        String host = args[0];
        
        try (
            Socket sock = new Socket(host, Integer.parseInt(args[1]));
            PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
            BufferedReader in = new BufferedReader( new InputStreamReader(sock.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        ) {
            String line;
            SocketListener listen = new SocketListener(in, this);

            // Start the socket listener.
            listen.start();

            // Send name to socket. 
            line = args[2];
            out.println(line);

            interrupted = false;
            // Read from stdin and write to the socket. 
            while((line = stdIn.readLine()) != null){
                if(interrupted) break;
                out.println(line);
            }
        }
        catch(IOException ie){
            System.out.println(ie.getMessage());
            System.exit(1);
        }
    }

    public static void main(String args[]){
        TalkClient tc = new TalkClient(args);
    }
}