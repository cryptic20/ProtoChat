import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.swing.JFrame;

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
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    // TODO Auto-generated method stub

  }

}
