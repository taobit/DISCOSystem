/*
 * Copyright 2007 Phidgets Inc.  All rights reserved.
 */


import com.phidgets.*;
import com.phidgets.event.*;

public class ManagerExample
{
	public static final void main(String args[]) throws Exception {

		Manager man;
		System.out.println(Phidget.getLibraryVersion());

		man = new Manager();
		man.addAttachListener(new AttachListener()
		{
			public void attached(AttachEvent ae) {
				System.out.println("attachment of " + ae);
			}
		});
		man.addDetachListener(new DetachListener()
		{
			public void detached(DetachEvent ae) {
				System.out.println("detachment of " + ae);
			}
		});
		try
		{
			man.open();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("Press any key to exit...");
		System.in.read();

		man.close();
		Thread.sleep(1000);
		man = null;
		Thread.sleep(1000);
		System.out.println(" ok");
		Thread.sleep(1000);
	}
}
