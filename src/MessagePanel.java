import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.net.InetAddress;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class MessagePanel extends JPanel implements Runnable, ActionListener {
  private final int WIDTH = 300;
  private final int HEIGHT = 400;
  private final int port = 8080;
  private InetAddress myAddress;
  private static Socket mySocket;
  private Thread thread;
  private String ip_address = "";
  private GridBagConstraints gbc;
  private JButton msg;
  private JButton exit;
  private static windowManager winManager;

  public MessagePanel() {
    super();
    // this will manage which window to append received message.
    winManager = new windowManager();
    setPreferredSize(new Dimension(WIDTH, HEIGHT));
    setBorder(new EmptyBorder(5, 5, 5, 5));
    setLayout(new GridBagLayout());

    gbc = new GridBagConstraints();
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    gbc.anchor = GridBagConstraints.NORTH;

    add(new JLabel("<html><h1><strong>Chat APP</strong></h1><hr></html>"), gbc);

    gbc.anchor = GridBagConstraints.CENTER;
    gbc.fill = GridBagConstraints.HORIZONTAL;

    JPanel buttons = new JPanel(new GridBagLayout());
    msg = new JButton("New Message");
    msg.addActionListener(this);
    exit = new JButton("Exit");
    exit.addActionListener(this);
    buttons.add(msg, gbc);
    buttons.add(exit, gbc);
    add(buttons, gbc);

    try {
      // show my address
      getAddress();
    } catch (java.net.UnknownHostException e) {
      e.printStackTrace();
      System.exit(-1);
    }

    // set my socket
    mySocket = new Socket(this.port, Socket.SocketType.NoBroadcast);



  }

  public void addNotify() {
    super.addNotify();
    if (thread == null) {
      thread = new Thread(this);
      thread.start();
    }
  }

  private void getAddress() throws java.net.UnknownHostException {
    myAddress = InetAddress.getLocalHost();
    ip_address = myAddress.getHostAddress();
    System.out.println(ip_address);

    add(new JLabel("<html>" + "<h1>" + "<strong>My IP: " + ip_address + "</h1><h2>My PORT: " + port
        + "</h2></html>"), gbc);

  }



  @Override
  public void run() {
    // listen for messages
    receiveMethod();
  }


  public static void receiveMethod() {



    do {

      // this thread will block in the receive call
      // until a message is received
      DatagramPacket inPacket = mySocket.receive();

      // receive packet
      byte[] inBuffer = inPacket.getData();
      if (inBuffer.length < 1) {
        String inMessage = new String(inBuffer);
        InetAddress senderAddress = inPacket.getAddress();
        int senderPort = inPacket.getPort();
        System.out.println("Received message = " + inMessage);

        // search window manager if there's already a window for source address and port
        String key = senderAddress + ":" + senderPort;
        newWindow window = (newWindow) winManager.getWindow(key);
        if (window != null) {
          window.addToTextArea(key + "-" + inMessage);
        } else {
          // make new window
          newWindow newChat = new newWindow();
          newChat.setTitle(key);
          newChat.setSocket(mySocket);
          newChat.setSourceAddress(senderAddress);
          newChat.setSourcePort(senderPort);
          newChat.updateSourceAddressField(senderAddress.getHostAddress());
          newChat.updateSourcePortField(senderPort + "");
          newChat.addToTextArea(key + "-" + inMessage);
          // add the new window to window manager
          winManager.addWindow(key, newChat);
        }


      }
      System.out.println("still listening...");


    } while (true);
  }


  @Override
  public void actionPerformed(ActionEvent e) {
    JButton btnClicked = (JButton) e.getSource();
    switch (btnClicked.getText()) {
      case "New Message":
        System.out.println("creating new message");
        newWindow chat = new newWindow();
        chat.setSocket(mySocket);
        break;
      case "Exit":
        System.out.println("System exiting...");
        System.exit(0);
        break;
      default:
        break;
    }
  }

}
