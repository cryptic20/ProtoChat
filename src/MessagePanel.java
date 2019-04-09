import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class MessagePanel extends JPanel implements Runnable, ActionListener {
  private final int WIDTH = 300;
  private final int HEIGHT = 400;
  private final int port = 64000;
  private static Socket mySocket;
  private Thread thread;
  private InetAddress sourceAddress;
  private int sourcePort;
  private GridBagConstraints gbc;
  private JButton msg;
  private JButton exit;
  private static WindowManager winManager;
  private JTextField dest_ip; // destination IP address
  private JTextField dest_port;
  private JTextField dest_name;
  private String windowTitle;
  private ButtonGroup socket_type;
  private JRadioButton broadcast_btn;
  private JRadioButton noBroadcast_btn;
  private JPanel noBroadcastPanel;
  private JPanel broadcastPanel;

  public MessagePanel() {
    super();
    winManager = new WindowManager(); // this will manage which window to append received message.
    setPreferredSize(new Dimension(WIDTH, HEIGHT));
    setBorder(new EmptyBorder(15, 15, 15, 15));
    setLayout(new GridBagLayout());
    gbc = new GridBagConstraints();
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    // header
    add(new JLabel("<html><h1><strong>Proto Chat</strong></h1><hr></html>"), gbc);
    // gbc settings
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.anchor = GridBagConstraints.LINE_END;
    gbc.insets = new Insets(2, 0, 2, 0);
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;

    JPanel buttons = new JPanel(new GridBagLayout());
    // panel for containing JRadioButton
    JPanel radio_panels = new JPanel(new FlowLayout());
    // panel for IP and port fields when no broadcast socket type is selected
    noBroadcastPanel = new JPanel(new GridBagLayout());
    // panel for name field when broadcast is selected for socket type.
    broadcastPanel = new JPanel(new GridBagLayout());

    socket_type = new ButtonGroup();
    JLabel btnLabel = new JLabel("Socket Type");
    broadcast_btn = new JRadioButton("Broadcast");
    noBroadcast_btn = new JRadioButton("No Broadcast");
    btnLabel.setLabelFor(noBroadcast_btn);
    btnLabel.setLabelFor(broadcast_btn);
    // add listener
    SocketRadioButtonListener radio_listeners = new SocketRadioButtonListener();
    broadcast_btn.addActionListener(radio_listeners);
    noBroadcast_btn.addActionListener(radio_listeners);

    // add JRadioButton to the group. that way only one can be chosen at a time.
    socket_type.add(broadcast_btn);
    socket_type.add(noBroadcast_btn);

    // add buttons to socket_panel
    radio_panels.add(broadcast_btn, gbc);
    radio_panels.add(noBroadcast_btn, gbc);

    // broadcast fields
    dest_name = new JTextField();
    JLabel name_label = new JLabel("Enter Name");
    name_label.setLabelFor(dest_name);
    name_label.setToolTipText("Enter the name of the recipient");
    broadcastPanel.add(name_label, gbc);
    broadcastPanel.add(dest_name, gbc);
    // add broadcast panel to main component
    add(broadcastPanel, gbc);

    // no broadcast fields
    dest_ip = new JTextField();
    JLabel ip_label = new JLabel("IP Address");
    ip_label.setLabelFor(dest_ip);
    dest_ip.setToolTipText("Enter IP address");

    dest_port = new JTextField();
    JLabel port_label = new JLabel("Port Number");
    port_label.setLabelFor(dest_port);
    dest_port.setToolTipText("Enter PORT");
    noBroadcastPanel.add(ip_label, gbc);
    noBroadcastPanel.add(dest_ip, gbc);
    noBroadcastPanel.add(port_label, gbc);
    noBroadcastPanel.add(dest_port, gbc);
    add(noBroadcastPanel, gbc);

    // create new message buttons and exit
    msg = new JButton("New Message");
    msg.addActionListener(this);
    exit = new JButton("Exit");
    exit.addActionListener(this);
    // add the buttons for creating new message and exit.
    buttons.add(radio_panels, gbc); // add radio panel to button panels
    buttons.add(msg, gbc);
    buttons.add(exit, gbc);
    // add button panel to main
    add(buttons, gbc);

    setSocketType(true); // default is no broadcast

    JLabel ip_info = new JLabel("<html><h1><strong>My IP: " + mySocket.getAddress().getHostAddress()
        + "</h1><h2>My PORT: " + port + "</strong></h2></html>");
    noBroadcastPanel.add(ip_info);

    JLabel name_info = new JLabel("<html><h1><strong> My Name: "
        + mySocket.getAddress().getHostName() + "</strong></h1></html>");
    broadcastPanel.add(name_info);
  }

  public void addNotify() {
    super.addNotify();
    if (thread == null) {
      thread = new Thread(this);
      thread.start();
    }
  }

  /**
   * This method will set the selected socket's type. True for Broadcast, False for No Broadcast
   * 
   * @param broadcast Boolean input
   */
  private void setSocketType(boolean broadcast) {
    // if port is binded by previous socket, close it
    if (mySocket != null) {
      mySocket.close();
    }
    // set my socket based on selected type
    if (broadcast) {
      System.out.println("Socket type is BROADCAST");
      mySocket = new Socket(this.port, Socket.SocketType.Broadcast);
      // set broadcast button as selected
      broadcast_btn.setSelected(true);
      // show broadcast panel and hide no broadcast panel
      noBroadcastPanel.setVisible(false);
      broadcastPanel.setVisible(true);
    } else {
      // no broadcast
      System.out.println("Socket type is NO BROADCAST");
      mySocket = new Socket(this.port, Socket.SocketType.NoBroadcast);
      noBroadcast_btn.setSelected(true);
      // hide broadcast panel and show no broadcast panel
      noBroadcastPanel.setVisible(true);
      broadcastPanel.setVisible(false);
    }

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
        System.out.println(inPacket.getLength());
        String inMessage = new String(inBuffer).substring(0, inPacket.getLength());
        InetAddress senderAddress = inPacket.getAddress();
        int senderPort = inPacket.getPort();
        System.out.println("Received message: " + inMessage);

        // search window manager if there's already a window for source address and port
        String key = senderAddress.getHostAddress() + ":" + senderPort;
        ChatWindow window = (ChatWindow) winManager.getWindow(key);
        if (window != null) {
          window.setVisible(true);
          window.toFront();
          window.addToTextArea(key + ": " + inMessage);
        } else {
          // make new window
          ChatWindow newChat = new ChatWindow();
          newChat.setVisible(true);
          newChat.toFront();
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

  public void extractName(JTextField name_field) {
    String name = name_field.getText();

  }

  public void extractIPandPortFields(JTextField ip_address, JTextField port) {

    String ip = ip_address.getText();
    String port_num = port.getText();
    if (ip != "" && port_num != "") {
      try {
        sourcePort = Integer.parseInt(port.getText());
        sourceAddress = InetAddress.getByName(ip);
        windowTitle = sourceAddress.getHostAddress() + ":" + sourcePort;
      } catch (UnknownHostException e) {
        e.printStackTrace();
        System.exit(-1);
      }
    }
    // clear fields
    ip_address.setText("");
    port.setText("");
  }

  /**
   * This private class will serve as a new action listener for JRadioButtons.
   */
  private class SocketRadioButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      JRadioButton socket_type = (JRadioButton) e.getSource();
      if (socket_type.getText() == "Broadcast") {
        setSocketType(true);
      } else {
        setSocketType(false);
      }

    }

  };

  @Override
  public void actionPerformed(ActionEvent e) {
    JButton btnClicked = (JButton) e.getSource();
    switch (btnClicked.getText()) {
      case "New Message":
        extractIPandPortFields(dest_ip, dest_port);
        ChatWindow check_chat = (ChatWindow) winManager.getWindow(this.windowTitle);
        if (check_chat != null) {
          // chat already exists
          System.out.println("chat exist! pulling from map");
          check_chat.setVisible(true);
          check_chat.toFront();
        } else {
          // make new window chat
          System.out.println("creating new chat window");
          ChatWindow chat = new ChatWindow();
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
