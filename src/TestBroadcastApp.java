import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class TestBroadcastApp {
  private static InetAddress sourceAddress;
  private static int port = 7000;

  public static void main(String[] args) {


    try {
      sourceAddress = InetAddress.getLocalHost();
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    Socket first = new Socket(port, Socket.SocketType.Broadcast);
    Socket second = new Socket(port + 1, Socket.SocketType.Broadcast);
    Socket third = new Socket(port + 2, Socket.SocketType.Broadcast);
    Socket fourth = new Socket(port + 3, Socket.SocketType.Broadcast);
    Socket[] sockets = {first, second, third, fourth};
    int i = 1;
    for (Socket socket : sockets) {
      socket.send("????? shen ##### socket" + i, sourceAddress, 64000);
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


          if (inMessage.startsWith("#####")) {
            String[] split_message = inMessage.split(" "); // split message by white-space;
            if (split_message[0].equals("#####")
                && split_message[1].equalsIgnoreCase("socket" + (j + 1))) {
              System.out.println("it matched!");
              try {
                senderAddress = InetAddress.getByName(split_message[3]);
              } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
              }
            }
            // send a message back after receiving.
            sockets[j].send("Initiating conversation..." + (j + 1), senderAddress, senderPort);

          } else {
            sockets[j].send(
                "I received your message Scher, '" + inMessage + "' \nfrom socket" + (j + 1),
                senderAddress, senderPort);
          }

        }
      }

    } while (true);
  }

}
