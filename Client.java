package JavaTalk;

import java.io.*;

// ChatDatas keep track of these. 
public class Client{
    String name;
    PrintWriter out;

    public Client(String name, PrintWriter out){
        this.name = name;
        this.out = out;
    }
}