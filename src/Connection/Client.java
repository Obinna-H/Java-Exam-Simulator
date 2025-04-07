/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import File.ReadPathActivation;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Humphrey
 */
public class Client {
static Socket sckt; 
ServerSocket ss;
static DataInputStream dtinpt;  
static DataOutputStream dtotpt;   
ReadPathActivation key = new ReadPathActivation();

public Client() throws IOException {  
   key.readPathActivation();
   
   ss= new ServerSocket(5432);
 }  

public  void clientConnection() throws IOException{
    try (Socket s1 = new Socket ("127.0.0.1",5432)) {
        OutputStream s1out = s1.getOutputStream();
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s1out))) {
            String lic= key.readPathActivation();
            bw.write(lic);
        }
    }catch (ConnectException connExc){
    System.err.println("could not connect."+connExc);
    }
}

}