/*
 * Copyright 2007 Phidgets Inc.  All rights reserved.
 */

import com.phidgets.*;
import com.phidgets.event.*;

public class phExample
{
	public static final void main(String args[]) throws Exception {
		PHSensorPhidget phsensor;

		System.out.println(Phidget.getLibraryVersion());


		phsensor = new PHSensorPhidget();
		phsensor.addAttachListener(new AttachListener() {
			public void attached(AttachEvent ae) {
				System.out.println("attachment of " + ae);
			}
		});
		phsensor.addDetachListener(new DetachListener() {
			public void detached(DetachEvent ae) {
				System.out.println("detachment of " + ae);
			}
		});
		phsensor.addErrorListener(new ErrorListener() {
			public void error(ErrorEvent ee) {
				System.out.println("error event for " + ee);
			}
		});
		phsensor.addPHChangeListener(new PHChangeListener()
		{
			public void phChanged(PHChangeEvent oe) {
				System.out.println(oe);
			}
		});

		phsensor.openAny();
		System.out.println("waiting for PHSensor attachment...");
		phsensor.waitForAttachment();

		System.out.println("Serial: " + phsensor.getSerialNumber());
		phsensor.setPHChangeTrigger(0.1);
		System.out.println("trigger: " + phsensor.getPHChangeTrigger());
		System.out.println("Outputting events.  Input to stop.");
		System.in.read();
		System.out.print("closing...");
		phsensor.close();
		phsensor = null;
		System.out.println(" ok");
		if (false) {
			System.out.println("wait for finalization...");
			System.gc();
		}
	}
}
