package code;

import com.phidgets.*;
import com.phidgets.event.*;

public class PhidgetInterface {

	InterfaceKitPhidget device;
	
	public PhidgetInterface() {
		try {
			Phidget.enableLogging(Phidget.PHIDGET_LOG_VERBOSE, null);
			device = new InterfaceKitPhidget();
			device.addAttachListener(new AttachListener() {
				public void attached(AttachEvent ae) {
					System.out.println("A new device has been attached!");
				}
			});
			device.open(73651);
			device.waitForAttachment();
			System.out.println("opened");
			
		} catch (PhidgetException e) {
			System.out.println(e.toString());
		}
	}
	
	public void turnOnPort(int i) {
		try {
			device.setOutputState(i, true);
		} catch (PhidgetException e) {
			System.out.println(e.toString());
		}
	}
	
	public void turnOffPort(int i) {
		try {
			device.setOutputState(i, false);
		} catch (PhidgetException e) {
			System.out.println(e.toString());
		}
	}
	
}
