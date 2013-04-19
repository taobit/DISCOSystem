/*
 * Copyright 2007 Phidgets Inc.  All rights reserved.
 */


import com.phidgets.*;
import com.phidgets.event.*;

public class IRExample
{
	public static final void main(String args[]) throws Exception {
	
		IRPhidget ir;
		
		System.out.println(Phidget.getLibraryVersion());

		ir = new IRPhidget();
		
		ir.addAttachListener(new AttachListener() {
			public void attached(AttachEvent ae) {
				System.out.println("attachment of " + ae);
			}
		});
		ir.addDetachListener(new DetachListener() {
			public void detached(DetachEvent ae) {
				System.out.println("detachment of " + ae);
			}
		});
		ir.addErrorListener(new ErrorListener() {
			public void error(ErrorEvent ee) {
				System.out.println("error event for " + ee);
			}
		});
		ir.addCodeListener(new CodeListener()
		{
			public void code(CodeEvent ce) {
				System.out.println(ce);
			}
		});
		ir.addLearnListener(new LearnListener()
		{
			public void learn(LearnEvent le) {
				System.out.println(le);
				try
				{
					((IRPhidget)le.getSource()).transmit(le.getValue().getCode(), le.getValue().getCodeInfo());
				}
				catch(PhidgetException e)
				{
				}
			}
		});
		RawDataListener rDataListen;
		ir.addRawDataListener(rDataListen = new RawDataListener()
		{
			public void rawData(RawDataEvent rde) {
				System.out.println(rde);
			}
		});

		
		ir.openAny();
		System.out.println("waiting for IR attachment...");
		ir.waitForAttachment();

		System.out.println("Serial: " + ir.getSerialNumber());
		
		System.out.println("sending some raw data...");
		
		int[] dataRaw = {
		   9040,   4590,    540,    630,    550,   1740,    550,   1750,    550,   1740,
			550,    620,    550,   1750,    550,   1740,    550,   1750,    550,   1740,
			550,   1740,    560,   1740,    540,    630,    550,    620,    550,    620,
			540,    630,    550,   1750,    550,   1740,    560,   1740,    550,    620,
			550,   1740,    550,    620,    550,    620,    560,    610,    550,    620,
			550,   1750,    550,   1740,    550,    620,    550,   1740,    550,   1750,
			550,    620,    550,    620,    550,    620,    540};

		ir.transmitRaw(dataRaw);

		System.out.println("Waiting for a code...");
		while(true)
		{
			Thread.sleep(10);
			try{
				IRCode code = ir.getLastCode();
				System.out.println("Got a code!!: "+code.toString()+" ("+code.getBitCount()+"-bit)");
				break;
			}
			catch(PhidgetException e)
			{
			}
		}
		
		System.out.println("Waiting for a learned code...");
		while(true)
		{
			Thread.sleep(10);
			try{
				IRLearnedCode code = ir.getLastLearnedCode();
				System.out.println("Got a learned code!!: "+code.toString());
				break;
			}
			catch(PhidgetException e)
			{
			}
		}
		
		//stop listening for raw data and start polling for it
		ir.removeRawDataListener(rDataListen);
		System.out.println("Now polling raw data...");
		int dataToRead=100; //print the new 100 elements
		while(dataToRead>0)
		{
			Thread.sleep(10);
			try{
				int[] data = new int[16];
				int length = ir.readRaw(data);
				if(length > 0)
				{
					String out = " Raw data:";
					for(int i=0;i<length;i++)
					{
						if(i%8 == 0) out = out + "\n";
						if(data[i]==com.phidgets.IRPhidget.RAWDATA_LONGSPACE)
							out = out + "LONG";
						else
							out = out + data[i];
						if((i+1)%8 != 0) out = out + ", ";
					}
					System.out.println(out);
					dataToRead-=length;
				}
			}
			catch(PhidgetException e)
			{
			}
		}
		
		System.out.println("Continuing to output events.  Enter to stop.");
		System.in.read();
		System.out.print("closing...");
		ir.close();
		ir = null;
		System.out.println(" ok");
		if (false) {
			System.out.println("wait for finalization...");
			System.gc();
		}
	}
}
