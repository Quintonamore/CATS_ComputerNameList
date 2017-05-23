/**
 * The Client Program used to send the name of a computer through
 * a specified port to a server computer
 *
 * @author Wyatt Gleason
 * @version 1.0
 * @since 5/19/2017
 */

import java.util.*;
import java.net.*;
import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.*;


public class SendComputerName{

  private static final String SERVER_IP = "192.168.1.7";  //Set to IP of Server Computer
  private static final int SENDING_PORT = 1024;

  public static void main(String args[]) throws Exception{

    JFrame initialsFrame = new JFrame("Name Dialog");

    String techInitials = JOptionPane.showInputDialog(initialsFrame,"Enter your initials.","CATS Student Tech Initials.", JOptionPane.QUESTION_MESSAGE);

    try{
        while(techInitials.isEmpty()){
          techInitials = JOptionPane.showInputDialog(initialsFrame,"Please enter your initials!","CATS Student Tech Initials.", JOptionPane.WARNING_MESSAGE);
        }
       } catch (NullPointerException e){
         techInitials = "NIL";
       }

    byte[] compNameBytes = new byte[15];
    byte[] receiveData = new byte[75];

    String compName = InetAddress.getLocalHost().getHostName() + ", " + techInitials;
    compNameBytes = compName.getBytes();

    InetAddress address = InetAddress.getByName(SERVER_IP);
    DatagramPacket sendPacket = new DatagramPacket(compNameBytes, compNameBytes.length, address, SENDING_PORT);
    DatagramSocket clientSocket = new DatagramSocket();

    try{
      System.out.println("Sending data to server...");
      clientSocket.send(sendPacket);
    } catch (SocketException e){
      e.printStackTrace();
      System.out.println("Error sending data to PM Server; Write down the computer name, date, and your initials.");
      System.exit(1);
    }

    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
    clientSocket.receive(receivePacket);
    String confirmation = new String(receivePacket.getData());
    System.out.println(confirmation);
    clientSocket.close();

    System.out.println("Done...");
    System.exit(0);
  }
}
