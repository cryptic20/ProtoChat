import javax.swing.JFrame;

public class Windows {

	private newMessage[] windows;
	private int index;
	
public void addWindow(newMessage newWindow) {
		System.out.println(newWindow.getTitle());
		windows = new newMessage[50]; /// 30
		
		
			if(!(isInArray(newWindow.getTitle()))) {
				
		    for (int i = 0; i < windows.length; i++) {
			if(windows[i] == null) {
				windows[i] = newWindow;
				index = i;
				
				System.out.println(windows[i].getTitle());
				
				break;
		  
			
		      }	
		    } 
		 
		 }

	} 

public newMessage[] getWindows() {
	return windows;
  }
  
  
public int getWindowIndex() {
	return index;
}



	
	public boolean isInArray(String title) {
		
		for (int i = 0; i < windows.length; i++) {
			
			
			if(windows[i] == null) {
				return false;
			} else 
				if(windows[i].getTitle().equals(title)){

					System.out.print("entro");
					index = i;
					
					System.out.println(windows[i].getTitle());
				return true;
		       
			
			}
			
		}
		
		return false;
	}
	
}
