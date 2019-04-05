import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class newWindow extends JFrame implements ActionListener, KeyListener, Runnable {
  private Socket mySocket;
  private InetAddress sourceAddress;
  private int sourcePort;
  private JTextArea textArea;
  private JTextField textField;
  private JTextField dest_ip;
  private JTextField dest_port;

  public void setSocket(Socket socket) {
    this.mySocket = socket;
  }

  public void setSourceAddress(InetAddress sourceAdd) {
    this.sourceAddress = sourceAdd;
  }

  public void setSourcePort(int port) {
    this.sourcePort = port;
  }

  public void updateSourcePortField(String port) {
    dest_port.setText(port);
    dest_port.setEnabled(false);
  }

  public void updateSourceAddressField(String address) {
    dest_ip.setText(address);
    dest_ip.setEnabled(false);
  }

  public void setTitle(String title) {
    this.setTitle(title);
  }


  // constructor
  public newWindow() {
    setResizable(false);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setBounds(100, 100, 450, 300);
    JPanel panel = new JPanel();
    panel.setBorder(new EmptyBorder(5, 5, 5, 5));
    add(panel);
    panel.setLayout(null);

    dest_ip = new JTextField();
    dest_ip.setBounds(25, 0, 125, 25);
    dest_ip.setToolTipText("Enter IP address");
    panel.add(dest_ip);
    dest_ip.setColumns(10);
    dest_ip.addKeyListener(this);

    dest_port = new JTextField();
    dest_port.setBounds(160, 0, 50, 25);
    dest_port.setToolTipText("Enter PORT");
    panel.add(dest_port);
    dest_port.setColumns(10);
    dest_port.addKeyListener(this);

    JTextArea textArea = new JTextArea();
    textArea.setEnabled(false);
    textArea.setEditable(false);
    textArea.setBounds(25, 29, 407, 153);
    panel.add(textArea);


    JButton send = new JButton("Send");
    send.setBounds(350, 200, 75, 25);
    panel.add(send);
    send.addActionListener(this);

    textField = new JTextField();
    textField.setBounds(25, 194, 300, 36);
    panel.add(textField);
    textField.setColumns(10);


    JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setBounds(25, 29, 300, 153);
    panel.add(scrollPane);



  }

  public void addToTextArea(String txt) {
    if (this.textArea.getText().trim().length() == 0) {
      this.textArea.append(txt);
    } else {
      this.textArea.append("\n" + txt);
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    JButton btnClicked = (JButton) e.getSource();
    String button = btnClicked.getText();

    switch (button) {
      case "Send":
        String msg_text = textField.getText();
        textField.setText("");
        mySocket.send(msg_text, sourceAddress, sourcePort);
        addToTextArea("Me: " + msg_text);
        break;
      default:
        break;
    }
  }

  @Override
  public void keyPressed(KeyEvent arg0) {

  }

  @Override
  public void keyReleased(KeyEvent arg0) {

    if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
      String dest_address = dest_ip.getText();
      if (dest_address != "") {
        try {
          InetAddress inet = InetAddress.getByName(dest_address);

          this.setSourceAddress(inet);
        } catch (UnknownHostException e) {
          e.printStackTrace();
          System.exit(-1);
        }
        updateSourceAddressField(dest_address);
      }
      String str_port = dest_port.getText();
      if (str_port != "") {
        updateSourcePortField(str_port);
      }
    }

  }

  @Override
  public void run() {}

  @Override
  public void keyTyped(KeyEvent e) {}

}
