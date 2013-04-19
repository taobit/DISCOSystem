/*
 * Copyright 2007 Phidgets Inc.  All rights reserved.
 */


import com.phidgets.*;
import com.phidgets.event.*;

public class EncoderExample
{
	public static final void main(String args[]) throws Exception {
		EncoderPhidget enc;

		System.out.println(Phidget.getLibraryVersion());


		enc = new EncoderPhidget();
		enc.addAttachListener(new AttachListener() {
			public void attached(AttachEvent ae) {
				System.out.println("attachment of " + ae);
			}
		});
		enc.addDetachListener(new DetachListener() {
			public void detached(DetachEvent ae) {
				System.out.println("detachment of " + ae);
			}
		});
		enc.addErrorListener(new ErrorListener() {
			public void error(ErrorEvent ee) {
				System.out.println("error event for " + ee);
			}
		});
		enc.addInputChangeListener(new InputChangeListener()
		{
			public void inputChanged(InputChangeEvent oe) {
				System.out.println(oe);
			}
		});
		enc.addEncoderPositionChangeListener(new EncoderPositionChangeListener()
		{
			public void encoderPositionChanged(EncoderPositionChangeEvent oe)
			{
				System.out.println(oe);
			}
		});

		enc.openAny();
		System.out.println("waiting for Encoder attachment...");
		enc.waitForAttachment();
		
		//Enable the encoders if using a 1047
		if (enc.getDeviceID() == enc.PHIDID_ENCODER_HS_4ENCODER_4INPUT)
        {
            enc.setEnabled(0, true);
            enc.setEnabled(1, true);
            enc.setEnabled(2, true);
			enc.setEnabled(3, true);
        }


		System.out.println("Serial: " + enc.getSerialNumber());
		System.out.println("Encoders: " + enc.getEncoderCount());
		System.out.println("Inputs: " + enc.getInputCount());

		System.out.println("Outputting events.  Input to stop.");
		System.in.read();
		System.out.print("closing...");
		enc.close();
		enc = null;
		System.out.println(" ok");
		if (false) {
			System.out.println("wait for finalization...");
			System.gc();
		}
	}
}
