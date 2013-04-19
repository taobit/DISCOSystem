/*
 * Copyright 2007 Phidgets Inc.  All rights reserved.
 */

import com.phidgets.*;
import com.phidgets.event.*;

public class ServoExample
{
	public static final void main(String args[]) throws Exception {
		ServoPhidget servo;

		System.out.println(Phidget.getLibraryVersion());


		servo = new ServoPhidget();
		servo.addAttachListener(new AttachListener() {
			public void attached(AttachEvent ae) {
				System.out.println("attachment of " + ae);
			}
		});
		servo.addDetachListener(new DetachListener() {
			public void detached(DetachEvent ae) {
				System.out.println("detachment of " + ae);
			}
		});
		servo.addErrorListener(new ErrorListener() {
			public void error(ErrorEvent ee) {
				System.out.println("error event for " + ee);
			}
		});
		servo.addServoPositionChangeListener(new ServoPositionChangeListener()
		{
			public void servoPositionChanged(ServoPositionChangeEvent oe)
			{
				System.out.println(oe);
			}
		});

		servo.openAny();
		System.out.println("waiting for Servo attachment...");
		servo.waitForAttachment();

		System.out.println("Serial: " + servo.getSerialNumber());
		System.out.println("Servos: " + servo.getMotorCount());

		for (int i = 15; i < 230; i++)
		{
			servo.setPosition(0, i);
			Thread.sleep(10);
			System.out.println("Position: " + servo.getPosition(0));
		}

		System.out.println("Outputting events.  Input to stop.");
		System.in.read();
		servo.setPosition(0, -23);
		System.out.print("closing...");
		servo.close();
		servo = null;
		System.out.println(" ok");
	}
}
