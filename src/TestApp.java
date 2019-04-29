import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class TestApp {
  private static InetAddress sourceAddress;
  private static int port = 64000; // 7000

  public static void main(String[] args) {


    try {
      sourceAddress = InetAddress.getLocalHost();
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    Socket first = new Socket(port, Socket.SocketType.NoBroadcast);
    Socket second = new Socket(port + 1, Socket.SocketType.NoBroadcast);
    Socket third = new Socket(port + 2, Socket.SocketType.NoBroadcast);
    Socket fourth = new Socket(port + 3, Socket.SocketType.NoBroadcast);
    Socket[] sockets = {first, second, third, fourth};
    int i = 1;
    for (Socket socket : sockets) {
      socket.send("from socket" + i, sourceAddress, 64000);
      i++;
    }

    do {
      for (int j = 0; j < 4; j++) {
        DatagramPacket inPacket = sockets[j].receive();
        if (inPacket != null) {
          byte[] inBuffer = inPacket.getData();
          String inMessage = new String(inBuffer).substring(0, inPacket.getLength());
          InetAddress senderAddress = inPacket.getAddress();
          int senderPort = inPacket.getPort();
          System.out.println("Received message: " + inMessage);

          // send a message back after receiving.
          sockets[j].send("I received your message '" + inMessage + "' from socket" + (j + 1),
              senderAddress, senderPort);
        }
      }

    } while (true);
  }
}
