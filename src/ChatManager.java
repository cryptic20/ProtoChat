import java.util.HashMap;

/*
 * This class is used for managing the chat window using HashMap.
 */
public class ChatManager {
  private HashMap<String, ChatWindow> map = new HashMap<String, ChatWindow>();

  /*
   * @param title Recipients InetAddress and Port number as key.
   * 
   * @param window the chat window object stored as value.
   */
  public void addWindow(String title, ChatWindow window) {
    if (!map.containsKey(title)) {
      // if key does not exist, add to map.
      map.put(title, window);
    }
  }

  /*
   * @param key The title of the window object
   */
  public Object getWindow(String key) {
    Object window = null;
    if (map.containsKey(key)) {
      window = map.get(key);
    }
    return window;
  }
}
