/*
 * Copyright 2007 Phidgets Inc.  All rights reserved.
 */


import com.phidgets.*;
import com.phidgets.event.*;

public class WeightSensorExample
{
	public static final void main(String args[]) throws Exception {
		WeightSensorPhidget weight;

		System.out.println(Phidget.getLibraryVersion());


		weight = new WeightSensorPhidget();
		weight.addAttachListener(new AttachListener() {
			public void attached(AttachEvent ae) {
				System.out.println("attachment of " + ae);
			}
		});
		weight.addDetachListener(new DetachListener() {
			public void detached(DetachEvent ae) {
				System.out.println("detachment of " + ae);
			}
		});
		weight.addErrorListener(new ErrorListener() {
			public void error(ErrorEvent ee) {
				System.out.println("error event for " + ee);
			}
		});
		weight.addWeightChangeListener(new WeightChangeListener()
		{
			public void weightChanged(WeightChangeEvent oe)
			{
				System.out.println(oe);
			}
		});

		weight.openAny();
		System.out.println("waiting for WeightSensor attachment...");
		weight.waitForAttachment();

		System.out.println("Serial: " + weight.getSerialNumber());
		weight.setWeightChangeTrigger(0.1);
		System.out.println("trigger: " + weight.getWeightChangeTrigger());
		System.out.println("Outputting events.  Input to stop.");
		System.in.read();
		System.out.print("closing...");
		weight.close();
		weight = null;
		System.out.println(" ok");
		if (false) {
			System.out.println("wait for finalization...");
			System.gc();
		}
	}
}
