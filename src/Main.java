import javax.swing.JFrame;

public class Main {
  public static void main(String[] args) {
    JFrame window = new JFrame("Datagram Chat");
    window.setContentPane(new MessagePanel());
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setResizable(false);
    window.pack();
    window.setVisible(true);
  }
}
