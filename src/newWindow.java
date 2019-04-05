import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.InetAddress;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;

@SuppressWarnings("serial")
public class newWindow extends JFrame implements ActionListener, Runnable, KeyListener {
  private Socket mySocket;
  private InetAddress sourceAddress;
  private int sourcePort;
  private JTextArea messageContainer;
  private JTextField messageToSend;

  public void setSocket(Socket socket) {
    this.mySocket = socket;
  }

  public void setSourceAddress(InetAddress sourceAdd) {
    this.sourceAddress = sourceAdd;
  }

  public void setSourcePort(int port) {
    this.sourcePort = port;
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


    messageContainer = new JTextArea();
    messageContainer.setEditable(false);
    panel.add(messageContainer);


    JButton send = new JButton("Send");
    send.setBounds(350, 200, 75, 25);
    panel.add(send);
    send.addActionListener(this);

    messageToSend = new JTextField();
    messageToSend.setBounds(25, 200, 300, 35);
    messageToSend.setColumns(10);
    panel.add(messageToSend);
    messageToSend.addKeyListener(this);


    JScrollPane scrollPane =
        new JScrollPane(messageContainer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    DefaultCaret caret = (DefaultCaret) messageContainer.getCaret();
    caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    scrollPane.setBounds(25, 29, 300, 150);
    panel.add(scrollPane);



  }

  public void addToTextArea(String txt) {
    if (this.messageContainer.getText().trim().length() == 0) {
      this.messageContainer.append(txt);
    } else {
      this.messageContainer.append("\n" + txt);
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    JButton btnClicked = (JButton) e.getSource();
    String button = btnClicked.getText();

    switch (button) {
      case "Send":
        sendMessage();
        break;
      default:
        break;
    }
  }

  public void sendMessage() {
    String msg_text = messageToSend.getText();
    messageToSend.setText("");
    mySocket.send(msg_text, sourceAddress, sourcePort);
    addToTextArea("Me: " + msg_text);

  }

  @Override
  public void run() {}

  @Override
  public void keyPressed(KeyEvent e) {
    // TODO Auto-generated method stub

  }

  @Override
  public void keyReleased(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      sendMessage();
    }

  }

  @Override
  public void keyTyped(KeyEvent e) {
    // TODO Auto-generated method stub

  }
}
