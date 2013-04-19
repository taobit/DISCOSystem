/*
 * Copyright 2007 Phidgets Inc.  All rights reserved.
 */

import com.phidgets.*;
import com.phidgets.event.*;

public class AccelExample
{
	public static final void main(String args[]) throws Exception {
		AccelerometerPhidget accel;

		System.out.println(Phidget.getLibraryVersion());


		accel = new AccelerometerPhidget();
		accel.addAttachListener(new AttachListener() {
			public void attached(AttachEvent ae) {
				System.out.println("attachment of " + ae);
			}
		});
		accel.addDetachListener(new DetachListener() {
			public void detached(DetachEvent ae) {
				System.out.println("detachment of " + ae);
			}
		});
		accel.addErrorListener(new ErrorListener() {
			public void error(ErrorEvent ee) {
				System.out.println("error event for " + ee);
			}
		});
		accel.addAccelerationChangeListener(new AccelerationChangeListener() {
			public void accelerationChanged(AccelerationChangeEvent oe) {
				System.out.println(oe);
			}
		});

		accel.openAny();
		System.out.println("waiting for Accelerometer attachment...");
		accel.waitForAttachment();

		System.out.println("Serial: " + accel.getSerialNumber());
		System.out.println("Axes: " + accel.getAxisCount());
		accel.setAccelerationChangeTrigger(0, 0.2);
		accel.setAccelerationChangeTrigger(1, 0.2);
		System.out.println("triggers: " + accel.getAccelerationChangeTrigger(0) + ", " + accel.getAccelerationChangeTrigger(1));
		System.out.println("Outputting events.  Input to stop.");
		System.in.read();
		System.out.print("closing...");
		accel.close();
		accel = null;
		System.out.println(" ok");
		if (false) {
			System.out.println("wait for finalization...");
			System.gc();
		}
	}
}
