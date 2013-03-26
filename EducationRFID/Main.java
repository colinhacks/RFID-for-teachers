import gnu.io.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.LinkedList;

public class Main {
	
	public static void main(String[] args) {
		
		RFIDreader test = new RFIDreader();
		try {
			test.start(test.getCOMnames().get(0));
		} catch (PortInUseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long tagID;
		while (true) {
			tagID = test.blockingReadBuffer();
			System.out.println("Hello"+tagID);
		}
		//test.disconnect();
	}
	
}
