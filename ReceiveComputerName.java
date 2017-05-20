/**
 * The Server Program used to receive the name of a computer through
 * a specified port from a client computer. The Name of the computer is written
 * to a file as well as the date, and the Tech who completed the PM.
 *
 * @author Wyatt Gleason
 * @version 1.0
 * @since 5/19/2017
 */

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
  private static final String fileName = "Computer_Names.csv";
  private static final String columnNamesList = "Computer Name, Student Tech Initials, Date " + "\n";
  private static final int LISTENING_PORT = 1024;

   public static void main(String args[]) throws Exception
      {
         DatagramSocket serverSocket = new DatagramSocket(LISTENING_PORT);
            byte[] receiveData = new byte[15];
            byte[] sendData = new byte[75];

            while(true)
               {
                 System.out.println("Listening on port " + LISTENING_PORT);
                  DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                  serverSocket.receive(receivePacket);
                  String sentence = new String(receivePacket.getData());
                  System.out.println("Recieved Computer Name: " + sentence);

                  writeToFile(sentence, fileName);

                  InetAddress IPAddress = receivePacket.getAddress();
                  int port = receivePacket.getPort();
                  String capitalizedSentence = "Computer Name " + sentence + "successfully written to master file.";
                  sendData = capitalizedSentence.getBytes();


                  DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                  serverSocket.send(sendPacket);
              }
      }

  /**
  * Write the received information to a file. The file is created if it does not already exist
  * and appended to otherwise.
  *
  * @param compName The received data packet with the information that is to be written
  *        to the output file.
  * @param compFileName The name of the file that the information is written to.
  * @throws IOException Exception if file is not found or otherwise cannot be created.
  */
  public static void writeToFile(String compName, String compFileName) throws IOException
      {
         try{
               File output = new File(compFileName);
               Date currentDate = new Date();

               if(!output.exists()){
                 output.createNewFile();
                 PrintWriter pw = new PrintWriter(output);
                 pw.write(columnNamesList);
                 pw.close();
                 System.out.println("File Created with header.");
               }

               BufferedWriter bw = new BufferedWriter(new FileWriter(output.getAbsoluteFile(),true));
               bw.write(compName + ", " + sdf.format(currentDate) + "\n");
               bw.close();
               System.out.println("Written to file: " + compFileName);
          } catch (IOException e)
            {
              e.printStackTrace();
              System.out.println("Error Writing or Creating File");
              return;
            }
      }
}
