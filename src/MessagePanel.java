import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class MessagePanel extends JPanel implements Runnable, ActionListener {
  private final int WIDTH = 300;
  private final int HEIGHT = 500;
  private final int port = 8080;
  private static Socket mySocket;
  private Thread thread;
  private InetAddress sourceAddress;
  private int sourcePort;
  private GridBagConstraints gbc;
  private JButton msg;
  private JButton exit;
  private static WindowManager winManager;
  private JTextField dest_ip;
  private JTextField dest_port;
  private String windowTitle;

  public MessagePanel() {
    super();
    // this will manage which window to append received message.
    winManager = new WindowManager();
    setPreferredSize(new Dimension(WIDTH, HEIGHT));
    setBorder(new EmptyBorder(15, 15, 15, 15));
    setLayout(new GridBagLayout());

    gbc = new GridBagConstraints();
    gbc.gridwidth = GridBagConstraints.REMAINDER;

    add(new JLabel("<html><h1><strong>Chat APP</strong></h1><hr></html>"), gbc);


    gbc.anchor = GridBagConstraints.CENTER;
    gbc.fill = GridBagConstraints.BOTH;

    JPanel buttons = new JPanel(new GridBagLayout());
    dest_ip = new JTextField();
    JLabel ip_label = new JLabel("IP Address");
    ip_label.setLabelFor(dest_ip);
    dest_ip.setToolTipText("Enter IP address");
    dest_port = new JTextField();
    JLabel port_label = new JLabel("Port Number");
    port_label.setLabelFor(dest_port);
    dest_port.setToolTipText("Enter PORT");
    gbc.insets = new Insets(2, 0, 2, 0);
    msg = new JButton("New Message");
    msg.addActionListener(this);
    exit = new JButton("Exit");
    exit.addActionListener(this);
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;
    buttons.add(ip_label, gbc);
    buttons.add(dest_ip, gbc);
    buttons.add(port_label, gbc);
    buttons.add(dest_port, gbc);
    gbc.anchor = GridBagConstraints.LINE_END;
    buttons.add(msg, gbc);
    buttons.add(exit, gbc);
    add(buttons, gbc);

    // show my address
    getAddress();
    add(new JLabel("<html><h3>COPYRIGHT &#169; 2019 S WYCO</h3></html>"), gbc);

  }

  public void addNotify() {
    super.addNotify();
    if (thread == null) {
      thread = new Thread(this);
      thread.start();
    }
  }

  private void getAddress() {
    // set my socket
    mySocket = new Socket(this.port, Socket.SocketType.NoBroadcast);

    add(new JLabel("<html>" + "<h1>" + "<strong>My IP: " + mySocket.getAddress().getHostAddress()
        + "</h1><h2>My PORT: " + port + "</h2></html>"), gbc);

  }



  @Override
  public void run() {
    // listen for messages
    receiveMethod();
  }


  public static void receiveMethod() {
    do {
      DatagramPacket inPacket = mySocket.receive();
      // receive packet
      if (inPacket != null) {
        byte[] inBuffer = inPacket.getData();
        String inMessage = new String(inBuffer);
        InetAddress senderAddress = inPacket.getAddress();
        int senderPort = inPacket.getPort();
        System.out.println("Received message: " + inMessage);

        // search window manager if there's already a window for source address and port
        String key = senderAddress.getHostAddress() + ":" + senderPort;
        newWindow window = (newWindow) winManager.getWindow(key);
        if (window != null) {
          window.setVisible(true);
          window.toFront();
          window.addToTextArea(key + ": " + inMessage);
        } else {
          // make new window
          newWindow newChat = new newWindow();
          newChat.setVisible(true);
          newChat.setTitle(key);
          newChat.setSocket(mySocket);
          newChat.setSourceAddress(senderAddress);
          newChat.setSourcePort(senderPort);
          newChat.addToTextArea(key + ": " + inMessage);
          // add the new window to window manager
          winManager.addWindow(key, newChat);
        }
      }
    } while (true);
  }

  public void extractFields(JTextField ip_address, JTextField port) {
    String ip = ip_address.getText();
    String port_num = port.getText();
    if (ip != "" && port_num != "") {
      try {
        this.sourcePort = Integer.parseInt(port.getText());
        this.sourceAddress = InetAddress.getByName(ip);
        this.windowTitle = sourceAddress.getHostAddress() + ":" + sourcePort;
      } catch (UnknownHostException e) {
        e.printStackTrace();
        System.exit(-1);
      }
    }
    // clear fields
    ip_address.setText("");
    port.setText("");
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    JButton btnClicked = (JButton) e.getSource();
    switch (btnClicked.getText()) {
      case "New Message":
        this.extractFields(dest_ip, dest_port);
        newWindow check_chat = (newWindow) winManager.getWindow(this.windowTitle);
        if (check_chat != null) {
          // chat already exists
          System.out.println("chat exist! pulling from map");
          check_chat.setVisible(true);
          check_chat.toFront();
        } else {
          // make new window chat
          System.out.println("creating new chat window");
          newWindow chat = new newWindow();
          chat.setVisible(true);
          chat.setSocket(mySocket);
          chat.setTitle(this.sourceAddress.getHostAddress() + ":" + this.sourcePort);
          chat.setSocket(mySocket);
          chat.setSourceAddress(this.sourceAddress);
          chat.setSourcePort(this.sourcePort);
          // add the new window to window manager
          winManager.addWindow(this.windowTitle, chat);
        }
        break;
      case "Exit":
        System.exit(0);
        break;
      default:
        break;
    }
  }

}
