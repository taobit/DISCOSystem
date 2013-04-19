/*
 * Copyright 2007 Phidgets Inc.  All rights reserved.
 */


import com.phidgets.*;
import com.phidgets.event.*;

public class DictionaryExample
{
	public static final void main(String args[]) throws Exception {
		Dictionary dict;
		DictionaryKeyListener listen;

		System.out.println(Phidget.getLibraryVersion());

		dict = new Dictionary();
		listen = new DictionaryKeyListener(dict, ".*");
		
		

		listen.addKeyChangeListener(new KeyChangeListener()
		{
			public void keyChanged(KeyChangeEvent e)
			{
				System.out.println(e);
			}
		});

		listen.addKeyRemovalListener(new KeyRemovalListener()
		{
			public void keyRemoved(KeyRemovalEvent e)
			{
				System.out.println(e);
			}
		});

		dict.open("localhost",5001);
		
		Thread.sleep(2500);
		
		listen.start();

		System.out.println("Connected: " + dict.isAttached());
		System.out.println("Server: " + dict.getServerAddress() + ":" + dict.getServerPort());

		dict.add("test1", "ok");
		dict.add("test2", "ok", true);
		dict.add("test3", "ok", false);
		dict.add("test4", "ok", true);
		dict.remove("test4");
		
		System.out.println("Press any key to read...");
		System.in.read();
		
		try
		{
			String val = dict.get("test1");
			System.out.println("Value of test1: "+val);
			
			//should throw exception
			val = dict.get("test4");
			System.out.println("Value of test4: "+val);
		}
		catch(PhidgetException ex)
		{
			if(ex.getErrorNumber() == PhidgetException.EPHIDGET_NOTFOUND)
				System.out.println("Error during dict.get(): Key was not found.");
		}

		System.out.println("Press any key to exit...");
		System.in.read();

		dict.close();
		dict = null;
		System.out.println(" ok");
	}
}
