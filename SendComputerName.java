import java.util.*;
import java.net.*;
import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class SendComputerName{
  public static void main(String args[]) throws Exception{

  byte[] compNameBytes = new byte[10];
  byte[] receiveData = new byte[10];

  String compName = InetAddress.getLocalHost().getHostName();
  compNameBytes = compName.getBytes();

  InetAddress address = InetAddress.getByName("192.168.1.7"); //Set to IP of Server Computer
  DatagramPacket sendPacket = new DatagramPacket(compNameBytes, compNameBytes.length, address, 1024);
  DatagramSocket clientSocket = new DatagramSocket();
  clientSocket.send(sendPacket);

  DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
  clientSocket.receive(receivePacket);
  String modifiedSentence = new String(receivePacket.getData());
  System.out.println("FROM SERVER:" + modifiedSentence);
  clientSocket.close();

  System.out.println(InetAddress.getLocalHost().getHostAddress());
  }
}
