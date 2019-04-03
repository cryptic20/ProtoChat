import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class newWindow extends JFrame implements ActionListener {
  private DatagramSocket socket;
  private JFrame mainWindow;
  private String message;
  private sendMessage sendMsg;
  private InetAddress addr;
  private int port;

  public void setInstance(DatagramSocket socket, String text, sendMessage msg, JFrame chatWindow) {
    this.socket = socket;
    this.message = text;
    this.sendMsg = msg;
    this.mainWindow = chatWindow;
  }

  public void setIPandPort(InetAddress addr, int port) {
    this.addr = addr;
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
    
    JTextArea textArea = new JTextArea();
    textArea.setEnabled(false);
    textArea.setEditable(false);
    textArea.setBounds(25, 29, 407, 153);
    panel.add(textArea);
    JButton Close = new JButton("Close");
    Close.setBounds(35, 242, 117, 29);
    panel.add(Close);
    Close.addActionListener(this);
    
    JButton send = new JButton("sendMessage");
    send.setBounds(274, 242, 117, 29);
    panel.add(send);
    send.addActionListener(this);
    
    JTextField textField = new JTextField();
    textField.setBounds(25, 194, 300, 36);
    panel.add(textField);
    textField.setColumns(10);
    
    JButton btnNewChat = new JButton("New Chat");
    btnNewChat.setBounds(350, 199, 100, 29);
    panel.add(btnNewChat);
    btnNewChat.addActionListener(this);
    
    JScrollPane scrollPane = new JScrollPane(textArea, 
               JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setBounds(25, 29, 300, 153);
    panel.add(scrollPane);
    
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    // TODO Auto-generated method stub

  }

}
