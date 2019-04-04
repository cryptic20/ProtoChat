public class Windows {
  private newWindow[] windows;
  private int index;

  public void addWindow(newWindow newWindow) {
    System.out.println(newWindow.getTitle());



    if (!(isInArray(newWindow.getTitle()))) {

      for (int i = 0; i < windows.length; i++) {
        if (windows[i] == null) {
          windows[i] = newWindow;
          index = i;

          System.out.println(windows[i].getTitle());

          break;


        }
      }

    }

  }

  public newWindow[] getWindows() {
    return windows;
  }


  public int getWindowIndex() {
    return index;
  }



  public boolean isInArray(String title) {

    for (int i = 0; i < windows.length; i++) {


      if (windows[i] == null) {
        return false;
      } else if (windows[i].getTitle().equals(title)) {

        System.out.print("entro");
        index = i;

        System.out.println(windows[i].getTitle());
        return true;


      }

    }

    return false;
  }

}
