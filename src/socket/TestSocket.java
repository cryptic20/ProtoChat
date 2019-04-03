package socket;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

public class TestSocket {

  private static Socket mySocket;

  public static void main(String[] args) {

    mySocket = new Socket(64000, Socket.SocketType.NoBroadcast);

    System.out.println("My Address = " + mySocket.getAddress().getHostAddress());
    try {
      InetAddress inet = InetAddress.getByName("10.0.75.1");
    } catch (UnknownHostException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    mySocket.send("Hello Object Oriented Communication World!!!", mySocket.getAddress(), 8080);

    System.out.println("Going to sleep .... z z z z z");

    try {
      TimeUnit.SECONDS.sleep(5);
    } catch (InterruptedException ie) {
      ie.printStackTrace();
      System.exit(-1);
    }

    System.out.println("Just woke up!!");

    DatagramPacket inPacket = mySocket.receive();

    byte[] inBuffer = inPacket.getData();
    String inMessage = new String(inBuffer);
    InetAddress senderAddress = inPacket.getAddress();
    int senderPort = inPacket.getPort();

    System.out.println();
    System.out.println("Message        = " + inMessage);
    System.out.println("Sender Address = " + senderAddress.getHostAddress());
    System.out.println("Sender Port    = " + senderPort);

    mySocket.close();

  }

}
