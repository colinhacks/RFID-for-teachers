import gnu.io.PortInUseException;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class TeacherWindow extends JFrame {


    public JTextField messageBox; // top
	private JTextField nameBox; // middle
    private JTextArea infoBox;
    private JButton enter;
    public long currentID;
    public final HashMap shared;
    
    public TeacherWindow(String title, final HashMap shared) {
    	super(title);
    	this.shared = shared;
    	//initialize rfid tag reader
    	final RFIDreader test = new RFIDreader(this);
		try {
			test.start(test.getCOMnames().get(0));
		} catch (PortInUseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        GridLayout layout = new GridLayout(1,4);
        
        enter = new JButton("Submit");
        enter.addActionListener(new ActionListener() {
        	
            public void actionPerformed(ActionEvent e) {
                String name = nameBox.getText();
                //String info = infoBox.getText();
                try{
                
                URL url = new URL("http://209.61.142.34/manus/updateRFID.php?rfid="+String.valueOf(currentID)+"&name="+name);
                URLConnection yc = url.openConnection();
                BufferedReader in = new BufferedReader(
                                        new InputStreamReader(
                                        yc.getInputStream()));
                String inputLine;

                while ((inputLine = in.readLine()) != null) 
                    System.out.println(inputLine);
                in.close();
                messageBox.setText("Success!  Scan more tagged items now.");
                nameBox.setText("");
                
                }catch(IOException er){}
                
                // add to hashmap
                shared.put(currentID, name);
                
            }
        });

       this.addWindowStateListener(new WindowAdapter() {
    	   @Override
    	   public void windowStateChanged(WindowEvent we) {
    		   super.windowStateChanged(we);
    		   if (we.getNewState() == Frame.ICONIFIED) {
    			   test.disconnect();
    		   }
    	   }
       });
        
        
        messageBox = new JTextField("Scan tagged items now.");
        messageBox.setEditable(false);
        
        
        
        nameBox = new JTextField();
        nameBox.setEditable(true);
        Font font = new Font("Verdana", Font.BOLD, 40);
        nameBox.setFont(font);
        nameBox.setForeground(Color.BLUE);
        nameBox.setHorizontalAlignment(JTextField.CENTER);
       /* 
        infoBox = new JTextArea("Enter info here.");
        infoBox.setEditable(true);
        infoBox.setBorder(BorderFactory.createCompoundBorder(
                infoBox.getBorder(), 
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        */
       
        add(messageBox, BorderLayout.NORTH);
        add(nameBox, BorderLayout.CENTER);
        //add(new JScrollPane(infoBox));
        add(enter, BorderLayout.SOUTH);
        
        
        
        setSize(500, 300);
        setVisible(true);

    }
  
    }
    