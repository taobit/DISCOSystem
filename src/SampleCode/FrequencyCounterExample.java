/*
 * Copyright 2011 Phidgets Inc.  All rights reserved.
 */
 
import com.phidgets.FrequencyCounterPhidget;
import com.phidgets.PhidgetException;
import com.phidgets.event.ErrorEvent;
import com.phidgets.event.ErrorListener;
import com.phidgets.event.FrequencyCounterCountEvent;
import com.phidgets.event.FrequencyCounterCountListener;
import java.io.IOException;

public class FrequencyCounterExample {

    static FrequencyCounterPhidget fc;

    public static void main(String[] args) throws InterruptedException {
        ErrorListener ErrorListener = null;
        FrequencyCounterCountListener frequencyCounterCountListener = null;
        int i;

        try {
            fc = new FrequencyCounterPhidget();

            fc.openAny();
            System.out.println("Waiting for the Phidget Frequency Counter to be attached...");
            fc.waitForAttachment();

            fc.addErrorListener(new ErrorListener() {

                public void error(ErrorEvent ex) {
                    System.out.println("\n--->Error: " + ex.getException());
                }
            });

            fc.addFrequencyCounterCountListener(new FrequencyCounterCountListener() {

                public void frequencyCounterCounted(FrequencyCounterCountEvent fcce) {
                    System.out.println(fcce.toString());
					System.out.println("--->real index is: " + fcce.getIndex());
                }
            });

            System.out.println("Phidget Information");
            System.out.println("====================================");
            System.out.println("Version: " + fc.getDeviceVersion());
            System.out.println("Name: " + fc.getDeviceName());
            System.out.println("Serial #: " + fc.getSerialNumber());
            System.out.println("# Channels: " + fc.getFrequencyInputCount());
            Thread.sleep(100);

            //setting all channels to true
            for (i = 0; i < fc.getFrequencyInputCount(); i++) {

                System.out.println("Setting the enable state of channel " + i + " to true");
                fc.setEnabled(i, true);

                System.out.println("Setting the filter type of channel " + i + " to Zero-Crossing");
                fc.setFilter(i, fc.PHIDGET_FREQUENCYCOUNTER_FILTERTYPE_ZERO_CROSSING);
            }

            System.out.print("\n\nPress any key to close...\n\n");
            try {
                System.in.read();
            } catch (IOException ex) {
            }

            //closing

            for (i = 0; i < fc.getFrequencyInputCount(); i++) {
                fc.reset(i);
            }

            fc.removeErrorListener(ErrorListener);
            fc.removeFrequencyCounterCountListener(frequencyCounterCountListener);
            fc.close();
            fc = null;
            System.out.println("\nTurning off PhidgetFrequencyCounter");

        } catch (PhidgetException ex) {
            System.out.println("Exception: " + "Phidget Error: " + ex.getDescription());
        }
    }
}
