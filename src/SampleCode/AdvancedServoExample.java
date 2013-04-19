/*
 * Copyright 2008 Phidgets Inc.  All rights reserved.
 */

import com.phidgets.*;
import com.phidgets.event.*;

public class AdvancedServoExample
{
	public static final void main(String args[]) throws Exception {
		AdvancedServoPhidget servo;
                

		System.out.println(Phidget.getLibraryVersion());


		servo = new AdvancedServoPhidget();
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
		System.out.println("waiting for AdvancedServo attachment...");
		servo.waitForAttachment();

		System.out.println("Serial: " + servo.getSerialNumber());
		System.out.println("Servos: " + servo.getMotorCount());

                //Initialize the Advanced Servo
                servo.setEngaged(0, false);
                servo.setSpeedRampingOn(0, false);
                
                servo.setPosition(0, 50);
                servo.setEngaged(0, true);
                Thread.sleep(500);
                
                System.out.println();
                System.out.println("Start Position: " + servo.getPosition(0));
                
                servo.setSpeedRampingOn(0, true);
                servo.setAcceleration(0,servo.getAccelerationMin(0));
                servo.setVelocityLimit(0, 200);
                servo.setPosition(0, 150);
                
		System.out.println("Outputting events.  Press Enter to stop");
		System.in.read();
                
		System.out.print("closing...");
                System.out.println();
		servo.close();
		servo = null;
		System.out.println(" ok");
	}
}
