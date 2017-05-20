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

    while(techInitials == null){
      System.out.println("In While loop.");
      techInitials = JOptionPane.showInputDialog(initialsFrame,"Please enter your initials!","CATS Student Tech Initials.", JOptionPane.WARNING_MESSAGE);
    }

    byte[] compNameBytes = new byte[15];
    byte[] receiveData = new byte[75];

    String compName = InetAddress.getLocalHost().getHostName() + ", " + techInitials;
    compNameBytes = compName.getBytes();

    InetAddress address = InetAddress.getByName(SERVER_IP);
    DatagramPacket sendPacket = new DatagramPacket(compNameBytes, compNameBytes.length, address, SENDING_PORT);
    DatagramSocket clientSocket = new DatagramSocket();
    clientSocket.send(sendPacket);

    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
    clientSocket.receive(receivePacket);
    String confirmation = new String(receivePacket.getData());
    System.out.println(confirmation);
    clientSocket.close();

    System.out.println("Done");
    System.exit(0);
  }
}
