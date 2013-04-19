/*
 * Copyright 2007 Phidgets Inc.  All rights reserved.
 */

import com.phidgets.*;
import com.phidgets.event.*;

public class MotorControlExample
{
	public static final void main(String args[]) throws Exception {
		MotorControlPhidget motor;

		System.out.println(Phidget.getLibraryVersion());


		motor = new MotorControlPhidget();
		motor.addAttachListener(new AttachListener() {
			public void attached(AttachEvent ae) {
				System.out.println("attachment of " + ae);
			}
		});
		motor.addDetachListener(new DetachListener() {
			public void detached(DetachEvent ae) {
				System.out.println("detachment of " + ae);
			}
		});
		motor.addErrorListener(new ErrorListener() {
			public void error(ErrorEvent ee) {
				System.out.println("error event for " + ee);
			}
		});
		motor.addInputChangeListener(new InputChangeListener()
		{
			public void inputChanged(InputChangeEvent oe) {
				System.out.println(oe);
			}
		});
		motor.addCurrentChangeListener(new CurrentChangeListener()
		{
			public void currentChanged(CurrentChangeEvent oe)
			{
				System.out.println(oe);
			}
		});
		motor.addMotorVelocityChangeListener(new MotorVelocityChangeListener()
		{
			public void motorVelocityChanged(MotorVelocityChangeEvent oe)
			{
				System.out.println(oe);
			}
		});

		motor.openAny();
		System.out.println("waiting for MotorControl attachment...");
		motor.waitForAttachment();

		System.out.println("Serial: " + motor.getSerialNumber());
		System.out.println("Motors: " + motor.getMotorCount());
		System.out.println("Inputs: " + motor.getInputCount());

		for (int i = -100; i < 20; i++)
		{
			motor.setVelocity(0, i);
			motor.setVelocity(1, -i);
			Thread.sleep(50);
		}

		System.out.println("Outputting events.  Input to stop.");
		System.in.read();
		System.out.print("closing...");
		motor.close();
		motor = null;
		System.out.println(" ok");
		if (false) {
			System.out.println("wait for finalization...");
			System.gc();
		}
	}
}
