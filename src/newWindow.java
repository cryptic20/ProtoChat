import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.DatagramSocket;
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
  private static DatagramSocket dgsocket;
  private static Socket socket;
  private InetAddress addr;
  private int port;
  private JTextArea textArea;
  private JTextField textField;
  private JTextField dest_ip;
  private JTextField dest_port;

  public void setInstance(DatagramSocket dgsocket, Socket socket) {
    this.dgsocket = dgsocket;
    this.socket = socket;
  }

  public void setAddress(InetAddress addr) throws UnknownHostException {
    this.addr = addr;
  }

  public void setPort(int port) {
    this.port = port;
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
        String text = textField.getText();
        textField.setText("");
        addToTextArea("Me: " + text);
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
      String dest = dest_ip.getText();
      if (dest != "") {
        try {
          InetAddress inet = InetAddress.getByName(dest);
          this.setAddress(inet);
        } catch (UnknownHostException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        dest_ip.setEnabled(false);
        System.out.println(this.addr);
      }
      String str_port = dest_port.getText();
      if (str_port != "") {
        dest_port.setEnabled(false);
        this.setPort(Integer.parseInt(str_port));
        System.out.println(this.port);
      }
    }



  }


  @Override
  public void keyTyped(KeyEvent arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void run() {}

}
