import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MessagePanel extends JPanel implements Runnable {
  private final int WIDTH = 320;
  private final int HEIGHT = 240;
  private Thread thread;
  private final int port = 8080;
  private String ip_address = "";
  
  public MessagePanel() {
    super();
    setPreferredSize(
        new Dimension(WIDTH, HEIGHT));
    setFocusable(true);
    requestFocus();
}
  public void addNotify() {
    super.addNotify();
    if(thread == null) {
        thread = new Thread(this);
        thread.start();
    }
}
  
  private void init() {
    
    //label for ip address
    JLabel ip_label = new JLabel();
    ip_label.setText("My IP: "+ this.ip_address);
    
    //label for 
    
    
    //buttons
    JButton closeBtn = new JButton("Close");
    closeBtn.setToolTipText("Exit");
    closeBtn.setSize(100, 30);
    closeBtn.setBackground(Color.RED);
    
    JButton newMsgBtn = new JButton("New Message");
    newMsgBtn.setToolTipText("Send New Message");
    newMsgBtn.setSize(100, 30);
    newMsgBtn.setBackground(Color.GREEN);
    
    //add components
    this.add(ip_label);
    this.add(newMsgBtn);
    this.add(closeBtn);
  }
  
 @Override
  public void run() {
    init();
    
    while(true) {
      System.out.println("tet");
    }
   
  }

}
