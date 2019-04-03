import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
  private InetAddress address;
  private Thread thread;
  private String ip_address = "";
  private GridBagConstraints gbc;
  private JButton msg;
  private JButton exit;

  public MessagePanel() {
    super();
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

    gbc.weighty = 1;
    add(buttons, gbc);

    try {
      getAddress();
    } catch (java.net.UnknownHostException e) {
      e.printStackTrace();
    }
  }

  public void addNotify() {
    super.addNotify();
    if (thread == null) {
      thread = new Thread(this);
      thread.start();
    }
  }

  private void getAddress() throws java.net.UnknownHostException {
    address = InetAddress.getLocalHost();
    ip_address = address.getHostAddress();
    System.out.println(ip_address);

    add(new JLabel("<html>" + "<h1>" + "<strong>My IP: " + ip_address + "</h1><h2>My PORT: " + port
        + "</h2></html>"), gbc);

  }



  @Override
  public void run() {
    // listen for messages

  }

  @Override
  public void actionPerformed(ActionEvent e) {
    JButton btnClicked = (JButton) e.getSource();
    switch (btnClicked.getText()) {
      case "New Message":
        System.out.println("creating new message");
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
