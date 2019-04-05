import javax.swing.JFrame;

/**
 * Simple chat application using IP DatagramSocket.
 * 
 * @author S Wyco
 */
public class Main {
  public static void main(String[] args) {
    JFrame window = new JFrame("Datagram Chat");
    window.add(new MessagePanel());
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.pack();
    window.setLocationRelativeTo(null);
    window.setVisible(true);
  }
}
