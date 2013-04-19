/*
 * Copyright 2011 Phidgets Inc.  All rights reserved.
 */

import com.phidgets.TextLCDPhidget;
import com.phidgets.event.AttachEvent;
import com.phidgets.event.AttachListener;
import com.phidgets.event.DetachEvent;
import com.phidgets.event.DetachListener;
import com.phidgets.event.ErrorEvent;
import com.phidgets.event.ErrorListener;

public class TextLCDExample {

    public static final void main(String args[]) throws Exception {
        TextLCDPhidget lcd;

        lcd = new TextLCDPhidget();

        lcd.addAttachListener(new AttachListener() {

            public void attached(AttachEvent ae) {
                System.out.println("attachment of " + ae);
            }
        });

        lcd.addDetachListener(new DetachListener() {

            public void detached(DetachEvent ae) {
                System.out.println("detachment of " + ae);
            }
        });

        lcd.addErrorListener(new ErrorListener() {

            public void error(ErrorEvent ee) {
                System.out.println("error event for " + ee);
            }
        });

        lcd.openAny();
        System.out.println("Waiting for the TextLCD to be attached...");
        lcd.waitForAttachment();

        System.out.println("Phidget Information");
        System.out.println("====================================");
        System.out.println("Version: " + lcd.getDeviceVersion());
        System.out.println("Name: " + lcd.getDeviceName());
        System.out.println("Serial #: " + lcd.getSerialNumber());
        System.out.println("# Rows: " + lcd.getRowCount());
        System.out.println("# Columns: " + lcd.getColumnCount());

        if (lcd.getDeviceID() == TextLCDPhidget.PHIDID_TEXTLCD_ADAPTER) {
            System.out.println("# Screens: " + lcd.getScreenCount());

            //set screen 0 as active
            lcd.setScreen(0);
            lcd.setScreenSize(lcd.PHIDGET_TEXTLCD_SCREEN_2x16);
            lcd.initialize();

        }

        lcd.setDisplayString(0, "Hello World");

        for (int i = 0; i < 100; i++) {
            lcd.setContrast(i);
            Thread.sleep(50);
        }

        lcd.setCustomCharacter(8, 0x0, 0xF8000);
        lcd.setCustomCharacter(9, 0x0, 0xFFC00);
        lcd.setCustomCharacter(10, 0x0, 0xFFFE0);
        lcd.setCustomCharacter(11, 0x0, 0xFFFFF);
        lcd.setCustomCharacter(12, 0xF8000, 0xFFFFF);
        lcd.setCustomCharacter(13, 0xFFC00, 0xFFFFF);
        lcd.setCustomCharacter(14, 0xFFFE0, 0xFFFFF);
        lcd.setCustomCharacter(15, 0xFFFFF, 0xFFFFF);

        lcd.setDisplayString(1, "\010\011\012\013\014\015\016\017"); //Note: representation is octal
        lcd.setBacklight(true);
        lcd.setCursorBlink(true);
        lcd.setCursor(true);

		if (lcd.getDeviceID() == TextLCDPhidget.PHIDID_TEXTLCD_ADAPTER) {
			//switching the active screen to screen 1
			lcd.setScreen(1);
			lcd.setScreenSize(lcd.PHIDGET_TEXTLCD_SCREEN_2x16);
			lcd.initialize();

			lcd.setDisplayString(0, "Phidgets Rock");
		}
		
        for (int i = 0; i < 255; i++) {
            lcd.setContrast(i);
            Thread.sleep(25);
        }

        lcd.setCursorBlink(true);
        lcd.setCursor(true);

        System.out.print("Closing...");
        lcd.close();
        lcd = null;
        if (false) {
            System.out.println("wait for finalization...");
            System.gc();
        }
    }
}
