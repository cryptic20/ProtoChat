import java.util.HashMap;

/*
 * This class is used for managing the chat window using HashMap.
 */
public class ChatManager {
  private HashMap<String, ChatWindow> windowMap = new HashMap<String, ChatWindow>();
  private HashMap<String, String> users = new HashMap<String, String>();

  /**
   * 
   * @param user Name of the user.
   * @return Returns the key to the windowMap of the user if found. Else will return null.
   */
  public String getUserWindow(String user) {
    if (users.containsKey(user)) {
      return users.get(user);
    }
    return null;
  }

  /**
   * 
   * @param username Name of the user to be added.
   * @param windowKey Key to their Chat Window stored in windowMap.
   * @return True if a user is added. False if user already exists.
   */
  public void addUser(String username, String windowKey) {
    if (!users.containsKey(username)) {
      // if user not in map, add it
      users.put(username, windowKey);
    }
  }

  /*
   * @param title Recipients InetAddress and Port number as key.
   * 
   * @param window the chat window object stored as value.
   */
  public void addWindow(String title, ChatWindow window) {
    if (!windowMap.containsKey(title)) {
      // if key does not exist, add to map.
      windowMap.put(title, window);
    }
  }

  /*
   * @param key The title of the window object
   */
  public Object getWindow(String key) {
    Object window = null;
    if (windowMap.containsKey(key)) {
      window = windowMap.get(key);
    }
    return window;
  }
}
