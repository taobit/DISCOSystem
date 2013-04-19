/*
 * Copyright 2011 Phidgets Inc.  All rights reserved.
 */

import com.phidgets.AnalogPhidget;
import com.phidgets.PhidgetException;
import com.phidgets.event.ErrorEvent;
import com.phidgets.event.ErrorListener;
import java.io.IOException;

public class AnalogExample {

    public static void main(String[] args) throws InterruptedException {
        ErrorListener ErrorListener = null;

        try {
            AnalogPhidget ap = new AnalogPhidget();

            ap.openAny();
            System.out.println("Waiting for the Phidget Analog to be attached...");
            ap.waitForAttachment();

            ap.addErrorListener(new ErrorListener() {

            public void error(ErrorEvent ee) {
                    System.out.println("\n--->Error: " + ee.getException());
                }
            });

            System.out.println("Phidget Information");
            System.out.println("====================================");
			System.out.println("Library Version: "+ ap.getLibraryVersion());
            System.out.println("Version: " + ap.getDeviceVersion());
            System.out.println("Name: " + ap.getDeviceName());
            System.out.println("Serial #: " + ap.getSerialNumber());
            System.out.println("# Analog Outputs: " + ap.getOutputCount());

            System.out.println("Voltage Min: " + ap.getVoltageMin(0));
            System.out.println("Voltage Max: " + ap.getVoltageMax(3) + "\n");

            System.out.print("Setting all enabled states to true\n");
            for (int i = 0; i < ap.getOutputCount() - 1; i++) {
                ap.setEnabled(i, true);
            }

            Thread.sleep(100);

            System.out.println("\nCurrent Voltage of the first Analog Output: " + ap.getVoltage(0));

            double newVoltage = 2;        
            System.out.println("Setting the Voltage of the first Analog Output to " + newVoltage + "...");
            ap.setVoltage(0, newVoltage);

            Thread.sleep(100);

            //keep displaying output;

            System.out.print("\nPress any key to close...");
            try {
                System.in.read();
            } catch (IOException ex) {
            }

            //closing
            ap.setVoltage(0, 0);
            for (int i = 0; i < ap.getOutputCount() - 1; i++) {
                ap.setEnabled(i, false);
            }

            Thread.sleep(100);

            ap.removeErrorListener(ErrorListener);
            ap.close();
            ap = null;

        } catch (PhidgetException ex) {
            System.out.println("Exception: " + "Phidget Error: " + ex.getDescription());
        }
    }
}
