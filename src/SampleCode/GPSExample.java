/*
 * Copyright 2011 Phidgets Inc.  All rights reserved.
 */

import com.phidgets.GPSPhidget;
import com.phidgets.PhidgetException;
import com.phidgets.event.ErrorEvent;
import com.phidgets.event.ErrorListener;
import com.phidgets.event.GPSPositionChangeEvent;
import com.phidgets.event.GPSPositionChangeListener;
import com.phidgets.event.GPSPositionFixStatusChangeEvent;
import com.phidgets.event.GPSPositionFixStatusChangeListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GPSExample {

    static GPSPhidget gps;

    public static void main(String[] args) throws InterruptedException {
        ErrorListener ErrorListener = null;
        GPSPositionFixStatusChangeListener GPSPositionFixStatusChangeListener = null;

        try {
            gps = new GPSPhidget();

            gps.openAny();
            System.out.println("Waiting for the Phidget GPS to be attached...");
            gps.waitForAttachment();

            gps.addErrorListener(new ErrorListener() {

                public void error(ErrorEvent ex) {
                    System.out.println("\n--->Error: " + ex.getException());
                }
            });

            gps.addGPSPositionFixStatusChangeListener(new GPSPositionFixStatusChangeListener() {

                public void gpsPositionFixStatusChanged(GPSPositionFixStatusChangeEvent g) {
                     System.out.println(g.toString());
                }
            });

            gps.addGPSPositionChangeListener(new GPSPositionChangeListener() {

                public void gpsPositionChanged(GPSPositionChangeEvent gpspce) {
                    try {
                        System.out.println(gpspce.toString()
                                + ", Velocity: " + gps.getVelocity() + "km/h"
                                + ", Heading: " + gps.getHeading() + " degrees");
                    } catch (PhidgetException ex) {
            System.out.println("\n--->Error: " + ex.getDescription());
                    }

                }
            });

            System.out.println("Phidget Information");
            System.out.println("====================================");
            System.out.println("Version: " + gps.getDeviceVersion());
            System.out.println("Name: " + gps.getDeviceName());
            System.out.println("Serial #: " + gps.getSerialNumber());

			Calendar cal;
            cal = gps.getDateAndTime();
            
            DateFormat dateFormat = new SimpleDateFormat("'GPS Date:' E dd/MM/yyyy");
            DateFormat timeFormat = new SimpleDateFormat("'GPS Time:' HH:mm:ss.SSS z");
					 
			System.out.println(dateFormat.format(cal.getTime()));
            System.out.println(timeFormat.format(cal.getTime()));

            Thread.sleep(100);

            System.out.print("\n\nPress any key to close...\n\n");
            try {
                System.in.read();
            } catch (IOException ex) {
            }

            //closing

            gps.removeErrorListener(ErrorListener);
			gps.removeGPSPositionFixStatusChangeListener(GPSPositionFixStatusChangeListener);
            gps.close();
            gps = null;
            System.out.println("\nTurning off PhidgetGPS");

        } catch (PhidgetException ex) {
            System.out.println("Exception: " + "Phidget Error: " + ex.getDescription());
        }
    }
}
