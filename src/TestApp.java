import java.net.InetAddress;
import java.net.UnknownHostException;

public class TestApp {
  private static InetAddress sourceAddress;
  private static int port = 7000;

  public static void main(String[] args) {

    try {
      sourceAddress = InetAddress.getByName("localhost");
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    Socket first = new Socket(port, Socket.SocketType.NoBroadcast);
    Socket second = new Socket(port + 1, Socket.SocketType.NoBroadcast);
    Socket third = new Socket(port + 2, Socket.SocketType.NoBroadcast);
    Socket fourth = new Socket(port + 3, Socket.SocketType.NoBroadcast);
    Socket[] sockets = {first, second, third, fourth};
    int i = 0;
    for (Socket socket : sockets) {
      socket.send("from port" + port + i, sourceAddress, 8080);
      i++;
    }

  }

}
