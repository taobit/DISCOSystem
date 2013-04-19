/*
 * Copyright 2011 Phidgets Inc.  All rights reserved.
 */

import com.phidgets.BridgePhidget;
import com.phidgets.PhidgetException;
import com.phidgets.event.ErrorEvent;
import com.phidgets.event.ErrorListener;
import com.phidgets.event.BridgeDataEvent;
import com.phidgets.event.BridgeDataListener;
import java.io.IOException;

public class BridgeExample {

    static BridgePhidget b;

    public static void main(String[] args) throws InterruptedException {
        ErrorListener ErrorListener = null;
        BridgeDataListener BridgeDataListener = null;

        int i;

        try {
            b = new BridgePhidget();

            b.openAny();
            System.out.println("Waiting for the Phidget Bridge to be attached...");
            b.waitForAttachment();

            b.addErrorListener(new ErrorListener() {

                public void error(ErrorEvent ex) {
                    System.out.println("\n--->Error: " + ex.getException());
                }
            });

            System.out.println("Phidget Information");
            System.out.println("====================================");
            System.out.println("Version: " + b.getDeviceVersion());
            System.out.println("Name: " + b.getDeviceName());
            System.out.println("Serial #: " + b.getSerialNumber());
            System.out.println("# Bridges: " + b.getInputCount());

            Thread.sleep(100);

            //setting all bridge enable states to true
            for (i = 0; i < b.getInputCount(); i++) {

                System.out.println("Setting the enable state of bridge " + i + " to true");
                b.setEnabled(i, true);

                System.out.println("Setting the gain of bridge " + i + " to 8");
                b.setGain(i, b.PHIDGET_BRIDGE_GAIN_8);
            }

            System.out.println("Setting the data rate to 1000");
            b.setDataRate(1000);

            b.addBridgeDataListener(new BridgeDataListener() {

                public void bridgeData(BridgeDataEvent bde) {
                    System.out.println(bde.toString());
                }
            });

            System.out.print("\n\nPress any key to close...\n\n");
            try {
                System.in.read();
            } catch (IOException ex) {
            }

            //closing

            b.removeBridgeDataListener(BridgeDataListener);
            b.removeErrorListener(ErrorListener);

            b.close();
            b = null;
            System.out.println("\nTurning off Phidget Bridge");

        } catch (PhidgetException ex) {
            System.out.println("Exception: " + "Phidget Error: " + ex.getDescription());
        }
    }
}
