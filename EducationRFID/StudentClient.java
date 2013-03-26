import gnu.io.PortInUseException;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.Random;

public class StudentClient extends JFrame {


    public JTextField questionBox; // top
	public JTextField feedbackBox; // middle
	public JButton enter;
    public long currentID;
    public String prompt;
    public long answerrfid;
    Random randomGenerator;
    private final Color grey = new Color(100,100,100);
    private final Color green = new Color(0,200,0);
    private final Color red = new Color(200,0,0);
    private long[] possibleTags = {6599491045901L,113251618721924L,113251623008686L,412466556011L}; //TODO:define this properly
    
    public void chooseNewQuestion() {
    	int ind = randomGenerator.nextInt() % 4;
    	if (ind < 0) {ind += 4;}
    	long rfid = possibleTags[ind];
    	prompt = "";
    	answerrfid = rfid;
    	try{
            
            URL url = new URL("http://209.61.142.34/manus/getItemNameFromRFID.php?rfid="+String.valueOf(rfid));
            URLConnection yc = url.openConnection();
            BufferedReader in = new BufferedReader(
                                    new InputStreamReader(
                                    yc.getInputStream()));
            String inputLine;
            System.out.println(url.getContent());
            while ((inputLine = in.readLine()) != null) 
                prompt += inputLine;
            in.close();
            questionBox.setText("Muestrame "+prompt+"?");
            
            }catch(IOException er){}
    }
    
    public StudentClient() {
    	super("Manus for Student");
    	//initialize rfid tag reader
    	randomGenerator = new Random(513487);
    	RFIDreaderStudent test = new RFIDreaderStudent(this);
		try {
			test.start(test.getCOMnames().get(0));
		} catch (PortInUseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
        GridLayout layout = new GridLayout(1,4);
        
        questionBox = new JTextField();
        questionBox.setEditable(false);
        feedbackBox = new JTextField("initial");
        feedbackBox.setEditable(false);
        Font font = new Font("Verdana", Font.BOLD, 20);
        questionBox.setFont(font);
        questionBox.setForeground(Color.BLUE);
        questionBox.setHorizontalAlignment(JTextField.CENTER);
       
        this.getContentPane().setBackground(grey);
       
        enter = new JButton("Submit");
        enter.addActionListener(new ActionListener() {
        	
            public void actionPerformed(ActionEvent e) {
            	long previousAnswer = answerrfid;
                String name = questionBox.getText();
                //String info = infoBox.getText();
                //first check if answer is correct
                boolean correct = false;
                //rfid value set to currentID already
                URL url;
                try{
                
                url = new URL("http://209.61.142.34/manus/getItemNameFromRFID.php?rfid="+String.valueOf(currentID));
                System.out.println("sending: " + "http://209.61.142.34/manus/getItemNameFromRFID.php?rfid="+String.valueOf(currentID));
                URLConnection yc = url.openConnection();
                BufferedReader in = new BufferedReader(
                                        new InputStreamReader(
                                        yc.getInputStream()));
                String inputLine;
                String userAnswer = "";
                while ((inputLine = in.readLine()) != null) 
                    userAnswer += inputLine;
                in.close();
                if (userAnswer.equals(prompt)) {correct = true;}
                
                }catch(IOException er){}
                if (correct) {
                	feedbackBox.setBackground(green);
                	chooseNewQuestion();
                } else {
                	feedbackBox.setBackground(red);
                	questionBox.setText("Muestrame "+prompt+"?"+" Incorrecto. Intentalo de nuevo!");
                }
                System.out.println("End of submit");
                try{
                	
                	http://209.61.142.34/manus/insertSubmission.php?student_answer_rfid=5555&correct_rfid=222
                    
                    url = new URL("http://209.61.142.34/manus/insertSubmission.php?student_answer_rfid="+String.valueOf(currentID)+"&correct_rfid="+String.valueOf(previousAnswer));
                System.out.println("http://209.61.142.34/manus/insertSubmission.php?student_answer_rfid="+String.valueOf(currentID)+"&correct_rfid="+String.valueOf(previousAnswer));
                    URLConnection yc = url.openConnection();
                    BufferedReader in = new BufferedReader(
                                            new InputStreamReader(
                                            yc.getInputStream()));
                    String inputLine;
                    String nothing = "";
                    while ((inputLine = in.readLine()) != null) 
                        nothing += inputLine;
                    in.close();
                    
                    }catch(IOException er){}
            }
        });
        
       /* 
        infoBox = new JTextArea("Enter info here.");
        infoBox.setEditable(true);
        infoBox.setBorder(BorderFactory.createCompoundBorder(
                infoBox.getBorder(), 
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        */
       
        add(questionBox, BorderLayout.NORTH);
        add(feedbackBox, BorderLayout.CENTER);
        //add(new JScrollPane(infoBox));
        add(enter, BorderLayout.SOUTH);
        
        chooseNewQuestion();
        
        setSize(500, 300);
        setVisible(true);

    }
  
    }
    