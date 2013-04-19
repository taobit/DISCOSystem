/*
 * DISCOApp.java
 */

package disco;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import com.phidgets.*;
import com.phidgets.event.*;

/**
 * The main class of the application.
 */
public class DISCOApp extends SingleFrameApplication {

    static InterfaceKitPhidget ik;
    static AttachListener attachHandler;
    static DetachListener detachHandler;
    static int serialNumber;
    static String name;
    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        show(new DISCOView(this));
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of DISCOApp
     */
    public static DISCOApp getApplication() {
        return Application.getInstance(DISCOApp.class);
    }
    
    
    
//------------------------------------------------------------------------------
    /**
     * Main method launching the application.
     */
    public static final void main(String[] args) throws Exception {
        launch(DISCOApp.class, args);
        ik = new InterfaceKitPhidget();
        serialNumber = 0;
        name = new String();
        connectBox();
        controlBox();
        detachBox();
        
    }
    
    public static void printtoButton(String input){
        
    }

    public static void connectBox(){
        attachHandler = new AttachListener() {
            public void attached(AttachEvent event) {
                try {
                    serialNumber = ((Phidget)event.getSource()).getSerialNumber();
                    name = ((Phidget)event.getSource()).getDeviceName();
                } catch (PhidgetException exception) {
                    printError(exception.getErrorNumber(), exception.getDescription());
                }

                System.out.println("Hello Device " + name + ", Serial Number: " + Integer.toString(serialNumber));
            }
        };
    }
    
    public static void controlBox(){
        
    }
    
    public static void detachBox(){
        
    }
    
    public static void printError(int number, String description) {
        System.out.println("Error Event: " + Integer.toString(number) + " - "  + description); 
    }
}
