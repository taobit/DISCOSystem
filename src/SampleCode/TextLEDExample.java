/*
 * Copyright 2007 Phidgets Inc.  All rights reserved.
 */

import com.phidgets.*;
import com.phidgets.event.*;

public class TextLEDExample
{
	public static final void main(String args[]) throws Exception {
		TextLEDPhidget led;

		System.out.println(Phidget.getLibraryVersion());


		led = new TextLEDPhidget();
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
		System.out.println("waiting for TextLED attachment...");
		led.waitForAttachment();

		System.out.println("Serial: " + led.getSerialNumber());
		System.out.println("rows: " + led.getRowCount());
		System.out.println("columns: " + led.getColumnCount());

		led.setDisplayString(0, "Hi There...");

		for (int i = 0; i < 100; i++)
		{
			led.setBrightness(i);
			Thread.sleep(20);
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
