import java.io.*;
import java.net.*;
import java.util.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class ReceiveComputerName
{
  private static final DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");

   public static void main(String args[]) throws Exception
      {
         DatagramSocket serverSocket = new DatagramSocket(1024);
            byte[] receiveData = new byte[10];
            byte[] sendData = new byte[10];
            File output = new File("Computer_Names.csv");
            
            FileWriter fw = new FileWriter("Computer_Names.csv",true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter compNamesFile = new PrintWriter(bw);

            String columnNamesList = "Computer Name, Date";
            compNamesFile.write(columnNamesList + '\n');

            while(true)
               {
                  DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                  serverSocket.receive(receivePacket);
                  String sentence = new String(receivePacket.getData());
                  System.out.println("RECEIVED: " + sentence);

                  Date currentDate = new Date();
                  compNamesFile.write(sentence + "," + sdf.format(currentDate) + "\n");

                  InetAddress IPAddress = receivePacket.getAddress();
                  int port = receivePacket.getPort();
                  String capitalizedSentence = sentence.toUpperCase();
                  sendData = capitalizedSentence.getBytes();


                  DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                  serverSocket.send(sendPacket);
                  System.out.println("Waiting");
              }
      }
}
