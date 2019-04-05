

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 * 
 * This is my socket class. It is really cool!
 * 
 * @author sameh
 *
 */
public class Socket {

  public static enum SocketType {
    Broadcast, NoBroadcast
  };

  private int portNumber;
  private InetAddress address;
  private DatagramSocket mySocket;

  private ConcurrentLinkedQueue<DatagramPacket> messageQueue =
      new ConcurrentLinkedQueue<DatagramPacket>();

  private Thread receiveThread;
  private boolean receiveThreadShouldRun = true;

  /**
   * 
   * This is my constructor
   * 
   * @param portNumber This is the port number this socket will use.
   */
  public Socket(int portNumber, SocketType socketType) {
    this.portNumber = portNumber;

    try {
      address = InetAddress.getLocalHost();
    } catch (UnknownHostException uhe) {
      uhe.printStackTrace();
      System.exit(-1);
    }

    System.out.println("My IP Address = " + address.getHostAddress());
    System.out.println("My Port Number = " + portNumber);

    try {
      switch (socketType) {
        case Broadcast:
          mySocket = new DatagramSocket(portNumber);
          break;
        case NoBroadcast:
          mySocket = new DatagramSocket(portNumber, address);
          break;
      }
    } catch (SocketException se) {
      se.printStackTrace();
      System.exit(-1);
    }

    receiveThread = new Thread(new Runnable() {
      public void run() {
        receiveThreadMethod();
      }
    });
    receiveThread.setName("Receive Thread For Port = " + this.portNumber);
    receiveThread.start();
  }


  /**
   * receive thread
   */
  public void receiveThreadMethod() {

    try {
      mySocket.setSoTimeout(50);
    } catch (SocketException se) {
      se.printStackTrace();
      System.exit(-1);
    }

    do {
      DatagramPacket inPacket = null;

      byte[] inBuffer = new byte[1024];
      for (int i = 0; i < inBuffer.length; i++) {
        inBuffer[i] = ' ';
      }

      inPacket = new DatagramPacket(inBuffer, inBuffer.length);

      try {
        mySocket.receive(inPacket);
        messageQueue.add(inPacket);
      } catch (SocketTimeoutException ste) {
        // nothing to do here
      } catch (IOException ioe) {
        ioe.printStackTrace();
        System.exit(-1);
      }

    } while (receiveThreadShouldRun);

    System.out.println(receiveThread.getName() + " is exiting!!!");

  }

  /**
   * 
   * @param message
   * @param destinationAddress
   * @param destinationPort
   */
  public void send(String message, InetAddress destinationAddress, int destinationPort) {

    byte[] outBuffer;
    outBuffer = message.getBytes();

    DatagramPacket outPacket =
        new DatagramPacket(outBuffer, outBuffer.length, destinationAddress, destinationPort);

    try {
      mySocket.send(outPacket);
    } catch (IOException ioe) {
      ioe.printStackTrace();
      System.exit(-1);
    }
  }


  /**
   * 
   * This is my amazing receive method
   * 
   * @return A DatagramPacket if available, <code>null</code> otherwise.
   */
  public DatagramPacket receive() {
    return messageQueue.poll();
  }

  public void close() {
    receiveThreadShouldRun = false;
    try {
      TimeUnit.MILLISECONDS.sleep(100);
    } catch (InterruptedException ie) {
      ie.printStackTrace();
      System.exit(-1);
    }
    mySocket.close();
  }

  public int getPortNumber() {
    return portNumber;
  }


  public InetAddress getAddress() {
    return address;
  }


}
