import java.io.InputStream;

import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;

import gnu.io.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TooManyListenersException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class RFIDreaderStudent implements SerialPortEventListener {

	/* Example of how to use this:
	 * RFIDreader test = new RFIDreader();
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
	 */
	
	//for containing the ports that will be found
	public Enumeration ports = null;
	//map the port names to CommPortIdentifiers
	private HashMap portMap = new HashMap();
	
	//this is the object that contains the opened port
	private CommPortIdentifier selectedPortIdentifier = null;
	private SerialPort serialPort = null;
	
	//input and output streams for sending and receiving data
	private InputStream input = null;
	private OutputStream output = null;
	//buffer to be used by other programs
	private LinkedBlockingQueue<Long> buffer;
	private String nextBuffer;
	private StudentClient gui;
	
	//the timeout value for connecting with the port
	final static int TIMEOUT = 20000;
	
    //some ascii values for for certain things
    final static int SPACE_ASCII = 32;
    final static int DASH_ASCII = 45;
    final static byte NEW_LINE_ASCII = 10;
    
	
    public RFIDreaderStudent(StudentClient gui) {
		this.gui = gui;
	}

	/**
     * Updates the names of the possible serial ports, then returns an
     * ArrayList<String> of all the available values to you.
     */
    public ArrayList<String> getCOMnames() {
    	ArrayList<String> names = new ArrayList<String>();
    	ports = CommPortIdentifier.getPortIdentifiers(); //gives an Enumeration
    	while (ports.hasMoreElements()) {
			CommPortIdentifier curPort = (CommPortIdentifier)ports.nextElement();
			if (curPort.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				names.add(curPort.getName());
				portMap.put(curPort.getName(),curPort);
			}
		}
    	return names;
    }
    
    /**
     * Starts/initializes everything you'll ever need. Just give it a serial port name
     * e.g. "COM8". Make sure you do this before trying to read any buffer.
     * @param selectedPort
     * @throws PortInUseException
     * @throws IOException
     */
    public void start(String selectedPort) throws PortInUseException, IOException {
    	selectedPortIdentifier = (CommPortIdentifier)portMap.get(selectedPort);
		
		CommPort commPort = null;
		
		//the method below returns an object of type CommPort
		commPort = selectedPortIdentifier.open("RFIDreader", TIMEOUT);
		//the CommPort object can be casted to a SerialPort object
		serialPort = (SerialPort)commPort;
		
		//BAUD rate taken care of somehow!
		//TODO set baud rate	
		input = serialPort.getInputStream();
		output = serialPort.getOutputStream();
		buffer = new LinkedBlockingQueue<Long>();
		initListener();
		nextBuffer = "";
    }
	
    
    /**
     * Gives you the buffer. 
     */
    public LinkedBlockingQueue<Long> getBuffer() {
    	return buffer;
    }
    
    /**
     * Returns true if buffer is empty. False otherwise
     * @return
     */
    public boolean isBufferEmpty() {
    	return buffer.isEmpty();
    }
    
    /**
     * Returns how many items there are in the buffer.
     * @return
     */
    public int bufferSize() {
    	return buffer.size();
    }
    
    /**
     * Returns next item in the buffer or null if there isn't anything
     * @return
     */
    public long readBuffer() {
    	return buffer.poll();
    }
    
    /**
     * Returns next item in the buffer. Blocks any further process until
     * you get something in return.
     * @return
     */
    public long blockingReadBuffer()  {
    	try {
			return buffer.take();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return -1;
    }
    
	private void initListener() {
		try {
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		} catch (TooManyListenersException e) {
			System.out.println("Too many listeners");
		}
	}
		
	/**
	 * Apparently you should call this after you're done with the serial port
	 */
	public void disconnect() {
		//close the serial port
		try {
			serialPort.removeEventListener();
			serialPort.close();
			input.close();
			output.close();
		} catch (Exception e) {
			System.out.println("Failed to close");
		}
	}
	
	/**
	 * When a newline character is received, adds it to the common buffer
	 * @param b is the next item from the inputstream
	 */
	private void addToBuffer(byte b) {
		String c = new String(new byte[] {b});
		System.out.println(b);
		if ("0123456789ABCDE".contains(c)) {
			
			nextBuffer += new String(new byte[] {b});
		} else if (b == NEW_LINE_ASCII) {
			long input = Long.parseLong(nextBuffer, 16);
			System.out.println("I have input"+input);
			gui.currentID = input;
			System.out.println("goo");
			gui.feedbackBox.setText(String.valueOf(gui.currentID));
			System.out.println("added!");
			nextBuffer = "";
		}
	}
	
	public void serialEvent(SerialPortEvent evt) {
		if (evt.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				addToBuffer((byte)input.read());
			} catch (IOException e) {
				System.out.println("failed to read data");
			}
		}
		
	}
}
