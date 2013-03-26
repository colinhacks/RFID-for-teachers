import gnu.io.PortInUseException;
import java.io.IOException;


public class POSTmaker {

	public static void main(String[] args) {
		RFIDreader reader = new RFIDreader();
		try {
			reader.start(reader.getCOMnames().get(0));
		} catch (PortInUseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// Interesting stuff begins here
		
		long tagID;
		while (true) {
			tagID = reader.blockingReadBuffer();
			System.out.println("Hello"+tagID);
		}
		//test.disconnect();
	}
	
}
