package JavaTalk;

import java.io.*;

// Listens to the socket and writes to stdout.
public class SocketListener extends Thread {
    BufferedReader in;
    TalkClient tc;

    public SocketListener(BufferedReader in, TalkClient tc) {
        this.in = in;
        this.tc = tc;
    }

    @Override
    public void run(){
        try {
            String line;

            while((line = this.in.readLine()) != null){
                System.out.println(line);
            }

            this.tc.interupt();
        }
        catch(IOException ie){
            System.exit(1);
        }
    }
}