import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class sendMessage {
  private DatagramSocket socket;
  private static InetAddress addr;

  public sendMessage(DatagramSocket socket) {
    this.socket = socket;
  }

  public void sendPacket(String msg, InetAddress dest, int destPort) {
    try {
      addr = InetAddress.getLocalHost();
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(-1);
    }

    byte[] buffer = new byte[140];

    String message = msg;
    buffer = message.getBytes();

    try {
      DatagramPacket outPacket = new DatagramPacket(buffer, message.length(), dest, destPort);
      System.out.println("Sending packet!");
      socket.send(outPacket);
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(-1);
    }

  }

}
