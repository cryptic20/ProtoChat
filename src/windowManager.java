import java.util.HashMap;

/*
 * This class is used for managing the chat window using HashMap.
 */
public class windowManager {
  private HashMap<String, newWindow> map = new HashMap<String, newWindow>();

  /*
   * @param title Recipients InetAddress and Port number as key.
   * 
   * @param window the chat window object stored as value.
   */
  public boolean addWindow(String title, newWindow window) {
    if (!map.containsKey(title)) {
      // if key does not exist, add to map.
      map.put(title, window);
      return true;
    }
    return false;
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
