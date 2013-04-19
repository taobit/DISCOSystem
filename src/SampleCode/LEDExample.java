/*
 * Copyright 2007 Phidgets Inc.  All rights reserved.
 */


import com.phidgets.*;
import com.phidgets.event.*;

public class LEDExample
{
	public static final void main(String args[]) throws Exception {
		LEDPhidget led;

		System.out.println(Phidget.getLibraryVersion());


		led = new LEDPhidget();
		led.addAttachListener(new AttachListener() {
			public void attached(AttachEvent ae) {
				System.out.println("attachment of " + ae);
			}
		});
		led.addDetachListener(new DetachListener() {
			public void detached(DetachEvent ae) {
				System.out.println("detachment of " + ae);
			}
		});
		led.addErrorListener(new ErrorListener() {
			public void error(ErrorEvent ee) {
				System.out.println("error event for " + ee);
			}
		});

		led.openAny();
		System.out.println("waiting for LED attachment...");
		led.waitForAttachment();

		System.out.println("Serial: " + led.getSerialNumber());
		System.out.println("LEDs: " + led.getLEDCount());
		
		led.setCurrentLimit(LEDPhidget.PHIDGET_LED_CURRENT_LIMIT_40mA);

		System.out.println("setting brightnesses...");
		for (int j = 0; j < 10; j++)
		{
			for (int i = 0; i < led.getLEDCount(); i++)
			{
				led.setBrightness(i, 0);
			}
			Thread.currentThread().sleep(250);
			for (int i = 0; i < led.getLEDCount(); i++)
			{
				led.setBrightness(i, 100);
			}
			Thread.currentThread().sleep(250);
		}
		
		System.out.println("setting current limits...");
		for (int j = 0; j < 10; j++)
		{
			for (int i = 0; i < led.getLEDCount(); i++)
			{
				led.setCurrentLimit(i, 60);
			}
			Thread.currentThread().sleep(250);
			for (int i = 0; i < led.getLEDCount(); i++)
			{
				led.setCurrentLimit(i, 10);
			}
			Thread.currentThread().sleep(250);
		}

		System.out.print("closing...");
		led.close();
		led = null;
		System.out.println(" ok");
		if (false) {
			System.out.println("wait for finalization...");
			System.gc();
		}
	}
}
